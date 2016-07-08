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
import core.logging.Loggers;
import core.logging.LynxLogger;
import core.namespace.Namespace;

public class Core {
    
    private static Core instance = null;

    private static final Namespace APP_CONF_FILE = new Namespace(
	    "vendor.config.app");
    private static final Namespace USER_CONF_FILE = new Namespace(
	    "vendor.config.user");

    public static final String MISSING_DATA = "Missing data !";
    public static boolean DEBUG = false;

    private final static LynxLogger logger = Loggers.getLogger(Core.class);

    private AppSettingsModel appSettings;
    private GameLoader gameManager;

    private Core() {
	logger.warn(">>> -------------------- >>> STARTING CORE <<< -------------------- <<<");
	loadCoreSettings();
	if (DEBUG) {
	    Loggers.setRootLevel(Level.DEBUG);
	    logger.warn("Application running in debug mode.");
	    logger.logUserConfiguration();
	}
	loadUserSettings();

	this.gameManager = new GameLoader(this);

	logger.info("Core has successfully started...");
    }

    private void loadUserSettings() {
	logger.info("User settings have been successfully set...");
    }

    private void loadCoreSettings() {
	BaseController<AppSettingsModel> appController = new BaseController<>(
		APP_CONF_FILE, AppSettingsModel.class);
	this.appSettings = appController.fetch();

	DEBUG = this.appSettings.getDebug();

	logger.info("Core settings have been successfully set...");
    }

    public String getUserLang() {
	return null; // this.userSettings.getAsString("lang", "en");
    }

    public List<GameFacade> getGames() {
	return this.gameManager.getGames();
    }

    private boolean isValidGameNumber(Answer a) {
	GlobalValidator v = new GlobalValidatorBuilder().type(Integer.class)
		.range(0, this.getGames().size()).build();
	return v.validate(a.getValue());
    }

    public GameFacade selectGame(Answer r) {
	if (isValidGameNumber(r)) {
	    return new GameFacade(this.gameManager.getGame((int) r.getValue()));
	}
	return null;
    }
    
    public AppSettingsModel getSettings() {
	return this.appSettings;
    }
    
    public static Core getInstance() {
	if (instance == null) {
	    instance = new Core();
	}
	return instance;
    }

}
