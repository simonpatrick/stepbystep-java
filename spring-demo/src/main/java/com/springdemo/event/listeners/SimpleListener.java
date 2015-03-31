package com.springdemo.event.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */

@Component
public class SimpleListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof SelfDefinedEvent){
            System.out.println("self defined event is "+applicationEvent+"invoked!");
        }
    }
}
