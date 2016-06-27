package core.game;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
import core.exception.LynxException;
import core.game.interpreter.InterpreterFrame;
import core.logging.Log;
import core.translation.TranslateManager;

public class Game implements Comparable<Game> {

	private Path root;
	private GameInfo info;
	private Map<String, Object> variables;
	private TranslateManager translator;
	private Node currentNode;
	private InterpreterFrame interpreter;
	
	public Game(Path gameDir, GameInfo info) {
		this.root = gameDir;
		this.info = info;
		this.variables = null;
		this.interpreter = null;
	}
	
	protected static void gameCorruptedException() {
		throw new LynxException("Internal error : game corrupted.");
	}
	
	public void start() {
		this.variables = buildVariablesMap(info.getVariables());
		this.currentNode = Node.getFromId(info.getStartingPoint(), this);
		try {
			this.interpreter = new InterpreterFrame(this.variables);
		} catch (EvalError e) {
			Log.get().error("Impossible to start interpreter !", e);
			gameCorruptedException();
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

	public String getName() {
		return this.info.getName();
	}

	public Path getRoot() {
		return this.root;
	}

	public void setTranslator(TranslateManager t) {
		this.translator = t;		
	}

	public Map<String, Object> getVariables() {
		return this.variables;
	}

	public TranslateManager getTranslator() {
		return this.translator;
	}
	
	public Node getNode() {
		return this.currentNode;
	}
	
	public Node getNext(Answer a) {
		Node next = this.currentNode.getNext(a);
		if ( next != null ) {
			this.currentNode = next;
		}
		return next;
	}
	
	public void saveContextVariable() {
		try {
			this.variables = this.interpreter.getVariables(new ArrayList<String>(this.variables.keySet()));
		} catch (EvalError e) {
			Log.get().warn("Impossible to save context variables for the context {}", this.currentNode.getId());
		}
	}
	
	protected void execute(String code) {
		try {
			this.interpreter.eval(code);
		} catch (EvalError e) {
			Log.get().error("Impossible to eval following code: '{}' for node {} !", e, code, this.currentNode.getId());
			gameCorruptedException();
		}
	}
	
	protected Object getVariable(String name) {
		try {
	        return this.interpreter.getVariable(name);
        } catch (EvalError e) {
			Log.get().error("Impossible to get following variable: '{}' for node {} !", e, name, this.currentNode.getId());
			gameCorruptedException();
        }
		return null;
	}

}
