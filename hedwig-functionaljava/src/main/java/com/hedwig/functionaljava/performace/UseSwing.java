package com.hedwig.functionaljava.performace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UseSwing {
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        final JButton button = new JButton("click me");
        frame.getContentPane().add(button);

        frame.setSize(400, 300);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(frame, "you clicked!");
            }
        });

        button.addActionListener(event ->
                JOptionPane.showMessageDialog(frame, "you clicked again!"));
    }
}