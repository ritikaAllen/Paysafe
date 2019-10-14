package com.paysafe.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author rallen
 *
 */
@SpringBootApplication
@EnableFeignClients("com.paysafe.app")
@EnableScheduling
@EnableHystrix
public class PaysafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaysafeApplication.class, args);
	}

}
