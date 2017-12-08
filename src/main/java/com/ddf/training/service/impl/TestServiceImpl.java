package com.ddf.training.service.impl;

import org.springframework.stereotype.Service;

import com.ddf.training.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Override
	public String printHelloWorld() {
		return "hello world!";
	}
	
}
