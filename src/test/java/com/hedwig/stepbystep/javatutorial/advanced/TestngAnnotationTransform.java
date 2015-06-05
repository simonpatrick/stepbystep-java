package com.hedwig.stepbystep.javatutorial.advanced;

import com.google.common.collect.Lists;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class TestngAnnotationTransform {

    @BeforeTest
    public void before(){
        System.out.println("before test");
    }
    @Test
    public void test_transferm1(){
        System.out.println("test1");
    }

    @Test
    public void filtedByMethodInterceptor(){
        System.out.println("I am method interceptor testing");
    }

    public static void main(String[] args) {
        TestNG ng = new TestNG();
        ng.addListener(new Transform1());
        ng.addListener(new MethodInterceptor());
        ng.setTestClasses(new Class[]{TestngAnnotationTransform.class});
        ng.run();
    }

    public static  class Transform1 implements IAnnotationTransformer {

        @Override
        public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
            System.out.println("start tranform test annotaion:");
            annotation.setAlwaysRun(true);
//            annotation.setInvocationCount(20);
//            annotation.setThreadPoolSize(20);
            annotation.setDescription("test_annotaion");
            System.out.println(annotation.getSuiteName());
        }
    }

    public static class MethodInterceptor implements IMethodInterceptor{

        @Override
        public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
            System.out.println("running before testing running");
            List<IMethodInstance> result = Lists.newArrayList();
            for (IMethodInstance method : methods) {
                if(method.getMethod().getMethodName().contains("test")){
                    result.add(method);
                }
            }
            return result;
        }
    }
}
