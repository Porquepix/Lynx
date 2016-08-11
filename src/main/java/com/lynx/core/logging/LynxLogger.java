package com.lynx.core.logging;

import static com.lynx.core.logging.LoggerMessageFormat.*;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lynx.core.Version;

public class LynxLogger {

	private Logger logger;

	protected LynxLogger(Class<?> clazz) {
		this.logger = LogManager.getLogger(clazz);
	}

	public Level getLevel() {
		return logger.getLevel();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public void trace(String msg, Object... params) {
		trace(msg, null, params);
	}

	public void trace(String msg, Throwable cause, Object... params) {
		if ( isTraceEnabled() ) {
			logger.log(Level.TRACE, format(msg, params), cause);
		}
	}

	public void debug(String msg, Object... params) {
		debug(msg, null, params);
	}

	public void debug(String msg, Throwable cause, Object... params) {
		if ( isDebugEnabled() ) {
			logger.log(Level.DEBUG, format(msg, params), cause);
		}
	}

	public void info(String msg, Object... params) {
		info(msg, null, params);
	}

	public void info(String msg, Throwable cause, Object... params) {
		if ( isInfoEnabled() ) {
			logger.log(Level.INFO, format(msg, params), cause);
		}
	}

	public void warn(String msg, Object... params) {
		warn(msg, null, params);
	}

	public void warn(String msg, Throwable cause, Object... params) {
		if ( isWarnEnabled() ) {
			logger.log(Level.WARN, format(msg, params), cause);
		}
	}

	public void error(String msg, Object... params) {
		error(msg, null, params);
	}

	public void error(String msg, Throwable cause, Object... params) {
		if ( isErrorEnabled() ) {
			logger.log(Level.ERROR, format(msg, params), cause);
		}
	}

	public void logUserConfiguration() {
		if ( isInfoEnabled() ) {
			// Lynx
			info("Lynx version: {}", Version.CURRENT);

			// OS
			info("Operating system (name): {}", System.getProperty("os.name"));
			info("Operating system (version): {}", System.getProperty("os.version"));
			info("Operating system (arch): {}", System.getProperty("os.arch"));

			// Java
			info("Java version: {}", System.getProperty("java.version"));
			info("JVM version: {}", System.getProperty("java.vm.version"));
			info("Runtime Environment vendor: {}", System.getProperty("java.vendor"));
			info("JVM implementation vendor: {}", System.getProperty("java.vm.vendor"));

			// Hardware
			info("Available processors (cores): {}", Runtime.getRuntime().availableProcessors());
			info("Free memory (bytes): {}", Runtime.getRuntime().freeMemory());
			long maxMemory = Runtime.getRuntime().maxMemory();
			info("Maximum memory (bytes): {}", (maxMemory == Long.MAX_VALUE ? "no limit"
			        : maxMemory));
			info("Total memory available to JVM (bytes): {}", Runtime.getRuntime().totalMemory());
			File[] roots = File.listRoots();
			for ( File root : roots ) {
				info("File system root: {}", root.getAbsolutePath());
				info("Total space (bytes): {}", root.getTotalSpace());
				info("Free space (bytes): {}", root.getFreeSpace());
				info("Usable space (bytes): {}", root.getUsableSpace());
			}
		}
	}

}
