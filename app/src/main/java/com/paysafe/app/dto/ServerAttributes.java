package com.paysafe.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author rallen
 * 
 *         This class is used to capture the input from client
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Server attributes required for monitoring it")
@JsonFilter("ServerAttributesFilter")
public class ServerAttributes {

	/*
	 * service url to be monitored
	 */
	@NotNull
	@NotEmpty
	@ApiModelProperty(notes = "Server url that has to be monitored or not should always be provided to start or stop monitoring the service. URL cannot be null or empty.")
	private String url;
	/*
	 * interval at which the service has to be monitored
	 */
	private Long interval;

}
