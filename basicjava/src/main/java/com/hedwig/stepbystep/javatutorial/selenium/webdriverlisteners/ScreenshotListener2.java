package com.hedwig.stepbystep.javatutorial.selenium.webdriverlisteners;

import com.hedwig.stepbystep.javatutorial.selenium.ScreenShotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

/**
 * Created by patrick on 15/3/6.
 *
 * @version $Id$
 */


public class ScreenshotListener2 extends AbstractWebDriverEventListener {
    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        ScreenShotUtils.takeScreenshot(driver);
    }
}
