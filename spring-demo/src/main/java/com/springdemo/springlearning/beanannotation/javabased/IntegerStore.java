package com.springdemo.springlearning.beanannotation.javabased;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegerStore implements Store<Integer> {
    private static final Logger logger = LogManager.getLogger(IntegerStore.class.getName());
}
