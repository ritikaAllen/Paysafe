package com.paysafe.app.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author rallen
 *
 *         Description- This class maintains the attributes which create a
 *         status data for a service being monitored 
 *         
 *         status - The status of the service 
 *         time - The time the status is recorded
 *         monitoringInterval - The monitoring interval for the service being  monitored
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class Status {

	/*
	 * status of the service
	 */
	private String status;
	/*
	 * The time the status is recorded
	 */
	private LocalDateTime time;
	/*
	 * The monitoring interval for the service being  monitored
	 */
	private Long monitoringInterval;

	/**
	 * 
	 * @param serverStatus
	 * @param intervalOfMonitoring
	 */
	public Status(String serverStatus, Long intervalOfMonitoring) {
		setStatus(serverStatus);
		setTime(LocalDateTime.now());
		setMonitoringInterval(intervalOfMonitoring);
	}

	/**
	 * 
	 * @param serverStatus
	 */
	public void setStatus(String serverStatus) {
		if (serverStatus.equals("READY")) {
			status = "READY/UP";
		} else {
			status = "DOWN";
		}
	}

}
