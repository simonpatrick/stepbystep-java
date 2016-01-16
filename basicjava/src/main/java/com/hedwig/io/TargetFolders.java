package com.hedwig.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 16/1/7.
 */
public class TargetFolders {

    public static List<File> getTargetFolder(File file){
        List<File> targetFiles = new ArrayList<File>();

        if(file.isDirectory() &&file.exists()){ //workspace is existing
            File[] files = file.listFiles();
            for (File sub : files) {
                if(sub.isDirectory()){
                    File target = new File(sub.getAbsolutePath()+"/target/classes");
                    if(target.exists()){
                        if(file.isDirectory()){
                            targetFiles.add(target);
                        }
                    }
                }
            }
        }

        return targetFiles;
    }



    private static boolean isTargetFolder(File file){
        return file.getName().contains("/target");
    }

    public static void main(String[] args) {
        List<File> targets =TargetFolders.getTargetFolder(new File("/Users/patrick/workspace/works/self-repos/stepbystep-java"));
        for (File target : targets) {
            System.out.println(target.getAbsolutePath());
        }

    }
}
