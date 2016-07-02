package core;

import java.util.List;

import org.apache.logging.log4j.Level;

import core.game.Answer;
import core.game.GameLoader;
import core.game.facade.GameFacade;
import core.game.validation.GlobalValidator;
import core.game.validation.GlobalValidatorBuilder;
import core.json.controller.BaseController;
import core.json.model.AppSettingsModel;
import core.key.FileKey;
import core.logging.Log;


public class Core {
	
	private static final FileKey APP_CONF_FILE = new FileKey("vendor.config.app");
	private static final FileKey USER_CONF_FILE = new FileKey("vendor.config.user");
	
	public static final String MISSING_DATA = "Missing data !";
	public static boolean DEBUG = false;

	private AppSettingsModel appSettings;
	private GameLoader gameManager;
	
	public Core() {
		Log.get().warn(">>> -------------------- >>> STARTING CORE <<< -------------------- <<<");
		loadCoreSettings();
		if (DEBUG) {
			Log.get().setLevel(Level.DEBUG);
			Log.get().warn("Application running in debug mode.");
			Log.get().logUserConfiguration();
		}
		loadUserSettings();
		
		this.gameManager = new GameLoader(this);
		
		Log.get().info("Core has successfully started...");
	}

	private void loadUserSettings() {
		Log.get().info("User settings have been successfully set...");
    }

	private void loadCoreSettings() {
		BaseController<AppSettingsModel> appController = new BaseController<>(APP_CONF_FILE, AppSettingsModel.class);
		this.appSettings = appController.fetch();
		
		DEBUG = this.appSettings.getDebug();
		
		Log.get().info("Core settings have been successfully set...");
	}
	
	public String getUserLang() {
		return null; //this.userSettings.getAsString("lang", "en");
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
