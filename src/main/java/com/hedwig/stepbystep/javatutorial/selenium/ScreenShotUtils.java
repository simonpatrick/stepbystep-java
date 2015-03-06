package com.hedwig.stepbystep.javatutorial.selenium;

import com.hedwig.stepbystep.javatutorial.io.FileHelper;
import com.thoughtworks.selenium.ScreenshotListener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by patrick on 15/3/6.
 *
 * @version $Id$
 */

public class ScreenShotUtils {

    private ScreenShotUtils(){}
    private static final String SCREENSHOT_PATH = "target/screenshots/";
    private static final String PIC_SUFFIX=".jpg";

    public static String takeScreenshot(WebDriver driver){
        File file = FileHelper.createFile(SCREENSHOT_PATH,generateFileName());
        File pic = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(pic,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private static String generateFileName(){
        return "screenshot-"+System.currentTimeMillis()+PIC_SUFFIX;
    }

}
