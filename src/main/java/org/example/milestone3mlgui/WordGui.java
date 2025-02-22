package org.example.milestone3mlgui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import static javafx.geometry.Pos.CENTER_LEFT;

public class WordGui {
    //needs to create a word gui space that applies this fxml notation

    /*
    <HBox alignment="CENTER_LEFT" spacing = "3.0">
         <Label>00 </Label>
         <TextField>+1090</TextField>
         <Label>READ TO REG 90</Label>
    </HBox>
     */

    int id;
    HBox hbox; //container that holds labels and input field
    Label idl; //leftmost label holding id
    TextField wordField; //textfield that holds the word
    Label interp; //rightmost label holding interpretation;

    //todo validity check

    //todo interp string

    public WordGui(int i){
        id = i;
        hbox = new HBox();
        hbox.setAlignment(CENTER_LEFT);
        hbox.setSpacing(5.0);
        idl = new Label();
        idl.setText(String.format("%0" + 2 + "d", id));
        idl.setPrefWidth(25.0);
        wordField = new TextField();
        wordField.setText("+0000");

        wordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if(!isNowFocused){//a user has left a value in the text box

                //test if the new value is a valid word

                //display an error if not

                //give interpretation if legitimate
            }
        });

        interp = new Label();
        interp.setText("EMPTY");
        hbox.getChildren().addAll(idl, wordField, interp);
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

    public void setValue(String a){
        wordField.setText(a);
    }
}
