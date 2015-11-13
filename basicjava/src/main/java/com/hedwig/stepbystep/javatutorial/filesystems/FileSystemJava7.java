package com.hedwig.stepbystep.javatutorial.filesystems;

import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by patrick on 15/11/13.
 */
public class FileSystemJava7 {

    @Test
    public void testGetPath(){
        String path="pages";
        Path nPath = Paths.get(path);
        System.out.println(nPath);
    }
}
