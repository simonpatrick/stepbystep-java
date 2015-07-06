package com.springdemo.learningMVC.data.src.main.java.com.data.jms.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

public class CustomMappingJackson2MessageConverter
        extends MappingJackson2MessageConverter {

    public CustomMappingJackson2MessageConverter() {
        super();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.findAndRegisterModules();
        setObjectMapper(objectMapper);
    }
}
