/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Programmer Hrishav
 */
public class SwitchRoot {

    public static Parent switchRoot(Stage stage, Parent parent) {
        Parent oldparent = stage.getScene().getRoot();
        stage.getScene().setRoot(parent);
        stage.setFullScreen(true);
        return oldparent;
    }
}
