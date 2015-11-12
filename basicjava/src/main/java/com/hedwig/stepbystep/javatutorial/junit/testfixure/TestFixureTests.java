package com.hedwig.stepbystep.javatutorial.junit.testfixure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

import java.io.Closeable;
import java.io.IOException;

//https://github.com/junit-team/junit/wiki/Test-fixtures

public class TestFixureTests {
    private static final Logger logger = LogManager.getLogger(TestFixureTests.class.getName());

    static class ExpensiveManagedResource implements Closeable{

        @Override
        public void close() throws IOException {
            logger.info("expensive managed resource");
        }
    }

    static class ManagedResource implements Closeable{

        @Override
        public void close() throws IOException {
            logger.info("managed resource");
        }
    }
    private ManagedResource managedResource;
    private static ExpensiveManagedResource expensiveManagedResource;

    @BeforeClass
    public static void setupClass(){
        logger.info("@Before Class setup class");
        expensiveManagedResource = new ExpensiveManagedResource();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        logger.info("After Class Tear Down Class");
        expensiveManagedResource.close();
        expensiveManagedResource=null;
    }

    @Before
    public void setUp(){
        logger.info("@Before setup");
        this.managedResource = new ManagedResource();
    }

    @After
    public void tearDown() throws IOException {
        logger.info("After Tear Down");
        this.managedResource.close();
        this.managedResource=null;
    }

    @Test
    public void test1(){
        logger.info("@Test test1()");
    }

    @Test
    public void test2(){
        logger.info("@Test test2()");
    }
}
