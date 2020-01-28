package com.kube.example.webapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.kube.example.webapp.dto.WebDto;

@RestController
@RequestMapping("/")
public class WebController {

	private static final Logger log = LoggerFactory.getLogger(WebController.class);
	
	private final RestTemplate restTemplate;
	private final String serviceHost;
	
	@Value("${database.used}")
	private String used;
	
	@Value("${database.cheaper}")
	private String cheaper;
	
	@Value("${database.powerful}")
	private String powerful;
	
	@Value("${database.popular}")
	private String popular;

	public WebController(RestTemplate restTemplate, @Value("${service.host}") String serviceHost) {
		this.restTemplate = restTemplate;
		this.serviceHost = serviceHost;
	}

	@RequestMapping("")
	public ModelAndView index() {
		this.printEnvVariable();
		return new ModelAndView("index");
	}

	private void printEnvVariable() {
		log.info("Environment variable: used="+used+" cheaper="+cheaper+" powerful="+powerful+" popular="+popular);
	}

	@GetMapping("/classes")
	public ResponseEntity<List<WebDto>> listClasses() {
		return restTemplate.exchange("http://"+serviceHost+"/class", 
									 HttpMethod.GET, 
									 null, 
									 new ParameterizedTypeReference<List<WebDto>>() {});
	}
	
}//end class
