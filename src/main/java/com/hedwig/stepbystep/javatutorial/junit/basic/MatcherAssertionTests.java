package com.hedwig.stepbystep.javatutorial.junit.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


public class MatcherAssertionTests {

    private static final Logger logger = LogManager.getLogger(MatcherAssertionTests.class.getName());

    @Test
    public void testAssertArrayEquals(){
        byte[] expected = "trial".getBytes();
        byte[] actual = "trial".getBytes();
        Assert.assertArrayEquals("array is same",expected,actual);
        logger.info("result is same!");
    }

    @Test
    public void matcherAssertionTest(){
        Assert.assertEquals("failure string not equal","test","test");
    }

    @Test
    public void assertThatTest(){
        assertThat(Arrays.asList("fun","ban"),everyItem(containsString("n")));
    }

    /**
     * Matchers used
     */
    @Test
    public void assertThatHamcrestCoreMatchers(){
        assertThat("good",allOf(equalTo("good"),startsWith("good")));
        assertThat("good",not(allOf(equalTo("bad"), startsWith("good"))));
    }



}
