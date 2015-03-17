package com.datang.miou.testplan.task.cases;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

//import com.datang.miou.testplan.exception.TaskRunException;

import android.util.Log;

public class AppThread extends Thread {
	public static final String DEFAULT_NAME = "AppThread";
	private static volatile boolean debugLifecycle = false;
	private static final AtomicInteger created = new AtomicInteger();
	private static final AtomicInteger alive = new AtomicInteger();
	//private final Logger Log = Logger.getLogger(AppThread.class);

	public AppThread(Runnable r) {
		this(r, DEFAULT_NAME);
	}

	public AppThread(Runnable runnable, String name) {
		super(runnable, name + "-" + created.incrementAndGet());
		setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				Log.w("UNCAUGHT in thread " + t.getName(), e);
			}
		});
	}

	public void run() {
		// Copy debug flag to ensure consistent value throughout.
		boolean debug = debugLifecycle;
		if (debug)
			Log.d(DEFAULT_NAME,"Created " + getName());
		try {
			alive.incrementAndGet();
			super.run();
		} catch(RuntimeException e) {
			e.printStackTrace();
		}
		finally {
			alive.decrementAndGet();
			//if (debug)
				//Log.debug("Exiting " + getName());
		}
	}

	public static int getThreadsCreated() {
		return created.get();
	}

	public static int getThreadsAlive() {
		return alive.get();
	}

	public static boolean getDebug() {
		return debugLifecycle;
	}

	public static void setDebug(boolean b) {
		debugLifecycle = b;
	}

}
