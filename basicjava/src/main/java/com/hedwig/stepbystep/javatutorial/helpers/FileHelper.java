package com.hedwig.stepbystep.javatutorial.helpers;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 文件操作工具类
 */
public class FileHelper {

    private static final Logger logger = LogManager.getLogger(FileHelper.class.getName());
    private static final String classPath = FileHelper.class.getClassLoader().getResource("").getPath();
    /**
     * 数据写入到文件
     * @param path
     * @param text
     * @throws IOException
     */
    public static void writeToFile(String path,String text) throws IOException {

        File file = new File(path);
        if(!file.exists()) file.createNewFile();
        ArrayList<String> texts = Lists.newArrayList(text);
        FileUtils.writeLines(file, texts, true);
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

    public static File createFileInClassPath(String dir, String fileName) {

        createDir(classPath+dir);
        return createFile(classPath+dir+fileName);
    }

    /**
     *
     * @param dir
     * @param fileName
     * @return
     */
    public static File createFile(String dir, String fileName) {
        createDir(dir);
        return createFile(dir+fileName);
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

    /**
     * check file if an appropriate file with correct extension name
     * @param path
     * @param fileSuffix
     */

    public static void checkIfSuitableFile(String path, String ...fileSuffix){

        for (String s : fileSuffix) {
            if(path.endsWith(s)) return;
        }
        throw  new RuntimeException(path+" ,File suffix is not correct,please check the file");

    }

    public static File createDir(String dir) {
        File d = new File(dir);
        if(!d.exists()){
            d.mkdir();
        }
        return d;
    }


    public static boolean existsInClasspath(final String fileName) {
        return getResourceFromClasspath(fileName) != null;
    }


    public static URL getResourceFromClasspath(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResource(fileName);
    }

    public static String getFilePath(final String fileName) {
        if (existsInClasspath(fileName)) {
            return getResourceFromClasspath(fileName).getPath();
        }
        return getPathForSystemFile(fileName);
    }

    public static String getPathForSystemFile(final String fileName) {
        File file = new File(fileName);
        return file.getPath();
    }
}
