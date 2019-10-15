package com.paysafe.app.service;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.paysafe.app.dto.ServerAttributes;
import com.paysafe.app.dto.ServiceResponse;
import com.paysafe.app.entity.MonitoringData;
import com.paysafe.app.entity.Status;
import com.paysafe.app.feignclient.ServiceProxy;
import com.paysafe.app.repository.PaysafeMonitoringRepository;

import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author rallen
 * 
 *         Description- This is a service class to handle all the business logic
 *
 */
@Service
@RequiredArgsConstructor
@RequestScope
public class PaysafeService {

	@Autowired
	private TaskScheduler taskScheduler;

	ScheduledFuture<?> scheduleAtFixedRate;

	private String serverUrl;

	@Autowired
	private PaysafeMonitoringRepository repository;

	/**
	 * This service will start the monitoring of the service at the specified
	 * interval
	 * 
	 * @param serverInfo
	 */
	public void startServerMonitoring(ServerAttributes serverInfo) {

		serverUrl = serverInfo.getUrl();
		Long monitoringInterval = serverInfo.getInterval();

		System.out.println("\nRequest to start service monitoring from user\n");
		System.out.println("Start Monitoring Service with URL " + serverUrl + " at interval " + monitoringInterval);

		/*
		 * checks whether the service already has some monitoring data or not
		 * and updates the monitoring flag for the service
		 */
		if (repository.findIfMonitoringDataExistsForServer(serverUrl)) {
			/*
			 * enables the service for monitoring
			 */
			repository.setMonitoringForServer(serverUrl, true);

		} else {
			/*
			 * add the new service for monitoring with monitoring flag true
			 */
			repository.addNewServiceForMonitoring(serverUrl, true);
		}

		/*
		 * schedule the monitoring of the service at the specified interval
		 * provided as request input from user
		 */
		scheduleAtFixedRate = taskScheduler.scheduleAtFixedRate(() -> checkRemoteServer(serverUrl, monitoringInterval),
				monitoringInterval);
	}

	/**
	 * This service will stop the monitoring of the service whose url is passed
	 * as a request body input
	 * 
	 * @param serverUrl
	 */
	public void stopServerMonitoring(String serverUrl) {
		System.out.println("\nRequest to stop service monitoring from user");
		if (repository.findIfMonitoringDataExistsForServer(serverUrl)) {
			repository.setMonitoringForServer(serverUrl, false);

			System.out.println("Service not to be monitored now. Stop monitoring service");

		} else {
			System.out.println("Service not being monitered");
		}

	}

	/**
	 * This method will call the remote service to check its availability and
	 * update its status
	 * 
	 * @param serverUrl
	 * @param monitoringInterval
	 */
	public void checkRemoteServer(String serverUrl, Long monitoringInterval) {
		System.out.println("\nCheck if the service " + serverUrl + " is being monitored or not");
		Status newStatus;
		/*
		 * Check there is any monitoring data for the server
		 */
		boolean serviceBeingMonitored = repository.findIfServerIsBeingMonitored(serverUrl);

		/*
		 * check if the service has to be monitored
		 */
		if (serviceBeingMonitored) {
			System.out.println("Service being monitored. Keep monitoring service");
			/*
			 * build a feign client and call the remote service
			 */
			ServiceResponse serverResponse = Feign.builder().client(new ApacheHttpClient())
					.decoder(new JacksonDecoder()).target(ServiceProxy.class, serverUrl).verifyServerAvailability();
			/*
			 * identify the remote service status
			 */
			if (serverResponse.getStatus() != null) {
				System.out.println("Service is " + serverResponse.getStatus());
				newStatus = new Status(serverResponse.getStatus(), monitoringInterval);
			} else {
				System.out.println("Service Status is Unknown");
				newStatus = new Status("Unknown", monitoringInterval);
			}

			/*
			 * add new status data for the service being monitored
			 */
			repository.addNewStatusDataForaServer(serverUrl, newStatus);

		} else {
			/*
			 * stop monitoring of the service
			 */
			System.out.println("Service not to be monitored now. Stop monitoring service");
			scheduleAtFixedRate.cancel(true);
		}

	}

	/**
	 * This service will fetch the statistical status data for all the services
	 * monitored
	 * 
	 * @return
	 */
	public Map<String, MonitoringData> fetchServerStatistics() {
		return repository.getAllServersData();
	}

}
