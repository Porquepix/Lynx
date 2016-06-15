package core.game;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
import core.game.context.Context;
import core.game.context.ContextManager;
import core.logging.Log;
import core.translation.TranslateManager;

public class Game implements Comparable<Game> {

	private Path root;
	private GameInfo info;
	private Map<String, Object> variables;
	private TranslateManager translator;
	private ContextManager contextManager;
	private Context currentContext;
	
	public Game(Path gameDir, GameInfo info) {
		this.root = gameDir;
		this.info = info;
		this.variables = null;
		this.contextManager = new ContextManager(this);
	}
	
	public void start() {
		this.variables = buildVariablesMap(info.getVariables());
		this.currentContext = contextManager.loadContext(info.getStartingPoint());
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
	
	public String getCurrentText() {
		return this.currentContext.getText();
	}
	
	public Class<?> getAttendedType() {
		return this.currentContext.getAttendedType();
	}
	
	public void saveContextVariable() {
		try {
			this.variables = this.currentContext.save(new ArrayList<String>(this.variables.keySet()));
		} catch (EvalError e) {
			Log.get().warn("Impossible to save context variables for the context {}", this.currentContext);
		}
	}
	
	public boolean nextContext(Request r) {
		String nextID = this.currentContext.getNextContextID(r);
		if (nextID != null) {
			this.saveContextVariable();
			this.currentContext =  this.contextManager.loadContext(nextID);
		}
		return nextID != null;
	}

}
