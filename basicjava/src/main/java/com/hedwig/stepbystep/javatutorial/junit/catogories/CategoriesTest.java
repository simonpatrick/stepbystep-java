package com.hedwig.stepbystep.javatutorial.junit.catogories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CategoriesTest {
    private static final Logger logger = LogManager.getLogger(CategoriesTest.class.getName());

    public interface FastTests{}
    public interface SlowTests{}

    public class A{

        @Category(FastTests.class)
        @Test
        public void b(){
            logger.info("this is Class A");
        }
    }

    public class B{

        @Category(SlowTests.class)
        @Test
        public void b(){
            logger.info("this is class B");
        }
    }
}
