package com.paysafe.app.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.paysafe.app.dto.ServerAttributes;
import com.paysafe.app.entity.MonitoringData;
import com.paysafe.app.service.PaysafeService;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author rallen
 * 
 *         Description- This is a controller to handle all the client requests
 *
 */
@Data
@RestController
@RequestMapping("/paysafe-api/monitor")
@AllArgsConstructor
@Import(FeignClientsConfiguration.class)
public class PaysafeController {

	@Autowired
	private PaysafeService service;

	/**
	 * This controller will handle the monitoring request for a service at a
	 * particular monitoring interval
	 * 
	 * @param serverMonitoringInfo
	 * @return
	 */
	@PostMapping("/start")
	public MappingJacksonValue startServerMonitoring(@RequestBody @Valid ServerAttributes serverMonitoringInfo) {

		service.startServerMonitoring(serverMonitoringInfo);

		Resource<ServerAttributes> resource = new Resource<ServerAttributes>(serverMonitoringInfo);
		ControllerLinkBuilder linkToStopMonitoringServer = linkTo(
				methodOn(this.getClass()).stopServerMonitoring(serverMonitoringInfo));
		ControllerLinkBuilder linkToFetchServerStatus = linkTo(methodOn(this.getClass()).getServerStatus());
		resource.add(linkToStopMonitoringServer.withRel("stop-service-monitoring"),
				linkToFetchServerStatus.withRel("server-status"));

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("ServerAttributesFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(resource);
		mapping.setFilters(filterProvider);
		return mapping;
	}

	/**
	 * This controller will handle the stop monitoring request for a service
	 * 
	 * @param serverMonitoringInfo
	 * @return
	 */
	@PostMapping("/stop")
	public MappingJacksonValue stopServerMonitoring(@RequestBody @Valid ServerAttributes serverMonitoringInfo) {
		service.stopServerMonitoring(serverMonitoringInfo.getUrl());

		Resource<ServerAttributes> resource = new Resource<ServerAttributes>(serverMonitoringInfo);
		ControllerLinkBuilder linkToStartMonitoringServer = linkTo(
				methodOn(this.getClass()).startServerMonitoring(serverMonitoringInfo));
		ControllerLinkBuilder linkToFetchServerStatus = linkTo(methodOn(this.getClass()).getServerStatus());
		resource.add(linkToStartMonitoringServer.withRel("start-service-monitoring"),
				linkToFetchServerStatus.withRel("server-status"));

		Set<String> fieldList = new HashSet<>(Arrays.asList("url"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fieldList);
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("ServerAttributesFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(resource);
		mapping.setFilters(filterProvider);
		return mapping;
	}

	/**
	 * This controller will handle the request to fetch the monitoring data for
	 * all the services monitored
	 * 
	 * @return
	 */
	@GetMapping("/status")
	public ResponseEntity<Map<String, MonitoringData>> getServerStatus() {
		Map<String, MonitoringData> allServiceStatusData = service.fetchServerStatistics();

		return ResponseEntity.ok(allServiceStatusData);
	}

}
