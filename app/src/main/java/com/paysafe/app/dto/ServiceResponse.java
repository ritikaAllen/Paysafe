package com.paysafe.app.dto;

import lombok.Data;

/**
 * 
 * @author rallen
 * 
 *         Description- This class is used to capture the response from the
 *         remote service which is used to verify a service availability
 *
 */
@Data
public class ServiceResponse {

	private String status;
}
