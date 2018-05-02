package com.shensou.newpress.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * 
 */
public class TimerTaskUtils {

	private TimerTaskUtils() {
	}

	private static TimerTaskUtils instance;

	public synchronized static TimerTaskUtils getInstance() {
		if (instance == null) {
			instance = new TimerTaskUtils();
		}
		return instance;
	}

	private Timer timer;

	public void cancelTimer() {
		if (timer != null)
			timer.cancel();
		timer = null;
	}



	/**
	 * 
	 * @param task
	 * @param delay
	 * 
	 * @param period
	 * 
	 */
	public void startTimer(TimerTask task, long delay, long period) {
		cancelTimer();
		if (timer == null) {
			timer = new Timer();
			if (period < 0)
				timer.schedule(task, delay);
			else
				timer.schedule(task, delay, period);
		}
	}
}
