/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.main.Start;
import com.attendance.settings.sub.DeleteFacultyController;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultySettingsController extends AnchorPane{
    
    
    @FXML
    private JFXButton deletefaculty;

    @FXML
    private JFXButton close;

    @FXML
    private BorderPane pane;
    
    private FXMLLoader fxml;
    private Parent parent;
    

    public FacultySettingsController(Parent parent) {
        this.parent = parent;
        fxml=Fxml.getSettingsFacultyFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultySettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @FXML
    private void initialize(){
        close.setOnAction(e->SwitchRoot.switchRoot(Start.st, parent));
        deletefaculty.setOnAction(e->pane.setCenter(new DeleteFacultyController()));
    }
    
} 
