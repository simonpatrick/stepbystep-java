package com.springdemo.event;

import com.springdemo.event.listeners.RegisterEvent;
import com.springdemo.event.listeners.SelfDefinedEvent;
import com.springdemo.event.listeners.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */

@Service
public class RegisterService {
    @Autowired
    private ApplicationContext context;

    public void register(String username, String password) {
        System.out.println(username + "username");
        publishRegisterEvent(new User(username, password));
    }

    private void publishRegisterEvent(User user) {
        context.publishEvent(new RegisterEvent(user));
    }


    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
