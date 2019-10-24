/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.main.Start;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author pc
 */
public class UpdateActiveRoutineController extends AnchorPane{
    
    @FXML
    private JFXCheckBox filterbydate;

    @FXML
    private JFXButton asc;

    @FXML
    private JFXButton desc;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXButton apply;

    @FXML
    private JFXButton refresh;

    @FXML
    private VBox list;
    
    private FXMLLoader fxml;
    private RoutineDao dao;

    public UpdateActiveRoutineController() {
        fxml = Fxml.getUpdateActiveRoutineFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdateActiveRoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        dao = (RoutineDao) Start.app.getBean("routine");
        
        date.disableProperty().bind(filterbydate.selectedProperty().not());
        
        loadData(null);
        
        refresh.setOnAction(this::loadData);
        apply.setOnAction(this::filter);
        asc.setOnAction(this::sortAscending);
        desc.setOnAction(this::sortDescending);
    }
    
    private void loadData(ActionEvent evt) {
        List<Routine> nodes = dao.findByDepartment(SystemUtils.getCurrentUser().getDepartment());
        List<UpdateActiveRoutineNodeController> collect = nodes.stream().map(UpdateActiveRoutineNodeController::new ).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }
    
    private void filter(ActionEvent evt) {
        List<Routine> nodes = dao.findByDepartment(SystemUtils.getCurrentUser().getDepartment());
        nodes = nodes.stream().filter(f-> f.getDate().equals(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date.getValue()))).collect(Collectors.toList());
        List<UpdateActiveRoutineNodeController> collect = nodes.stream().map(UpdateActiveRoutineNodeController::new ).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }
    
    private void sortAscending(ActionEvent evt){
         List<Routine> nodes = dao.sortByDepartment(SystemUtils.getCurrentUser().getDepartment(),"asc");
        List<UpdateActiveRoutineNodeController> collect = nodes.stream().map(UpdateActiveRoutineNodeController::new ).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }
    
    private void sortDescending(ActionEvent evt) {
         List<Routine> nodes = dao.sortByDepartment(SystemUtils.getCurrentUser().getDepartment(),"desc");
        List<UpdateActiveRoutineNodeController> collect = nodes.stream().map(UpdateActiveRoutineNodeController::new ).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }
}
