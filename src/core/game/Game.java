package core.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import bsh.EvalError;
import core.exception.LynxException;
import core.game.interpreter.InterpreterFrame;
import core.game.tree.StateNode;
import core.json.controller.BaseController;
import core.json.model.GameInfoModel;
import core.logging.Loggers;
import core.logging.LynxLogger;
import core.namespace.Namespace;
import core.translation.CacheFileTranslator;

public class Game implements Comparable<Game> {

    private static final LynxLogger logger = Loggers.getLogger(Game.class);

    public static final String INFO_FILE = "base";
    public static final String INFO_FILE_EXT = "json";

    private Namespace root;
    private GameInfoModel info;
    private Map<String, Object> variables;
    private CacheFileTranslator translator;
    private StateNode currentNode;
    private InterpreterFrame interpreter;

    public Game(Namespace gameDir) {
	this.root = gameDir;
	this.variables = null;
	this.interpreter = null;
	loadInfo();
    }

    private void loadInfo() {
	Namespace namespace = root.append(INFO_FILE);
	BaseController<GameInfoModel> gInfoController = new BaseController<>(
		namespace, GameInfoModel.class);
	this.info = gInfoController.fetch();
    }

    public static void gameCorruptedException() {
	throw new LynxException(
		"Internal error : game corrupted. (Check logs to get more information)");
    }

    public void start() {
	this.variables = buildVariablesMap(info.getDeclaration());
	this.currentNode = new StateNode(this, info.getBoot());
	this.interpreter = new InterpreterFrame();
	try {
	    this.interpreter.addVariables(this.variables);
	} catch (EvalError e) {
	    logger.error(
		    "Impossible to add the game variables to the interpreter !",
		    e);
	    Game.gameCorruptedException();
	}
    }

    private Map<String, Object> buildVariablesMap(List<String> vars) {
	Map<String, Object> ret = new HashMap<>();
	for (String var : vars) {
	    ret.put(var, null);
	}
	return ret;
    }

    @Override
    public int compareTo(Game game) {
	return this.info.getName().compareTo(game.info.getName());
    }

    public String toString() {
	return root.toString();
    }

    public GameInfoModel getInfo() {
	return this.info;
    }

    public Namespace getRoot() {
	return this.root;
    }

    public void setTranslator(CacheFileTranslator t) {
	this.translator = t;
    }

    public Map<String, Object> getVariables() {
	return this.variables;
    }

    public CacheFileTranslator getTranslator() {
	return this.translator;
    }

    public StateNode getCurrentNode() {
	return this.currentNode;
    }

    public void setCurrentNode(StateNode node) {
	Objects.requireNonNull(node);
	this.currentNode = node;
    }

    public void saveContextVariable() {
	try {
	    this.variables = this.interpreter
		    .getVariables(new ArrayList<String>(this.variables.keySet()));
	} catch (EvalError e) {
	    logger.warn(
		    "Impossible to save context variables for the context {}",
		    this.currentNode.getId());
	}
    }

    public void eval(String code) {
	try {
	    this.interpreter.eval(code);
	} catch (EvalError e) {
	    logger.error(
		    "Impossible to eval following code: '{}' for node {} !", e,
		    code, this.currentNode.getId());
	    gameCorruptedException();
	}
    }

    public boolean evalCondition(String condition) {
	this.eval("__condition=(" + condition + ")");
	return (boolean) this.getVariable("__condition");
    }

    protected Object getVariable(String name) {
	try {
	    return this.interpreter.getVariable(name);
	} catch (EvalError e) {
	    logger.error(
		    "Impossible to get following variable: '{}' for node {} !",
		    e, name, this.currentNode.getId());
	    gameCorruptedException();
	}
	return null;
    }

    public boolean next(Answer a) {
	if (this.getCurrentNode().getAnswerNode().isVoidType()) {
	    this.advance();
	    return true;
	}

	if (this.getCurrentNode().getAnswerNode().isValidAnswer(a)) {
	    try {
		this.interpreter.addVariable("__answer", this.getCurrentNode()
			.getAnswerNode().getCastedAnswer(a).getValue());
	    } catch (EvalError e) {
		logger.error("Impossible to add answer variable for node: {}",
			this.getCurrentNode().getId());
		Game.gameCorruptedException();
	    }
	    this.advance();
	    return true;
	}

	return false;
    }

    private void advance() {
	this.setCurrentNode(this.getCurrentNode().getNextNode()
		.getNextStateNode());
	this.getCurrentNode().init();
    }

}
