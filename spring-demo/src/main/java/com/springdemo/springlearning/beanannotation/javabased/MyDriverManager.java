package com.springdemo.springlearning.beanannotation.javabased;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyDriverManager {
	private static final Logger logger = LogManager.getLogger(MyDriverManager.class.getName());
	public MyDriverManager(String url, String userName, String password) {
		System.out.println("url : " + url);
		System.out.println("userName: " + userName);
		System.out.println("password: " + password);
	}


}