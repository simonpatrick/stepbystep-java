package com.hedwig.stepbystep.javatutorial.junit.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;


/*All test methods are annotated with @Test. Unlike JUnit3 tests,
    you do not need to prefix the method name with "test" (and usually don't)
        Do not have your test classes extend junit.framework.TestCase (directly or indirectly).
        Usually, tests with JUnit4 do not need to extend anything (which is good, since Java does not support multiple inheritance)
        Do not use any classes in junit.framework or junit.extensions*/

public class SimpleJUnitTests {
    private static final Logger logger = LogManager.getLogger(SimpleJUnitTests.class.getName());
    @Test
    public  void thisAlwaysPass(){
       logger.info("this is always pass");
    }

    @Test
    @Ignore
    public void thisIsIgnore(){
        logger.info("this is ignored");
    }


}
