package com.hedwig.stepbystep.javatutorial.jbehave.runner;//package com.hedwig.stepbystep.javatutorial.jbehave.runner;
//
//import org.jbehave.core.embedder.Embedder;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//@RunWith(Parameterized.class)
//public class StoryRunner {
//
//	public WebDriver driver;
//	public String os;
//	public String osVersion;
//	public String browser;
//	public String browserVersion;
//
//	public StoryRunner(String os, String osVersion, String browser,
//			String browserVersion) {
//		this.os = os;
//		this.osVersion = osVersion;
//		this.browser = browser;
//		this.browserVersion = browserVersion;
//	}
//
//	@Before
//	public void setUp() {
//	        String BS_USER = System.getenv("bamboo_BS_USER");
//	        String BS_AUTH_KEY = System.getenv("bamboo_BS_AUTH_KEY");
//	        String url = "http://"+BS_USER+":"+BS_AUTH_KEY+"@hub.browserstack.com/wd/hub/";
//		DesiredCapabilities dc = new DesiredCapabilities();
//		dc.setCapability("os", os);
//		dc.setCapability("os_version", osVersion);
//		dc.setCapability("browser", browser);
//		dc.setCapability("browser_version", browserVersion);
//		dc.setCapability("browserstack.debug", "true");
//		dc.setCapability("build", "jbehave_tests_lan_com");
//		try {
//			System.out.println("Creating driver using key: "+BS_AUTH_KEY);
//			driver = new RemoteWebDriver(new URL(url), dc);
//		} catch (MalformedURLException e) {
//			System.err.println("Exception in url: " + e.getMessage());
//		}
//	}
//
//	@After
//	public void tearDown() {
//		driver.quit();
//	}
//
//	@Parameters
//	public static Collection<Object[]> data() {
//		Object[][] data = new Object[][] {
//				{ "windows", "7", "chrome", "29.0" },
//				{ "windows", "xp", "firefox", "23.0" } };
//		return Arrays.asList(data);
//	}
//
//	@Test
//	public void runStories() {
//		Embedder storyEmbedder = new StoryEmbedder(driver);
//		List<String> storyPaths = Arrays.asList("country_selector.story");
//		storyEmbedder.runStoriesAsPaths(storyPaths);
//	}
//
//}
