package org.example.milestone3mlgui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    ColorPicker primaryColor;
    @FXML
    ColorPicker secondaryColor;
    @FXML
    HBox background1;
    @FXML
    VBox interior1;
    @FXML
    VBox interior2;
    @FXML
    VBox interior3;
    @FXML
    VBox interior4;

    @FXML
    protected void onSubmitfileButtonclick(){

    }

    public void addMLPlainText(){
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        for(int i = 0; i < a.size(); i++){

        }
    }

    @FXML
    protected void onSubmitButtonClick(){
        Color color1 = primaryColor.getValue();
        Color color2 = secondaryColor.getValue();
        interior2.setBackground(Background.fill(color2));
        interior1.setBackground(Background.fill(color2));
        interior3.setBackground(Background.fill(color2));
        interior4.setBackground(Background.fill(color2));
        background1.setBackground(Background.fill(color1));
    }
    @FXML
    protected void onResetButtonClick(){
        interior2.setBackground(Background.fill(Paint.valueOf("white")));
        interior1.setBackground(Background.fill(Paint.valueOf("white")));
        interior3.setBackground(Background.fill(Paint.valueOf("white")));
        interior4.setBackground(Background.fill(Paint.valueOf("white")));
        background1.setBackground(Background.fill(Paint.valueOf("#4C721D")));
    }

    @FXML
    protected void onSaveButtonClick(){

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
                InputArea.setOnKeyReleased(event -> handleKeyRelease(event, 4));
            } else {
                OutputArea.setText(OutputArea.getText() + returnString);
            }
        } catch(Exception e){
            OutputArea.setText(OutputArea.getText() + "Cannot Run: Illegal Line\n");
        }
    }

    private void handleKeyRelease(KeyEvent keyEvent, int len){
        ACCIDXLabel.setText("ACC: "+m.accumulator+"    "+"IDX: "+m.index);
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String userInput = InputArea.getText().trim();
                InputArea.clear();
                try {
                    if (userInput.length() <= len) {
                        int word = Integer.parseInt(userInput);
                        OutputArea.setText(OutputArea.getText() + "User entered " + word + "\n");
                        returnString = "";
                        ACCIDXLabel.setText("ACC: "+m.accumulator+"    "+"IDX: "+m.index);
                        m.read(word);
                    } else {
                        OutputArea.setText(OutputArea.getText() + "Invalid input. Must be a "+ len +"-digit number.\n");
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

    @FXML
    protected void onAddButtonClick(){
        System.out.println(AmountOfSelectedWordGui());
        System.out.println(AmountOfAvailableLines());
        if(AmountOfSelectedWordGui() == 0){
            OutputArea.setText(OutputArea.getText() + "No Selected Locations\n");
            return;
        }
        if(AmountOfSelectedWordGui() > AmountOfAvailableLines()){
            OutputArea.setText(OutputArea.getText() + "Not enough blank lines at the end for requested addition\n");
            return;
        }
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        AddLine(3);
        for(int i = 99; i > -1; i--){
            if(a.get(i).isChecked()){
                AddLine(i);
            }
        }
        for(WordGui i : a){
            i.deselect();
        }
    }

    @FXML
    protected void onDeleteButtonClick(){
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        for(WordGui i : a){
            i.deselect();
        }
    }

    public void AddLine(int index){
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        for(int i = 99; i > index; i--){
            String storage = a.get(i - 1).getStringValue();
            a.get(i).setValue(storage);
        }
        a.get(index).setValue("+0000");
    }

    public int AmountOfAvailableLines(){
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        for(int i = 99; i > 0; i--){
            if(!a.get(i).getStringValue().equals("+0000")){
                System.out.println(a.get(i).getStringValue());
                return 99 - i;
            }
        }
        return 99;
    }

    public int AmountOfSelectedWordGui(){
        ArrayList<WordGui> a = MLApplication.GuiMemory;
        int b = 0;
        for(int i = 99; i > -1; i--){
            if(a.get(i).isChecked()){
                b++;
            }
        }
        return b;
    }
}