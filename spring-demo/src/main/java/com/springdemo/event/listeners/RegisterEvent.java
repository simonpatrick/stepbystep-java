package com.springdemo.event.listeners;

import com.springdemo.event.listeners.model.User;
import org.springframework.context.ApplicationEvent;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */


public class RegisterEvent extends ApplicationEvent {
    public RegisterEvent(User source) {
        super(source);
    }
}
