package com.hedwig.stepbystep.javatutorial.io;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件操作工具类
 */
public class FileHelper {
    private static final Logger logger = LogManager.getLogger(FileHelper.class.getName());

    /**
     * 数据写入到文件
     * @param path
     * @param text
     * @throws IOException
     */
    public static void writeToFile(String path,String text)  {

        File file = new File(path);
        try {
            if(!file.exists()) file.createNewFile();
            ArrayList<String> texts = Lists.newArrayList(text);
            FileUtils.writeLines(file, texts, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path){
        File file = new File(path);
        if(file.exists()) file.delete();
    }


    /**
     * Read file from class Path
     * @param classPathLocation
     * @return string value of the file
     */
    public static String readFileToString(String classPathLocation) {

        try {
            String filePath = FileHelper.class.getClassLoader().getResource(classPathLocation).getPath();
            return FileUtils.readFileToString(new File(filePath));
        } catch (Exception e) {
            logger.error("result={}",e.getStackTrace());
        }

        return StringUtils.EMPTY;
    }

    /**
     *
     * @param dir
     * @param fileName
     * @return
     */
    public static File createFile(String dir, String fileName) {
        new File(dir).mkdir();
        File file = new File(dir+fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File createFile(String fileName) {
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void checkIfSuitableFile(String path, String ...fileSuffix){

        for (String s : fileSuffix) {
            if(path.endsWith(s)) return;
        }

        throw  new RuntimeException("File suffix "+fileSuffix+" is not correct,please check the file");

    }

    public static File createDir(String dir) {
        File d = new File(dir);
        d.mkdir();
        return d;
    }
}
