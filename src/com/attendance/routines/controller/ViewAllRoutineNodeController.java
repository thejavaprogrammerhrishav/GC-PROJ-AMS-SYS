/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.main.Start;
import com.attendance.routine.service.RoutineService;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class ViewAllRoutineNodeController extends AnchorPane{
    
    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private JFXButton view;
    
    @FXML
    private JFXButton delete;

    
    private FXMLLoader fxml;
    
    private Routine routine;
    
    private RoutineService dao;
    private ExceptionDialog dialog;

    public ViewAllRoutineNodeController(Routine routine) {
        this.routine = routine;
        fxml = Fxml.getViewAllRoutineNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewAllRoutineNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        
        dao = (RoutineService) Start.app.getBean("routineservice");
        dao.setParent(name);
        dialog = dao.getEx();
        
        name.setText(routine.getFilename());
        date.setText(routine.getDate());
        
        view.setOnAction(e-> ViewRoutineController.show(routine, this.getScene()));
        delete.setOnAction(this::delete);
    }
    
    
    private void delete(ActionEvent evt) {
        boolean b = dao.deleteRoutine(routine);
        if(b) {
            dialog.showSuccess(this, "Delete Routine", "Routine Deleted Successfully");
        }else {
            dialog.showError(this, "Delete Routine", "Routine Deletion Failed");
        }
    }
    
}
