package com.hedwig.stepbystep.javatutorial.junit.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConcurrencyTest {
    private static final Logger logger = LogManager.getLogger(ConcurrencyTest.class.getName());

//    https://github.com/junit-team/junit/wiki/Multithreaded-code-and-concurrency

//    Certain code you override or implement is expected to adhere to contracts laid out in the Java SDK. Examples of these are:
//
//    equals method: http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals(java.lang.Object)
//    hashcode method: http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode()
//    Comparable interface: http://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
//    Serializable interface: http://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html
//    A project called junit-addons on SourceForge has test superclasses that enable compliance testing of objects to these java contracts
//
//    http://junit-addons.sourceforge.net/junitx/extensions/package-frame.html
//    Although these test helper classes work without issue, they are designed for pre-generic java, and JUnit 3.x and could be confusing to developers used to JUnit 4.x annotations style of test implementation.
//
//    The project Equals Verifier provides support for testing equals and hashCode.
//
//    Third-party extensions
//
//    Custom Runners
//    net.trajano.commons:commons-testing for UtilityClassTestUtil per #646
//    System Rules – A collection of JUnit rules for testing code that uses java.lang.System.
//    JUnit Toolbox – Provides runners for parallel testing, a PoolingWait class to ease asynchronous testing, and a WildcardPatternSuite which allow you to specify wildcard patterns instead of explicitly listing all classes when you create a suite class.
//    junit-dataprovider – A TestNG like dataprovider (see here) runner for JUnit.
//            junit-quickcheck – QuickCheck-style parameter suppliers for JUnit theories. Uses junit.contrib's version of the theories machinery, which respects generics on theory parameters.
}
