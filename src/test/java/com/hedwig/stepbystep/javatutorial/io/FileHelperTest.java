package com.hedwig.stepbystep.javatutorial.io;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class FileHelperTest {
    private static final String SCREENSHOT_PATH = "target/screenshots/";
    private static final String PIC_SUFFIX=".jpg";

    @Test
    public void testCreateFile() throws Exception {
        String path = "screenshot-"+System.currentTimeMillis()+PIC_SUFFIX;
        File file = FileHelper.createFile(SCREENSHOT_PATH,path);
        Assert.assertNotNull(file);
    }
}