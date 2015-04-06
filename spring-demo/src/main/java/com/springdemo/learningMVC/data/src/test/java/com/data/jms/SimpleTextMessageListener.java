package com.springdemo.learningMVC.data.src.test.java.com.data.jms;

import com.google.common.base.Stopwatch;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Testing to use.
 */
public class SimpleTextMessageListener implements MessageListener {

    private final List<String> messages = new ArrayList<>(1000);

    private Stopwatch stopwatch;

    public SimpleTextMessageListener() {
        super();
    }

    @Override
    public void onMessage(Message message) {
        start();
        TextMessage txt = (TextMessage) message;

        try {
            messages.add(messages.size(), txt.getText());
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

        end();
    }

    protected void start() {
        if (stopwatch == null) {
            stopwatch = Stopwatch.createStarted();
        }
    }

    protected void end() {
        if (stopwatch != null && messages.size() == 100) {
            stopwatch.stop();
        }
    }

    public List<String> getMessages() {
        return messages;
    }

    public Stopwatch getStopwatch() {
        return stopwatch;
    }


}
