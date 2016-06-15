package core.game.context;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import bsh.EvalError;
import core.exception.LynxException;
import core.game.Game;
import core.json.JsonContent;
import core.json.JsonFile;
import core.logging.Log;

public class ContextManager {
	
	private static final String ID_DELIMITER = ".";
	private static final String URI_FORMAT = "story/%s.json";
	
	private Game game;
	private Map<String, JsonContent> cache;
	
	public ContextManager(Game game) {
		this.game = game;
		this.cache = new HashMap<>();
	}
	
	public Context loadContext(String contextID) {
		int lastDelimiter = contextID.lastIndexOf(ID_DELIMITER);
		
		String file = contextID.substring(0, lastDelimiter).replace(ID_DELIMITER, "/");
		JsonContent json = getJsonContent(file);
		
		String key = contextID.substring(lastDelimiter + 1);
		JsonContent frame = json.getAsObject(key, new JsonContent());
		try {
			Context c =  new Context(game.getVariables(), frame);
			c.setTranslator(game.getTranslator());
			return c;
		} catch (EvalError e) {
			Log.get().error("Impossible to build context {}", e, contextID);
			throw new LynxException("Impossible to build context : game corrupted.");
		}
	}

	private JsonContent getJsonContent(String file) {
		if (!this.cache.containsKey(file)) {
			this.loadJsonContent(file);
		}
		return this.cache.get(file);
	}

	private void loadJsonContent(String file) {
		Path finalPath = this.game.getRoot().resolve(String.format(URI_FORMAT, file));
		JsonContent json = new JsonFile(finalPath).loadContentOrFail().getContent();
		this.cache.put(file, json);
	}

}
