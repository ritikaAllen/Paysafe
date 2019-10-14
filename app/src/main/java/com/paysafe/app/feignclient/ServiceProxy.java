package com.paysafe.app.feignclient;

import com.paysafe.app.dto.ServiceResponse;

import feign.RequestLine;

/**
 * 
 * @author rallen
 * 
 *         Description - FeignClient
 *
 */
public interface ServiceProxy {

	/**
	 * 
	 * @return status READY if the service is available
	 */
	@RequestLine("GET")
	ServiceResponse verifyServerAvailability();

}
