package core;

import org.apache.logging.log4j.Level;

import core.config.Config;
import core.game.GameManager;
import core.json.JsonContent;
import core.logging.Log;


public class Core {
	
	public static boolean DEBUG = false;
	
	private JsonContent settings;
	
	// CHANGE VISIBYLITY
	public GameManager gameManager;
	
	public Core() {
		this.settings = Config.APP.loadContent().getContent();
		loadSettings();
		if (DEBUG) {
			Log.get().setLevel(Level.DEBUG);
			Log.get().warn("Application running in debug mode.");
		}
		
		this.gameManager = new GameManager();
		
		Log.get().info("Core has successfully started...");
	}

	private void loadSettings() {
		DEBUG = settings.getAsBoolean("debug", DEBUG);
	}

}
