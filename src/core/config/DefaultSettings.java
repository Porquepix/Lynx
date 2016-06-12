package core.config;

import core.json.JsonContent;

public class DefaultSettings {
	
	public static final JsonContent LOG = new JsonContent();
	static {
		LOG.put("conf", "vendor/config/log4j2.conf");
		LOG.put("debug", "vendor/logs/debug.log.");
		LOG.put("error", "vendor/logs/error.log.");
	}
	
	public static final boolean DEBUG = false;

}
