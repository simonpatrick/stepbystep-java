package com.hedwig.stepbystep.javatutorial.proxy;

import com.google.common.collect.Lists;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by patrick on 15/4/2.
 *
 * @version $Id$
 */


public class ProxyTest {

    public static void main(String[] args) {

       List<TestProxy> e= (List<TestProxy>) Proxy.newProxyInstance(ProxyTest.class.getClassLoader()
               , new Class[]{List.class},new Handle2());
        System.out.println(e);
        e.get(0);
        System.out.println(e);
    }

    public static class Handle2 implements InvocationHandler{
        List l = Lists.newArrayList();
        public Handle2(){

        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("testing");
            l.add(args);
            return null;
        }
    }
}
