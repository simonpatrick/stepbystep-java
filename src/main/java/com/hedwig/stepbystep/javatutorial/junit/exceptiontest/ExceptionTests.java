package com.hedwig.stepbystep.javatutorial.junit.exceptiontest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTests {
    private static final Logger logger = LogManager.getLogger(ExceptionTests.class.getName());

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBounds(){
        new ArrayList<Object>().get(0);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
        List<Object> list = new ArrayList<Object>();
        thrown.expect(IndexOutOfBoundsException.class);
        //thrown.expectMessage("Index: 0, Size:0");
        list.get(0);
    }
}
