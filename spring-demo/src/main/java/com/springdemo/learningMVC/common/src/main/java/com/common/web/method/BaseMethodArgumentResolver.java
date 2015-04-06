package com.springdemo.learningMVC.common.src.main.java.com.common.web.method;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

/**
 */
public abstract class BaseMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Nonnull
    protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, String> variables =
                (Map<String, String>) request.getAttribute(
                        URI_TEMPLATE_VARIABLES_ATTRIBUTE, SCOPE_REQUEST);
        return (variables != null) ? variables : Collections.<String, String>emptyMap();
    }
}
