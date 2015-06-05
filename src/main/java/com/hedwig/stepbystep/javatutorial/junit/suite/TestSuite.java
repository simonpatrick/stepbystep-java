package com.hedwig.stepbystep.javatutorial.junit.suite;

import com.hedwig.stepbystep.javatutorial.junit.basic.MatcherAssertionTests;
import com.hedwig.stepbystep.javatutorial.junit.basic.SimpleJUnitTests;
import com.hedwig.stepbystep.javatutorial.junit.parameterized.FibonacciTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SimpleJUnitTests.class,
        TestRunnerTests.class,
        MatcherAssertionTests.class,
        FibonacciTest.class
})
public class TestSuite {
}
