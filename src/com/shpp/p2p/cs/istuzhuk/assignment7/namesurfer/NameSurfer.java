package com.shpp.p2p.cs.istuzhuk.assignment7.namesurfer;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

	/* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    private JTextField nameField;
    private JButton graphButton;

    private NameSurferDataBase dataBase;
    private NameSurferGraph graph;
    public void init() {

        JLabel nameText = new JLabel("Name:");
        add(nameText,NORTH);

        nameField = new JTextField("Samantha",10);
        add(nameField,NORTH);

        graphButton = new JButton("Graph");
        JButton clearButton = new JButton("Clear");

        add(graphButton,NORTH);
        add(clearButton,NORTH);

        nameField.addActionListener(this);
        addActionListeners();

        graph = new NameSurferGraph();
        add(graph);

        //reading from file
        try {
            dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nameField || e.getSource() == graphButton){
            if(!nameField.getText().equals("")) {
                NameSurferEntry entry = dataBase.findEntry(nameField.getText());
                graph.addEntry(entry);
            }
        }
        else graph.clear();
    }
}
