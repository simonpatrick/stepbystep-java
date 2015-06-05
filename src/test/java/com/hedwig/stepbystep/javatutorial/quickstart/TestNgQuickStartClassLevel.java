package com.hedwig.stepbystep.javatutorial.quickstart;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */

@Test
public class TestNgQuickStartClassLevel {

    @Test
    public TestNgQuickStartClassLevel(){
        assertTrue(true,"always true");
    }

    public void allReadyCorrect(){
        assertTrue(true,"always true");
    }

    public void allReadyCorrect_01(){
        assertTrue(true,"always true");
    }
}
