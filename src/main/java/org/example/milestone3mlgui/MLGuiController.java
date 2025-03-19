package org.example.milestone3mlgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MLGuiController {
    private static MLGuiController instance;
    Machine m;
    String returnString="";

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

    @FXML
    private Label ACCIDXLabel;
    @FXML
    Button Exit;

    @FXML
    protected void onSettingsButtonClick(){
        //new window opens
        MemContainer.setVisible(false);
        Exit.setDisable(false);
        Exit.setVisible(true);
        OutputArea.setText("options in settings: \n1. change primary color \n2. change secondary color \n to exit settings click exit");
        //take user input primary color
        //take user input secondary color
        //set primary
        //set secondary
    }
    @FXML
    protected void onExitButtonClick(){
        MemContainer.setVisible(true);
        Exit.setDisable(true);
        Exit.setVisible(false);
        OutputArea.setText("");

    }

    @FXML
    protected void onRunButtonClick(){
        m = new Machine();
        try{
            MemGuiToMachine();
            returnString = m.run(instance);
            ACCIDXLabel.setText("ACC: "+m.accumulator+"    "+"IDX: "+m.index);
            if(m.awaitingRead) {
                OutputArea.setText(OutputArea.getText() + returnString);
                InputArea.setOnKeyReleased(event -> handleKeyRelease(event));
            } else {
                OutputArea.setText(OutputArea.getText() + returnString);
            }
        } catch(Exception e){
            OutputArea.setText(OutputArea.getText() + "Cannot Run: Illegal Line\n");
        }
    }

    private void handleKeyRelease(KeyEvent keyEvent){
        ACCIDXLabel.setText("ACC: "+m.accumulator+"    "+"IDX: "+m.index);
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String userInput = InputArea.getText().trim();
                InputArea.clear();
                try {
                    if (userInput.length() <= 4) {
                        int word = Integer.parseInt(userInput);
                        OutputArea.setText(OutputArea.getText() + "User entered " + word + "\n");
                        returnString = "";
                        ACCIDXLabel.setText("ACC: "+m.accumulator+"    "+"IDX: "+m.index);
                        m.read(word);
                    } else {
                        OutputArea.setText(OutputArea.getText() + "Invalid input. Must be a 4-digit number.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(userInput);
                    OutputArea.setText(OutputArea.getText() + "User input is not a number. Try again\n");
                }
                returnString = m.run2();
                OutputArea.setText(OutputArea.getText() + returnString);
                ACCIDXLabel.setText("ACC: "+m.accumulator+"    "+"IDX: "+m.index);
            }
    }


    @FXML
    protected void onFileButtonClick(){
        //needs to accept a file (open a javafx file chooser)

        //if the file is at all invalid, do not update any memory and send an error message to our output field

        //if the file is valid, update the memory of the machine to reflect this memory change.
        //probably confirm to the user that the parse was successful
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Machine Language File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            try {
                m.parse(selectedFile);
                OutputArea.setText("File successfully loaded into memory\n");
            } catch (IllegalArgumentException | FileNotFoundException e) {
                OutputArea.setText("Error: " + e.getMessage());
            }
        }
        MemMachineToGui();
    }

}