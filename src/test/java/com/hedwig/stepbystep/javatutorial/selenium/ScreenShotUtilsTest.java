package com.hedwig.stepbystep.javatutorial.selenium;

import com.hedwig.stepbystep.javatutorial.selenium.webdriverlisteners.ScreenshotListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScreenShotUtilsTest {
    private WebDriver d = new FirefoxDriver();
    private EventFiringWebDriver driver ;

    @BeforeTest
    public void init(ITestContext context){
        driver = new EventFiringWebDriver(d);
        driver.register(new ScreenshotListener());
        System.out.println(context.getStartDate());
    }

    @Test
    public void testTakeScreenshot() throws Exception {
        driver.get("http://www.baidu.com");
        ScreenShotUtils.takeScreenshot(driver);
        try{
            driver.findElement(By.id("kw")).sendKeys("test");
            driver.findElement(By.id("q1"));
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}