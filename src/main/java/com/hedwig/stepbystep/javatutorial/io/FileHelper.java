package com.hedwig.stepbystep.javatutorial.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by patrick on 15/3/6.
 *
 * @version $Id$
 */


public class FileHelper {
    private FileHelper(){}

    public static File createFile(String dir,String name){
        new File(dir).mkdir();
        File file = new File(dir+name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
