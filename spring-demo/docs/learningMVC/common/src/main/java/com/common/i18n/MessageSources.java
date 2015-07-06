package com.springdemo.learningMVC.common.src.main.java.com.common.i18n;

import java.io.Serializable;

/**
 * {@code CustomMessageSource} simple factory.
 */
public final class MessageSources implements Serializable {

    private static final MessageSources INSTANCE = new MessageSources();

    private static final long serialVersionUID = 2712150473180538656L;

    public static CustomMessageSource getMessageSource() {
        return INSTANCE.messageSource;
    }

    private CustomMessageSource messageSource;

    private MessageSources() {}

    public static MessageSources getInstance() {
        return INSTANCE;
    }

    public void setMessageSource(CustomMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
