package org.example.milestone3mlgui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MLGuiController {

    Machine m;

    //TODO working on a method of setting and getting Gui Memory, sorry this still isn't ready yet

    public MLGuiController(){
        m = new Machine();//machine created when program is opened.
    }

    @FXML
    private VBox MemContainer;

    @FXML
    protected void onFileButtonClick(){
        //needs to accept a file (open a javafx file chooser)

        //if the file is at all invalid, do not update any memory and send an error message to our output field

        //if the file is valid, update the memory of the machine to reflect this memory change.
        //probably confirm to the user that the parse was successful
    }
}