package io2016.controls;

import javafx.scene.control.TextField;

/**
 * Created by ishfi on 03.01.2017.
 */
public class NumberTextField extends TextField {

    public NumberTextField(){
        //It is not necessary
        this.setPromptText("Tylko cyfry");
    }

    @Override
    public void replaceText(int i, int i1, String string){
        if (string.matches("[0-9]") || string.isEmpty()){
            super.replaceText(i, i1, string);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
