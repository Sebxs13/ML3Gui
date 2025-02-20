package org.example.milestone3mlgui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import static java.lang.Integer.parseInt;
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

    public WordGui(int i){
        id = i;
        hbox = new HBox();
        hbox.setAlignment(CENTER_LEFT);
        idl = new Label();
        idl.setText(Integer.toString(id));
        wordField = new TextField();
        wordField.setText("+0000");

        wordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if(!isNowFocused){//a user has left a value in the text box

                //test if the new value is a valid word

                //set the value of this id to the new word (if is valid)

                //set the word description
            }
        });

        interp = new Label();
        interp.setText("EMPTY");
        hbox.getChildren().addAll(idl, wordField, interp);
    }

    public HBox gethbox(){
        return hbox;
    }

    public TextField gettf(){
        return wordField;
    }
}
