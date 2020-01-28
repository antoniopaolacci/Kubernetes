package com.kube.example.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * URL http://localhost:port/swagger-ui.html 
 */

@Configuration
@EnableSwagger2
@SwaggerDefinition(
		info = @Info(
				title = "kube-webservice", 
				version = "0.0.1",
				description = "Kubernetes java microservice template.",
				contact = @Contact(
						name = "Antonio Paolacci",
						email = "ant.paolacci@gmail.com"
						),
						license = @License(
								name = "Apache 2.0",
								url = "http://www.apache.org/licenses/LICENSE-2.0"
								)

				),
				consumes = {"application/json", "application/xml"},
				produces = {"application/json", "application/xml"}	  
		)
public class SwaggerConfig {

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any())
		.build();
	}

}//end class
