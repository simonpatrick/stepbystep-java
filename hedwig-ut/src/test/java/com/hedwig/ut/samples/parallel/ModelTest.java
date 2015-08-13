package com.hedwig.ut.samples.parallel;

import org.testng.annotations.Test;

@Test
public class ModelTest {

	public void sleep100() throws InterruptedException {
		Thread.sleep(100);
	}

	public void sleep200() throws InterruptedException {
		Thread.sleep(200);
	}

	public void sleep500() throws InterruptedException {
		Thread.sleep(500);
	}

	public void sleep1000() throws InterruptedException {
		Thread.sleep(1000);
	}
}
