package com.github.surpassm.common.tool.util;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mc
 * Create date 2019/9/24 11:52
 * Version 1.0
 * Description  高并发场景下System.currentTimeMillis()的性能问题的优化
 */
public class SystemClock {

	private static final String THREAD_NAME = "system.clock";
	private static final SystemClock MILLIS_CLOCK = new SystemClock(1);
	private final long precision;
	private final AtomicLong now;

	private SystemClock(long precision) {
		this.precision = precision;
		now = new AtomicLong(System.currentTimeMillis());
		scheduleClockUpdating();
	}

	public static SystemClock millisClock() {
		return MILLIS_CLOCK;
	}

	private void scheduleClockUpdating() {
		//保证资源不浪费
		ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1,
				new BasicThreadFactory.Builder().namingPattern(THREAD_NAME).daemon(true).build());
		scheduler.scheduleAtFixedRate(() ->
				now.set(System.currentTimeMillis()), precision, precision, TimeUnit.MILLISECONDS);
	}

	public long now() {
		return now.get();
	}
}
