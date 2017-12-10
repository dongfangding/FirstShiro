package com.ddf.training.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddf.training.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TestService testService;
	
	
	@RequestMapping("/printHelloWorld")
	public String printHelloWorld() {
		log.info(testService.printHelloWorld());
		return "list";
	}
}
