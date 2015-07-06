package com.springdemo.learningMVC.common.src.main.java.com.common.i18n;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * The {@code ResourceBundleMessageSource} extends implementation.
 */
public class CustomResourceBundleMessageResource extends ResourceBundleMessageSource
        implements CustomMessageSource {

    /**
     * Construct a new CustomResourceBundleMessageResource.
     */
    public CustomResourceBundleMessageResource() {
        super();
    }
}
