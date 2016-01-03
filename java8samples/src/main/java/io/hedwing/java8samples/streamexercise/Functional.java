package io.hedwing.java8samples.streamexercise;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by patrick on 16/1/3.
 */
public class Functional {
    private JButton button = new JButton();
    private ActionEvent lastEvent;

    private void registerHandler() {
        button.addActionListener(event -> {
            this.lastEvent = event;
        });
    }
}
