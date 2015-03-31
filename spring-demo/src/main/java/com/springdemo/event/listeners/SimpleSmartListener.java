package com.springdemo.event.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */

@Component
public class SimpleSmartListener  implements SmartApplicationListener{
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return aClass.equals(SelfDefinedEvent.class);
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return aClass.equals(String.class);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println(applicationEvent+"is invoked");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
