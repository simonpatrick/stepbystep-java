package com.hedwig.stepbystep.javatutorial.webtest.pageobjects.pagefactory;

import com.hedwig.stepbystep.javatutorial.BaseWebTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BaiduHomePageFactoryTest extends BaseWebTest {

    @Test
    public void testBaiduPageFactory(){
        driver.get("http://www.baidu.com");
        BaiduHomePage page =  PageFactory.initElements(super.driver, BaiduHomePage.class);
        page.getKeyword().sendKeys("test");
        page.getSubmit().click();
        page.getVoiceRec().click();

        assertNotNull(super.driver.getTitle());
    }

}