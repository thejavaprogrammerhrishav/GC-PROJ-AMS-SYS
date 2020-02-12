/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Programmer Hrishav
 */
public class InputValidator {
    
    public static String getValue(ComboBox<String> box) throws Exception{
        if(box.getSelectionModel().getSelectedIndex()>=0){
            return box.getSelectionModel().getSelectedItem();
        }
        else{
            throw new Exception("Please "+box.getPromptText());
        }
    }
    
    public static boolean getValue(CheckBox... boxes) {
        for(CheckBox box:boxes){
            if(box.isSelected()){
                return true;
            }
        }
        return false;
    }
}
