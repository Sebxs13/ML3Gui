package org.example.milestone3mlgui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

import static javafx.geometry.Pos.CENTER_LEFT;

public class WordGui {

    int id;
    HBox hbox; //container that holds labels and input field
    Label idl; //leftmost label holding id
    TextField wordField; //textfield that holds the word
    Label interp; //rightmost label holding interpretation
    CheckBox cbox;

    @FXML
    TextArea fileInputArea;


    public WordGui(int i){
        id = i;
        hbox = new HBox();
        hbox.setAlignment(CENTER_LEFT);
        hbox.setSpacing(5.0);
        cbox = new CheckBox();
        idl = new Label();
        idl.setText(String.format("%0" + 2 + "d", id));
        idl.setPrefWidth(25.0);
        wordField = new TextField();
        wordField.setText("+0000");
        interp = new Label();
        interp.setText("EMPTY");
        hbox.getChildren().addAll(cbox, idl, wordField, interp);

        wordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if(!isNowFocused){//a user has left a value in the text box
                try{
                    if(wordField.getText().charAt(0) == '-'){
                        int b = Integer.parseInt(wordField.getText());
                        if(b >= -9999 && b < 0){
                            interp.setText("Value");
                        } else {
                            if(wordField.getText().equals("-99999")){
                                interp.setText("Halt: stops the program\n");
                                return;
                            }
                            interp.setText("Improper Input");
                        }
                    } else if(wordField.getText().charAt(0) == '+') {
                        int b = Integer.parseInt(wordField.getText().substring(1));
                        if(b >= 0 && b < 10000){
                            interp.setText(intToString(b));
                        } else {
                            interp.setText("Improper Input");
                        }
                    }
                } catch(Exception e){
                    interp.setText("Improper Input");
                }
            }
        });

    }

    public boolean isChecked(){
        return cbox.isSelected();
    }

    public void deselect(){
        if(cbox.isSelected()){
            cbox.setSelected(false);
        }
    }

    public HBox gethbox(){
        return hbox;
    }

    public int getValue(){
        if(wordField.getText().charAt(0) == '+'){
            return Integer.parseInt(wordField.getText().substring(1));
        }
        if(wordField.getText().charAt(0) == '-'){
            return -1 * Integer.parseInt(wordField.getText().substring(1));
        }
        return 0; //should not be reached assuming that checks have already been made
    }

    public String getStringValue(){
        return wordField.getText();
    }

    public void setValue(String a){
        wordField.setText(a);
        try {
            // Check if the value is negative
            if (wordField.getText().charAt(0) == '-') {
                int b = Integer.parseInt(wordField.getText());
                if (b >= -9999 && b < 0) {
                    interp.setText("Value: " + intToString(b));
                } else {
                    interp.setText("Improper Input: Out of Range");
                }
            }
            // Check if the value is positive
            else if (wordField.getText().charAt(0) == '+') {
                int b = Integer.parseInt(wordField.getText().substring(1));
                if (b >= 0 && b < 9999) {
                    interp.setText(intToString(b));
                } else {
                    interp.setText("Improper Input: Out of Range");
                }
            }
            // Check if it's a positive or zero number without sign
            else {
                int b = Integer.parseInt(wordField.getText());
                if (b >= 0 && b < 9999) {
                    interp.setText(intToString(b));
                } else {
                    interp.setText("Improper Input: Out of Range");
                }
            }
        } catch (Exception e) {
            interp.setText("Improper Input: Not a valid number");
        }
    }

    public String intToString(int word){
        //i have realized that error trapping for order may be pointless
        if(word == 0){
            return "EMPTY";
        }
        int command = word / 100;
        switch (command){
            case 10://read
                return "Read word from screen in to a location in memory.\n";
            case 11://write
                return "Write a word from memory into the screen\n";
            case 20://load
                return "Store word from memory into the accumulator\n";
            case 21://store
                return "Store word from accumulator into memory\n";
            case 30:// add
                return "Add a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 31://subtract
                return "Subtract a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 32://divide
                return "Divide a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 33://multiply
                return "Multiply a word from the accumulator with a word from memory, and store the results in the accumulator\n";
            case 40://branch
                return "Branch to a specific location in memory\n";
            case 41://branchneg
                return "Branch to a specific location in memory if the accumulator is negative\n";
            case 42://branchzero
                return "Branch to a specific location in memory if the accumulator is zero\n";
            case 43://halt
                return "Halt: stops the program\n";
            default:
                return "Value\n";
        }
    }
}
