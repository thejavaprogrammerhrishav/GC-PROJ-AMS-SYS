/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.routines.model.Routine;
import com.attendance.util.Fxml;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class ViewActiveRoutineController extends AnchorPane {

    @FXML
    private ImageView image;
    
    private FXMLLoader fxml;
    private Routine routine;

    public ViewActiveRoutineController(Routine routine) {
        this.routine = routine;
        fxml = Fxml.getViewActiveRoutineFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewActiveRoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        image.setImage(new Image(new ByteArrayInputStream(routine.getImage())) );
    }
    
    

}
