package com.hedwig.stepbystep.javatutorial.jackson.test;

import com.hedwig.stepbystep.javatutorial.jackson.sandbox.JacksonPrettyPrintUnitTest;
import com.hedwig.stepbystep.javatutorial.jackson.sandbox.SandboxTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    JacksonCollectionDeserializationUnitTest.class
    ,JacksonSerializationEnumsUnitTest.class
    ,JacksonDeserializationUnitTest.class
    ,JacksonDeserializationUnitTest.class
    ,JacksonPrettyPrintUnitTest.class
    ,JacksonSerializationIgnoreUnitTest.class
    ,JacksonSerializationUnitTest.class
    ,SandboxTest.class
    ,JacksonFieldUnitTest.class
}) // @formatter:on
public class UnitTestSuite {
}