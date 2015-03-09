package com.hedwig.stepbystep.javatutorial;

import com.hedwig.stepbystep.javatutorial.selenium.webdriverlisteners.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */


public class BaseWebTest {
    protected WebDriver d = new FirefoxDriver();
    protected EventFiringWebDriver driver ;

    @BeforeTest
    public void init(){
        driver = new EventFiringWebDriver(d);
        driver.register(new ScreenshotListener());
    }


    @AfterTest
    public void after(){
        driver.quit();
    }
}
