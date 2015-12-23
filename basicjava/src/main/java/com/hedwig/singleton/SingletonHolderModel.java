package com.hedwig.singleton;

public class SingletonHolderModel {
    private static class SingletonHolder {
        private static final SingletonHolderModel INSTANCE = new SingletonHolderModel();
    }

    private SingletonHolderModel() {
    }

    public static final SingletonHolderModel getInstance() {
        return SingletonHolder.INSTANCE;
    }
}