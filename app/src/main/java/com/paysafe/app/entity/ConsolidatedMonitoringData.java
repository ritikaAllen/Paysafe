package com.paysafe.app.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 
 * @author rallen
 * 
 *         Description- This class is used to maintain the monitoring data for
 *         all the services
 *
 */
@Data
public class ConsolidatedMonitoringData {

	/*
	 * Map a) key- service url being monitored b) value- status data of the
	 * service
	 * 
	 * At each interval when the service is hit, its status is added
	 */
	public final static Map<String, MonitoringData> monitoringData = new HashMap<>();

}
