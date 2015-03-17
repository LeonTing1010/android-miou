package com.datang.miou.utils;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Simply place a class like this in your Android applications classpath.
 */
public class Log4J {
	
	static {
		final LogConfigurator logC = new LogConfigurator();
		String filePath = SDCardUtils.getSystemLogPath();
		if (filePath != null) {
			File logFilePath = new File(filePath);
			if (!logFilePath.exists()) {
				logFilePath.mkdirs();
			}
			File logFile = new File(logFilePath.getAbsoluteFile()
					+ File.separator + "ou.log");
			logC.setFileName(logFile.getAbsolutePath());
			logC.setMaxBackupSize(20);
			logC.setMaxFileSize(2 * 1024 * 1024);
			logC.setRootLevel(Level.ERROR);
			// Set log level of a specific logger
			logC.setLevel("com.datang.outum", Level.WARN);
			logC.configure();
		}
	}

	public static boolean isEnabled(Logger log, Level level) {
		return log != null && log.getParent() != null
				&& level.isGreaterOrEqual(log.getParent().getLevel());
	}

}
