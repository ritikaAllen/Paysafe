package com.paysafe.app.repository;

import java.util.HashMap;
import java.util.Map;

import com.paysafe.app.entity.MonitoringData;

import lombok.Data;

/**
 * 
 * @author rallen
 * 
 *         Description- This class is used to maintain the monitoring data for
 *         all the services. This acts as a data source.
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
