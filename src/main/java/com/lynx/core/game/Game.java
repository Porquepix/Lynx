package com.lynx.core.game;

import java.util.Map;
import java.util.Optional;

import bsh.Interpreter;

import com.lynx.core.Core;
import com.lynx.core.cast.CasterFactory;
import com.lynx.core.cast.StringCasterFactory;
import com.lynx.core.game.answer.Answer;
import com.lynx.core.game.answer.AnswerManager;
import com.lynx.core.game.answer.CastedAnswerFacotry;
import com.lynx.core.game.node.CacheNodeFactory;
import com.lynx.core.game.node.Node;
import com.lynx.core.game.node.NodeFactory;
import com.lynx.core.game.node.NodeManager;
import com.lynx.core.game.save.Save;
import com.lynx.core.interpreter.IInterpreter;
import com.lynx.core.interpreter.InterpreterManager;
import com.lynx.core.json.controller.BaseController;
import com.lynx.core.json.model.GameInfoModel;
import com.lynx.core.json.model.NextModel;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.translation.Translator;

public class Game implements IGame {

	public static final String INFO_FILE = "base";

	private GameInfoModel info;
	private Translator translator;
	private InterpreterManager interpreter;

	private Namespace gameRoot; // main root (namespace of the game)
	private Namespace storyRoot; // story root (where the story is) [gameRoot +
	// ".story"]
	private Namespace langRoot; // lang root (where the translations are)
	// [gameRoot + ".lang"]
	private Namespace saveRoot;

	private String currentNodeName;
	private Node currentNode;
	private NodeManager nodeManager;
	private AnswerManager answerManager;
	private CastedAnswerFacotry answerFactory;

	public Game(Namespace gameDir) {
		this.gameRoot = gameDir;
		this.storyRoot = gameRoot.append("story");
		this.langRoot = gameRoot.append("lang");
		this.saveRoot = gameRoot.append("saves");

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
		NodeFactory factory = new CacheNodeFactory(this, cachesize);
		this.nodeManager = new NodeManager(storyRoot, factory);

		CasterFactory<String> casterFactory = new StringCasterFactory();
		this.answerFactory = new CastedAnswerFacotry(casterFactory);

		setCurrentNode(info.getBoot());
	}

	@Override
	public int compareTo(IGame game) {
		return getInfo().getName().compareTo(game.getInfo().getName());
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
	
	public Namespace getSaveRoot() {
		return this.saveRoot;
	}

	public Translator getTranslator() {
		return this.translator;
	}
	
	public void setTranslator(Translator gameTranslator) {
		this.translator = gameTranslator;
	}

	public IInterpreter getInterpreter() {
		return this.interpreter;
	}

	public Node getCurrentNode() {
		return this.currentNode;
	}

	public void setCurrentNode(String nodeName) {
		this.currentNodeName = nodeName;
		this.currentNode = nodeManager.getNode(nodeName);
		this.answerManager = new AnswerManager(currentNode, interpreter, answerFactory);
		interpreter.evalIfNotNull(currentNode.getModel().getInit());
	}
	
	public String getCurrentNodeName() {
		return this.currentNodeName;
	}

	public boolean next(String answer) {
		Optional<Answer> a = answerManager.toAnswer(answer);
		if ( currentNode.isSkippable() || (a.isPresent() && answerManager.validate(a.get())) ) {
			NextModel next = currentNode.getNext();
			interpreter.evalIfNotNull(next.getAfter());
			this.setCurrentNode(next.getNode());
			return true;
		}
		return false;
	}

	public Map<String, Object> getContextVariables() {
		return this.interpreter.getVariables(info.getDeclaration());
	}

	@Override
    public void save(Save save) {
		save.write("node", getCurrentNodeName());
		save.write("variables", getContextVariables());
    }

	@SuppressWarnings("unchecked")
    @Override
    public void load(Save save) {
		this.interpreter = new InterpreterManager(new Interpreter());
		this.interpreter.addVariables((Map<String, Object>) save.read("variables"));
		this.setCurrentNode((String) save.read("node"));
    }

}
