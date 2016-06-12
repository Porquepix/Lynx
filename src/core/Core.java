package core;

import org.apache.logging.log4j.LogManager;

import core.config.Config;
import core.config.DefaultSettings;
import core.json.JsonContent;


public class Core {
	
	public static final String LOGGER_DEBUG = "logger.debug";
	public static final String LOGGER_ERROR = "logger.error";
	
	private JsonContent settings;
	private boolean debug;
	
	public Core() {
		this.settings = Config.APP.getContent();
		loadSettings();
		LogManager.getLogger(LOGGER_DEBUG).info("Core has successfuly started...");
	}

	private void loadSettings() {
		this.debug = settings.getAsBoolean("debug", DefaultSettings.DEBUG);
		loadLoggerSettings();
	}

	private void loadLoggerSettings() {
		long ms = System.currentTimeMillis(); 
		JsonContent logSettings = settings.safeGetAsObject("log4j", DefaultSettings.LOG);
		System.setProperty("log4j.configurationFile", logSettings.getAsString("conf", ""));
		System.setProperty("logger.debug.path", logSettings.getAsString("debug", "") + ms);
		System.setProperty("logger.debug.active", this.debug ? "all" : "off");
		System.setProperty("logger.error.path", logSettings.getAsString("error", "") + ms);
	}

}
