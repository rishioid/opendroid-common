package com.opendroid.common;

import org.apache.log4j.Level;

import android.os.Environment;
import android.util.Log;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class Logger {

	private static boolean SD_PRESENT = false;

	static {
		boolean isSDPresent = android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);
		final LogConfigurator lconfig = new LogConfigurator();
		if (isSDPresent) {
			SD_PRESENT = true;
			lconfig.setFileName(Environment.getExternalStorageDirectory() + "/"
					+ "religion" + ".txt");
			lconfig.setLevel("ALL", Level.ALL);
			lconfig.configure();
		} else {
			SD_PRESENT = false;
			// lconfig.setFileName(Environment.getDataDirectory()+"/"+Logger.class.getCanonicalName()+".txt");
		}

	}

	public static void info(Class<?> clas, String message) {
		if (SD_PRESENT) {
			org.apache.log4j.Logger.getLogger(clas).info(message);
		}
		Log.i(clas.getName(), message);
	}

	public static void error(Class<?> clas, String message) {
		if (SD_PRESENT) {
			org.apache.log4j.Logger.getLogger(clas).error(message);
		}
		Log.e(clas.getName(), message);
	}

	public static void error(Class<?> clas, Exception message) {
		if (SD_PRESENT) {
			org.apache.log4j.Logger.getLogger(clas).error(message);
		}
		Log.e(clas.getName(), message.getMessage());
	}

	public static void debug(Class<?> clas, String message) {
		if (SD_PRESENT) {
			org.apache.log4j.Logger.getLogger(clas).debug(message);
		}
		Log.d(clas.getName(), message);
	}

}
