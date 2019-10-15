package com.paysafe.app.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.paysafe.app.entity.MonitoringData;
import com.paysafe.app.entity.Status;

/**
 * This is the data access layer to reads from and writes data to
 * ConsolidatedMonitoringData which acts as a data source in our case
 * 
 * @author rallen
 *
 */
@Component
public class PaysafeMonitoringRepository {

	/**
	 * find if service has been monitored in the past
	 * 
	 * @param serverUrl
	 * @return
	 */
	public boolean findIfMonitoringDataExistsForServer(String serverUrl) {
		return ConsolidatedMonitoringData.monitoringData.containsKey(serverUrl);
	}

	/**
	 * Retrieves the monitoring data
	 * 
	 * @param serverUrl
	 *            - the server for which the monitoring data is requested
	 * @return monitoring data
	 */
	public MonitoringData getMonitoringDataForServer(String serverUrl) {
		return ConsolidatedMonitoringData.monitoringData.get(serverUrl);
	}

	/**
	 * start or stop service monitoring
	 * 
	 * @param serverUrl
	 *            - the service for which monitoring flag has to be updated
	 * @param monitoring
	 *            - true/false if the service has to be monitored or not
	 */
	public void setMonitoringForServer(String serverUrl, boolean monitoring) {
		if (findIfMonitoringDataExistsForServer(serverUrl)) {
			getMonitoringDataForServer(serverUrl).setMonitoring(monitoring);
		}
	}

	/**
	 * add a service which could be monitored in the future with monitoring flag
	 * false
	 * 
	 * @param serverUrl
	 *            - the service
	 */
	public void addNewServiceForMonitoring(String serverUrl) {
		ConsolidatedMonitoringData.monitoringData.put(serverUrl,
				MonitoringData.builder().statusData(new ArrayList<Status>()).monitoring(false).build());
	}

	/**
	 * add a service which could be monitored or not based on the monitoring
	 * flag passed by the user
	 * 
	 * @param serverUrl
	 *            - the service
	 * @param monitoring
	 *            - monitoring flag
	 */
	public void addNewServiceForMonitoring(String serverUrl, boolean monitoring) {
		ConsolidatedMonitoringData.monitoringData.put(serverUrl,
				MonitoringData.builder().statusData(new ArrayList<Status>()).monitoring(monitoring).build());
	}

	/**
	 * find if a service is being monitored or not
	 * 
	 * @param serverUrl
	 *            - the service
	 * @return true - if being monitored, false - if not being monitored
	 */
	public boolean findIfServerIsBeingMonitored(String serverUrl) {
		boolean monitoring = false;
		if (findIfMonitoringDataExistsForServer(serverUrl)) {
			monitoring = getMonitoringDataForServer(serverUrl).isMonitoring();
		}
		return monitoring;
	}

	/**
	 * add a new monitoring data for the service
	 * 
	 * @param serverUrl
	 *            - the service
	 * @param newStatus
	 *            - new status
	 */
	public void addNewStatusDataForaServer(String serverUrl, Status newStatus) {
		if (findIfMonitoringDataExistsForServer(serverUrl)) {
			List<Status> prevServiceStatus = getMonitoringDataForServer(serverUrl).getStatusData();
			prevServiceStatus.add(newStatus);
		}
	}

	/**
	 * retrieve all monitoring data for all the services
	 * 
	 * @return
	 */
	public Map<String, MonitoringData> getAllServersData() {
		return ConsolidatedMonitoringData.monitoringData;
	}

}
