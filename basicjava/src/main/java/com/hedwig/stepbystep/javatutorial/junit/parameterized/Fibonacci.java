package com.hedwig.stepbystep.javatutorial.junit.parameterized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Fibonacci {
    private static final Logger logger = LogManager.getLogger(Fibonacci.class.getName());

    public static int compute(int num){
        if(num<=1){
            return 1;
        }else{
            return compute(num-1)+compute(num);
        }
    }
}
