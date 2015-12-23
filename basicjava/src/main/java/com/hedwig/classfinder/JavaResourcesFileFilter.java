package com.hedwig.classfinder;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;
import java.util.List;


/**
 * Created by patrick on 15/9/24.
 */
public class JavaResourcesFileFilter implements FileFilter{
    private static List<String> ACCEPT_EXTS = Lists.newArrayList("jar", "war", "ear");

    @Override
    public boolean accept(@Nonnull File pathname) {
       if(pathname.isDirectory()) return true;
       String fName = pathname.getName();
       return ACCEPT_EXTS.contains(Files.getFileExtension(fName).toLowerCase());
    }
}
