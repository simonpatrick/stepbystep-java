package com.hedwig.stepbystep.javatutorial;

import com.hedwig.stepbystep.javatutorial.testng.listener.SimpleWebDriverScreenShotTestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.internal.thread.TestNGThread;

/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */


public class BaseWebTest {
    protected WebDriver d = new FirefoxDriver();
    protected EventFiringWebDriver driver ;
    protected AbstractWebDriverEventListener eventListener;

    @BeforeClass
    public void init(){
        initEventListener();
    }

    @BeforeSuite
    public void initEventListener(){
        for (ITestListener listener : TestNG.getDefault().getTestListeners()) {
            if(listener instanceof SimpleWebDriverScreenShotTestListener){
                eventListener=(SimpleWebDriverScreenShotTestListener)listener;
                return;
            }
        }
        eventListener = new SimpleWebDriverScreenShotTestListener();
        TestNG.getDefault().addListener((ITestListener)eventListener);
    }


    @BeforeTest
    public void init(ITestContext context){
        //driver = new EventFiringWebDriver(d);
        //driver.register(new ScreenshotListener());
        driver = new EventFiringWebDriver(d);
        driver.register(eventListener);
    }


    @AfterTest
    public void after(){

        driver.quit();
    }

    @AfterSuite
    public void clearup(){

        driver.quit();
    }

    @AfterClass
    public void clearup1(){
        driver.quit();
    }
}
