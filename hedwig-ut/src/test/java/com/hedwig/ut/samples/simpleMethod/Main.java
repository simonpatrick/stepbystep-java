package com.hedwig.ut.samples.simpleMethod;

import org.testng.TestNG;

public class Main {

	public static void main(String[] args) {
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] {
				DateUtilsTest.class,
				ExceptionTest.class
		});
		testng.run();
	}
}