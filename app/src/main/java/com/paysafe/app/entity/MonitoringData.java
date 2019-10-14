package com.paysafe.app.entity;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author rallen
 * 
 *         Description- This class is used to maintain the status data for a
 *         service being monitored
 *
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class MonitoringData {

	@JsonIgnore
	private boolean monitoring;
	private List<Status> statusData;

}
