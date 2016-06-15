package core;

import java.util.List;

import org.apache.logging.log4j.Level;

import core.config.Config;
import core.game.Game;
import core.game.GameManager;
import core.game.Request;
import core.game.validation.RequestValidator;
import core.game.validation.RequestValidatorBuilder;
import core.json.JsonContent;
import core.logging.Log;


public class Core {
	
	public static final String MISSING_DATA = "Missing data !";
	public static boolean DEBUG = false;

	private JsonContent coreSettings;
	private JsonContent userSettings;
	private GameManager gameManager;
	
	public Core() {
		this.coreSettings = Config.APP.loadContent().getContent();
		loadCoreSettings();
		if (DEBUG) {
			Log.get().setLevel(Level.DEBUG);
			Log.get().warn("Application running in debug mode.");
		}
		this.userSettings = Config.USER.loadContent().getContent();
		
		this.gameManager = new GameManager(this);
		
		Log.get().info("Core has successfully started...");
	}

	private void loadCoreSettings() {
		DEBUG = coreSettings.getAsBoolean("debug", DEBUG);
	}
	
	public String getUserLang() {
		return this.userSettings.getAsString("lang", "en");
	}
	
	public List<Game> getGames() {
		return this.gameManager.getGames();
	}
	
	private boolean isValidGameNumber(Request r) {
		RequestValidator v = new RequestValidatorBuilder()
			.type(Integer.class)
			.range(0, this.getGames().size())
			.build();
		return v.validate(r);
	}
	
	public Game selectGame(Request r) {
		if (isValidGameNumber(r)) {
			return this.gameManager.setCurrent((int) r.getInput());
		}
		return null;
	}

}
