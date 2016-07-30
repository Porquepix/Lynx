package core.logging;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Loggers {

    private static final String DEFAULT_CONF = "vendor/config/log4j2.conf";

    private static Level LEVEL = Level.WARN;
    private static Map<Class<?>, LynxLogger> instances = new HashMap<>();

    static {
	System.setProperty("log4j.configurationFile", DEFAULT_CONF);
	setRootLevel(LEVEL);
    }

    public static Level getRootLevel() {
	return LEVEL;
    }

    public static void setRootLevel(Level level) {
	LEVEL = level;

	LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	Configuration config = ctx.getConfiguration();
	LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
	loggerConfig.setLevel(level);
	ctx.updateLoggers();
    }

    public static LynxLogger getLogger(Class<?> clazz) {
	if (!instances.containsKey(clazz)) {
	    instances.put(clazz, new LynxLogger(clazz));
	}
	return instances.get(clazz);
    }

}
