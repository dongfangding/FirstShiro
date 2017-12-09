package com.ddf.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ddf.training.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestService testService;
	
	
	@RequestMapping("/printHelloWorld")
	public String printHelloWorld() {
		System.out.println(testService.printHelloWorld());
		return "list";
	}
}
