/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ExportSettingsController extends AnchorPane{
    
    @FXML
    private JFXButton studentlist;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton facultylist;

    @FXML
    private JFXButton classdetailslist;

    @FXML
    private JFXButton dailystatisticslist;
    
    @FXML
    private BorderPane pane;
    
    @FXML
    private Label department;
    
    private FXMLLoader fxml;
    private Parent parent;
    

    public ExportSettingsController(Parent parent) {
        this.parent = parent;
        fxml=Fxml.getSettingsExportFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ExportSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @FXML
    private void initialize (){
        department.setText(SystemUtils.getDepartment());
        close.setOnAction(e->SwitchRoot.switchRoot(Start.st, parent));
        studentlist.setOnAction(e->pane.setCenter(RootFactory.getExportStudentListRoot()));
        facultylist.setOnAction(e->pane.setCenter(RootFactory.getExportFacultyListRoot()));
        classdetailslist.setOnAction(e->pane.setCenter(RootFactory.getExportClassDetailsListRoot()));
        dailystatisticslist.setOnAction(e->pane.setCenter(RootFactory.getExportDailyStatsListRoot()));
    }
    
} 
