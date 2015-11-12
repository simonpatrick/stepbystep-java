package com.hedwig.stepbystep.javatutorial.freeminder_parser;

/**
 * Created by patrick on 15/8/14.
 *
 * @version $Id$
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by def on 13.07.14.
 */
public class GUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuModel;
    private JMenuItem openTxt;
    private JMenuItem saveTxt;
    private JMenuItem generateCases;
    private JPanel mainPanel;
    private JTextArea textArea;
    private JFileChooser dialog;

    public GUI(ActionListener listener, String text) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultLookAndFeelDecorated(false);
        this.setPreferredSize(new Dimension(700, 500));
        this.setTitle("Test case generator");
        mainPanel = new JPanel();


        menuBar = new JMenuBar();
        menuModel = new JMenu("File");
        menuModel.setMnemonic(KeyEvent.VK_M);
        menuModel.getAccessibleContext().setAccessibleDescription("File");
        menuBar.add(menuModel);
        openTxt = new JMenuItem("Open", KeyEvent.VK_O);
        openTxt.addActionListener(listener);
        openTxt.setActionCommand("open");
        menuModel.add(openTxt);
        saveTxt = new JMenuItem("Save", KeyEvent.VK_O);
        saveTxt.addActionListener(listener);
        saveTxt.setActionCommand("save");
        menuModel.add(saveTxt);
        generateCases = new JMenuItem("Export to freeMind", KeyEvent.VK_E);
        menuModel.add(generateCases);
        textArea = new JTextArea(text);
        JScrollPane sp = new JScrollPane(textArea);
        mainPanel.add(sp);
        generateCases.addActionListener(listener);
        generateCases.setActionCommand("export");
        this.setJMenuBar(menuBar);

        this.setContentPane(mainPanel);
        mainPanel.setLayout(new GridLayout(1, 1));

        this.pack();
        this.setVisible(true);


    }

    public String getModel() {
        return textArea.getText();
    }

    public void setModel(String txt) {
        this.textArea.setText(txt);
    }

    public File getSrcFile(String filename) {
        dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialog.setApproveButtonText("Сохранить");
        dialog.setDialogTitle("Куда сохранить");
        dialog.setSelectedFile(new File(filename));
        dialog.setDialogType(JFileChooser.SAVE_DIALOG);
        dialog.setMultiSelectionEnabled(false);
        int res = dialog.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        return null;
    }

    public File openTxtFile() {
        dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialog.setApproveButtonText("Открыть");
        dialog.setDialogTitle("Открыть текстовый файл");
        dialog.setDialogType(JFileChooser.OPEN_DIALOG);
        dialog.setMultiSelectionEnabled(false);
        int res = dialog.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        return null;
    }

    public File saveTxtFile() {
        dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialog.setApproveButtonText("Сохранить");
        dialog.setDialogTitle("Куда сохранить");
        dialog.setDialogType(JFileChooser.SAVE_DIALOG);
        dialog.setMultiSelectionEnabled(false);
        int res = dialog.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        return null;
    }
}

