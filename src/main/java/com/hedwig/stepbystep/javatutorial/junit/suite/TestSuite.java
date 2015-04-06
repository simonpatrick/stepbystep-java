package com.hedwig.stepbystep.javatutorial.junit.suite;

import com.testinglearning.junit.basic.MatcherAssertionTests;
import com.testinglearning.junit.basic.SimpleJUnitTests;
import com.testinglearning.junit.parameterized.FibonacciTest;
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
