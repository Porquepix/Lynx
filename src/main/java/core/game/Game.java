package core.game;

import java.util.Map;

import bsh.Interpreter;
import core.Core;
import core.cast.CasterFactory;
import core.cast.StringCasterFactory;
import core.game.answer.Answer;
import core.game.answer.AnswerManager;
import core.game.answer.CastedAnswerFacotry;
import core.game.node.CacheNodeFactory;
import core.game.node.Node;
import core.game.node.NodeFactory;
import core.game.node.NodeManager;
import core.interpreter.InterpreterManager;
import core.json.controller.BaseController;
import core.json.model.GameInfoModel;
import core.json.model.NextModel;
import core.namespace.Namespace;
import core.translation.CacheFileTranslator;
import core.translation.Translator;

public class Game implements Comparable<Game> {

    public static final String INFO_FILE = "base";

    private GameInfoModel info;
    private Translator translator;
    private InterpreterManager interpreter;

    private Namespace gameRoot; // main root (namespace of the game)
    private Namespace storyRoot; // story root (where the story is) [gameRoot +
	                         // ".story"]
    private Namespace langRoot; // lang root (where the translations are)
	                        // [gameRoot + ".lang"]

    private Node currentNode;
    private NodeManager nodeManager;
    private AnswerManager answerManager;
    private CastedAnswerFacotry answerFactory;

    public Game(Namespace gameDir) {
	this.gameRoot = gameDir;
	this.storyRoot = gameRoot.append("story");
	this.langRoot = gameRoot.append("lang");

	this.interpreter = null;
	this.nodeManager = null;
	loadInfo();
    }

    private void loadInfo() {
	Namespace namespace = gameRoot.append(INFO_FILE);
	BaseController<GameInfoModel> gInfoController = new BaseController<>(namespace,
	        GameInfoModel.class);
	this.info = gInfoController.fetch();
    }

    public void start() {
	this.interpreter = new InterpreterManager(new Interpreter());
	this.interpreter.declareVariables(info.getDeclaration());

	int cachesize = Core.getInstance().getAppSettings().getNodeCacheSize();
	NodeFactory factory = new CacheNodeFactory(cachesize);
	this.nodeManager = new NodeManager(storyRoot, factory);

	CasterFactory<String> casterFactory = new StringCasterFactory();
	this.answerFactory = new CastedAnswerFacotry(casterFactory);

	setCurrentNode(info.getBoot());
    }

    @Override
    public int compareTo(Game game) {
	return this.info.getName().compareTo(game.info.getName());
    }

    @Override
    public String toString() {
	return gameRoot.toString();
    }

    public GameInfoModel getInfo() {
	return this.info;
    }

    public Namespace getRoot() {
	return this.gameRoot;
    }

    public Namespace getStoryRoot() {
	return this.storyRoot;
    }

    public Namespace getLangRoot() {
	return this.langRoot;
    }

    public void setTranslator(CacheFileTranslator t) {
	this.translator = t;
    }

    public Translator getTranslator() {
	return this.translator;
    }

    public Node getCurrentNode() {
	return this.currentNode;
    }

    public void setCurrentNode(String nodeName) {
	this.currentNode = nodeManager.getNode(nodeName);
	this.answerManager = new AnswerManager(currentNode, interpreter, answerFactory);
	interpreter.evalIfNotNull(currentNode.getModel().getInit());
    }

    public boolean next(String answer) {
	Answer a = answerManager.toAnswer(answer);
	if (answerManager.validate(a)) {
	    NextModel next = currentNode.getNext(interpreter);
	    interpreter.evalIfNotNull(next.getAfter());
	    this.setCurrentNode(next.getNode());
	    return true;
	}
	return false;
    }

    public Map<String, Object> getContextVariables() {
	return this.interpreter.getVariables(info.getDeclaration());
    }

}