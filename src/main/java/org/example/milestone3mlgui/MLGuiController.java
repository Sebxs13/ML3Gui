package org.example.milestone3mlgui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class MLGuiController {
    private static MLGuiController instance;
    Machine m;

    //TODO working on a method of setting and getting Gui Memory, sorry this still isn't ready yet

    //Gui to Machine Memory (at run)

    //Machine to Gui Memory (at file)

    public void MemGuiToMachine(){
        //THIS ASSUMES ALL MEMORY IS VALID WORDS. PLEASE RUN A VALIDITY CHECK BEFORE USING
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        for(int i = 0; i < a.size(); i++){
            m.memory.setWordSingle(i, a.get(i).getValue()); //add all visual gui to machine memory
        }
    }

    public void MemMachineToGui(){
        int[] wordlist = m.memory.getWordlist();
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        for(int i = 0; i < a.size(); i++){
            if(m.memory.getWordSingle(i) >= 0){
                String formattedString = String.format("%0" + 4 + "d", m.memory.getWordSingle(i));
                a.get(i).setValue("+" + formattedString);
            } else {
                a.get(i).setValue(String.format("%0" + 5 + "d", m.memory.getWordSingle(i)));
            }
        }
    }

    public MLGuiController(){
        instance = this;
        m = new Machine();//machine created when program is opened.
    }

    @FXML
    private VBox MemContainer;

    @FXML
    private TextArea InputArea;

    @FXML
    private Text OutputArea;
    //OutputArea.setText(OutputArea.getText() + "This message is sent to screen\n");

    @FXML
    private Label ACCIDXLabel;

    @FXML
    protected void onRunButtonClick(){
        MemGuiToMachine();
        m.run(instance);
        InputArea.setOnKeyReleased(event ->handleKeyRelease(event));
    }

    private void handleKeyRelease(KeyEvent keyEvent){
        if (keyEvent.getCode()== KeyCode.ENTER) {
            m.run2();
        }

    }
    public static void requestInput(){

        if(instance != null){
            instance.OutputArea.setText("waiting for input");
        }
    }


    @FXML
    protected void onFileButtonClick(){
        //needs to accept a file (open a javafx file chooser)

        //if the file is at all invalid, do not update any memory and send an error message to our output field

        //if the file is valid, update the memory of the machine to reflect this memory change.
        //probably confirm to the user that the parse was successful
    }

}