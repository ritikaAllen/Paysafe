package com.paysafe.app.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author rallen
 * 
 * This class contains the Swagger configuration
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final ApiInfo apiInfo = new ApiInfo("Paysafe Server Monitoring API",
			"This API could be used to monitor the Paysafe API servers ", "1.0", "urn:tos", null, "Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo);
	}

}
