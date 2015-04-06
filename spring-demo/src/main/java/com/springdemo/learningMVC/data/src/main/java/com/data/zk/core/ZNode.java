package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;

import java.util.List;
import java.util.Objects;

public interface ZNode<T> {
    public static String ROOT = "/";
    public static String HTTPRESOURCE_IND="/";

    /**
     * check if it is a root path
     * @param path
     * @return if root ，return{@code true},otherwise return {@code false}
     */
    static boolean isRootPath(String path){
        return path!=null&&(ROOT.equals(path))||
                (path.startsWith(HTTPRESOURCE_IND))&&path.length()>=1
                        &&!path.substring(1).contains(HTTPRESOURCE_IND);
    }

    static String getParentPath(String path){
        Objects.requireNonNull(path,"path");
        if(isRootPath(path)){
            return ROOT;
        }
        return path.substring(0,path.lastIndexOf(HTTPRESOURCE_IND));
    }

    public String getPath();

    public T getData();

    public long getCzxid();

    public long getMzxid();

    public long getCtime();

    public long getMtime();

    public int getVersion();

    public int getCversion();

    public int getAversion();

    public long getEphemeralOwner();

    public int getDataLength();

    public int getNumChildren();

    public long getPzxid();

    public ZNode<?> getParent();

    public List<ZNode<?>> getChildren();
}
