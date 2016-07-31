package com.lynx.core;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.lynx.core.game.GameFacade;
import com.lynx.core.game.GameLoader;
import com.lynx.core.json.controller.BaseController;
import com.lynx.core.json.model.AppSettingsModel;
import com.lynx.core.json.model.UserSettingsModel;
import com.lynx.core.logging.Loggers;
import com.lynx.core.logging.LynxLogger;
import com.lynx.core.namespace.Namespace;
import com.lynx.core.translation.FileTranslator;
import com.lynx.core.translation.Translator;

public class Core {

    private static class SingletonHolder {
	private static final Core instance = new Core();
    }

    public enum TranslationKey {
	MISSING_DATA;
    }

    private static final Namespace APP_CONF_FILE = new Namespace("vendor.config.app");
    private static final Namespace USER_CONF_FILE = new Namespace("vendor.config.user");
    private static final Namespace CORE_TRANSLATOR_ROOT = new Namespace("vendor.lang");
    private final static LynxLogger logger = Loggers.getLogger(Core.class);

    private AppSettingsModel appSettings;
    private UserSettingsModel userSettings;
    private Translator tranlastor;
    private GameLoader gameManager;

    private Core() {
	logger.warn(">>> -------------------- >>> STARTING CORE <<< -------------------- <<<");
	loadAppSettings();
	if (appSettings.getDebug()) {
	    Loggers.setRootLevel(Level.DEBUG);
	    logger.warn("Application running in debug mode.");
	    logger.logUserConfiguration();
	}
	loadUserSettings();

	this.tranlastor = new FileTranslator(CORE_TRANSLATOR_ROOT.append(userSettings.getLang())
	        .append("core"));
	this.gameManager = new GameLoader(this);

	logger.info("Core has successfully started...");
    }

    public AppSettingsModel getAppSettings() {
	return this.appSettings;
    }

    private void loadAppSettings() {
	BaseController<AppSettingsModel> appController = new BaseController<>(APP_CONF_FILE,
	        AppSettingsModel.class);
	this.appSettings = appController.fetch();
	logger.info("Core settings have been successfully set...");
    }

    public UserSettingsModel getUserSettings() {
	return this.userSettings;
    }

    private void loadUserSettings() {
	BaseController<UserSettingsModel> userController = new BaseController<>(USER_CONF_FILE,
	        UserSettingsModel.class);
	this.userSettings = userController.fetch();
	logger.info("User settings have been successfully set...");
    }

    public Translator getTranslator() {
	return this.tranlastor;
    }

    public String translate(Core.TranslationKey key) {
	return tranlastor.translate(key.name().toLowerCase());
    }

    public List<GameFacade> getGames() {
	return this.gameManager.getGames();
    }

    public static Core getInstance() {
	return SingletonHolder.instance;
    }

}
