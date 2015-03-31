package com.springdemo.event;

import com.springdemo.event.listeners.SelfDefinedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-event.xml")
public class RegisterServiceTest {

    @Autowired
    private RegisterService service;


    @Test
    public void testRegister() throws Exception {
        service.register("long", "123");

    }

    @Test
    public void publishNewEvent() {
        service.getContext().publishEvent(new SelfDefinedEvent("self defined event"));
        service.getContext().publishEvent(new SelfDefinedEvent("self defined event"));
        service.getContext().publishEvent(new SelfDefinedEvent("self defined event"));
        service.getContext().publishEvent(new SelfDefinedEvent("self defined event"));
        service.getContext().publishEvent(new SelfDefinedEvent("self defined event"));
    }
}