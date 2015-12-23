package com.hedwig.classfinder.model;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by patrick on 15/9/24.
 */
public class ClassItem {
    private String name;
    private String packageName;
    private String resFileName ;
    private JarFile fromFile;
    private JarEntry jarEntry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getResFileName() {
        return resFileName;
    }

    public void setResFileName(String resFileName) {
        this.resFileName = resFileName;
    }

    public JarFile getFromFile() {
        return fromFile;
    }

    public void setFromFile(JarFile fromFile) {
        this.fromFile = fromFile;
    }

    public JarEntry getJarEntry() {
        return jarEntry;
    }

    public void setJarEntry(JarEntry jarEntry) {
        this.jarEntry = jarEntry;
    }
}
