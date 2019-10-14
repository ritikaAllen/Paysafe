package com.paysafe.app.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import rx.schedulers.Schedulers;

/**
 * 
 * @author rallen
 *
 *         This class contains the Scheduler Configuration
 */
@Configuration
public class SchedulerConfig {

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		System.out.println("In Task Scheduler");
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}

	@PreDestroy
	public void shutdown() {
		Schedulers.shutdown();
	}
}
