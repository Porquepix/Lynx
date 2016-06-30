package core;

import java.util.List;

import org.apache.logging.log4j.Level;

import core.game.Answer;
import core.game.GameLoader;
import core.game.facade.GameFacade;
import core.game.validation.GlobalValidator;
import core.game.validation.GlobalValidatorBuilder;
import core.json.JsonContent;
import core.json.JsonFile;
import core.logging.Log;


public class Core {
	
	public static final JsonFile CONFIG = new JsonFile("vendor/config/app.conf").loadContentOrFail();
	public static final JsonFile USER = new JsonFile("vendor/config/user.conf").loadContent();
	
	public static final String MISSING_DATA = "Missing data !";
	public static boolean DEBUG = false;

	private JsonContent coreSettings;
	private JsonContent userSettings;
	private GameLoader gameManager;
	
	public Core() {
		this.coreSettings = CONFIG.getContent();
		loadCoreSettings();
		if (DEBUG) {
			Log.get().setLevel(Level.DEBUG);
			Log.get().warn("Application running in debug mode.");
		}
		this.userSettings = USER.getContent();
		
		this.gameManager = new GameLoader(this);
		
		Log.get().info("Core has successfully started...");
	}

	private void loadCoreSettings() {
		DEBUG = coreSettings.getAsBoolean("debug", DEBUG);
	}
	
	public String getUserLang() {
		return this.userSettings.getAsString("lang", "en");
	}
	
	public List<GameFacade> getGames() {
		return this.gameManager.getGames();
	}
	
	private boolean isValidGameNumber(Answer a) {
		GlobalValidator v = new GlobalValidatorBuilder()
			.type(Integer.class)
			.range(0, this.getGames().size())
			.build();
		return v.validate(a.getValue());
	}
	
	public GameFacade selectGame(Answer r) {
		if (isValidGameNumber(r)) {
			return new GameFacade(this.gameManager.getGame((int) r.getValue()));
		}
		return null;
	}

}
