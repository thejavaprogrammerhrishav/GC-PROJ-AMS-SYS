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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class UpdateActiveRoutineNodeController extends AnchorPane {

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private JFXButton view;

    @FXML
    private JFXButton active;

    @FXML
    private JFXButton deactive;

    private FXMLLoader fxml;

    private Routine routine;
    
    private RoutineService dao;
    private ExceptionDialog dialog;

    public UpdateActiveRoutineNodeController(Routine routine) {
        this.routine = routine;
        fxml = Fxml.getUpdateActiveRoutineNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdateActiveRoutineNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao =  (RoutineService) Start.app.getBean("routineservice");
        dao.setParent(this);
        dialog = dao.getEx();
                
        name.setText(routine.getFilename());
        date.setText(routine.getDate());
        
        if (routine.getStatus().equals("Active")) {
            active.setDisable(true);
            deactive.setDisable(false);
        }
        
        if (routine.getStatus().equals("Not Active")) {
             active.setDisable(false);
            deactive.setDisable(true);
        }

        view.setOnAction(e -> ViewRoutineController.show(routine, this.getScene()));
        active.setOnAction(e->{
            deactivate(e);
            activate(e);
        });
        deactive.setOnAction(this::deactivate);
    }
    
    private void activate(ActionEvent evt) {
        routine.setStatus("Active");
        boolean b = dao.updateRoutine(routine);
        if(b) {
            dialog.showSuccess(this, "Update Routine Status", "Routine Status Updated Successfully");
        active.setDisable(true);
        deactive.setDisable(false);
        }else {
            dialog.showError(this ,"Update Routine Status", "Routine Status Updation Failed");
        }
    }
    
    private void deactivate(ActionEvent evt) {
        List<Routine> list = dao.findByDepartmentAndYear(routine.getDepartment(), Integer.parseInt(DateTime.now().toString(DateTimeFormat.forPattern("yyyy"))));
        list.stream().forEach(c-> {
            c.setStatus("Not Active");
            dao.updateRoutine(c);
        });
        active.setDisable(false);
        deactive.setDisable(true);
    }

}
