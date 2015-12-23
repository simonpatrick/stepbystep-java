package com.hedwig.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/10/16.
 */
public class TestNGTest {

    @Test
    public void test_pass(){
        System.out.println("passed_case");
    }

    @Test
    public void test_failed(){
        Assert.assertTrue(false);
    }

    @Test
    public void test_pass_2(){
        System.out.println("passed_case_2");
    }
}
