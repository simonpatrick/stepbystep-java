package com.springdemo.event.listeners;

import com.springdemo.event.listeners.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */

@Component
public class IndexRegisterListener implements ApplicationListener<RegisterEvent> {
    @Override
    public void onApplicationEvent(RegisterEvent registerEvent) {
        System.out.println(((User)registerEvent.getSource()).getName());

    }
}
