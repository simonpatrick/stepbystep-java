package com.springdemo.event.listeners;

import org.springframework.context.ApplicationEvent;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */


public class SelfDefinedEvent extends ApplicationEvent {
    public SelfDefinedEvent(final String source) {
        super(source);
    }
}
