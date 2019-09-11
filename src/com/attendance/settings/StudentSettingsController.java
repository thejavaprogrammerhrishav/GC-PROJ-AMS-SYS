/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.main.Start;
import com.attendance.settings.sub.DeleteStudentController;
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
public class StudentSettingsController extends AnchorPane{
    
    @FXML
    private JFXButton semester1;

    @FXML
    private JFXButton semester2;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton semester3;

    @FXML
    private BorderPane pane;
    
    private FXMLLoader fxml;
    private Parent parent;
    

    public StudentSettingsController(Parent parent) {
        this.parent = parent;
        fxml=Fxml.getSettingsStudentFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(StudentSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @FXML
    private void initialize(){
        pane.setCenter(new DeleteStudentController("1st"));
        close.setOnAction(e->SwitchRoot.switchRoot(Start.st, parent));
        
        semester1.setOnAction(e->pane.setCenter(new DeleteStudentController("1st")));
        semester2.setOnAction(e->pane.setCenter(new DeleteStudentController("2nd")));
        semester3.setOnAction(e->pane.setCenter(new DeleteStudentController("3rd")));
    }
    
   
} 
