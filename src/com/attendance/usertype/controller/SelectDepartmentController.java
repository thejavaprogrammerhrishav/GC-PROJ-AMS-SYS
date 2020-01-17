/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.usertype.controller;

import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Programmer Hrishav
 */
public class SelectDepartmentController extends AnchorPane {
    
    @FXML
    private Pane pane;

     @FXML
    private JFXButton department;

    @FXML
    private Label selected;

    @FXML
    private TextField search;

    @FXML
    private ListView<String> list;

    @FXML
    private JFXButton proceed;

    @FXML
    private JFXButton cancel;

    private FXMLLoader fxml;

    private String type;
    private Parent parent;

    public SelectDepartmentController(String type,Parent parent) {
        this.type = type;
        this.parent =parent;
        fxml = Fxml.getSelectDepartmentFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(SelectDepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        list.getItems().setAll(Arrays.asList(SystemUtils.getDepartments()));
        
        list.getSelectionModel().selectedItemProperty().addListener((ol,o,n)->{
            if(n!=null || !n.isEmpty()) {
                selected.setText(n);
            }
            pane.setVisible(false);
             proceed.setVisible(true);
            cancel.setVisible(true);
        });
        
        pane.setVisible(false);
        
        search.textProperty().addListener((ol,o,n)->{
            if(n.isEmpty()) {
                list.getItems().setAll(Arrays.asList(SystemUtils.getDepartments()));
            }
            else {
                List<String> filtered = Arrays.asList(SystemUtils.getDepartments()).stream().filter(f->f.toLowerCase().contains(n.toLowerCase())).collect(Collectors.toList());
                list.getItems().setAll(filtered);
            }
        });
        
        department.setOnAction(e->{
            proceed.setVisible(false);
            cancel.setVisible(false);
            pane.setVisible(true);
        });

        proceed.setOnAction(this::proceed);
        cancel.setOnAction(this::close);
    }
    
    private String getSelectedDepartment() {
        if(selected.getText().equals("Select Department") && list.getSelectionModel().getSelectedIndex()<0) {
            return "";
        }else{
            if(selected.getText().equals(list.getSelectionModel().getSelectedItem())){
                return selected.getText();
            }
        }
        return String.valueOf(null);
    }

    private void close(ActionEvent evt) {
        ((Node) evt.getSource()).getScene().getWindow().hide();
    }

    private void proceed(ActionEvent evt) {
        close(evt);
        if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("Student")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getViewStudentDetailsRoot(getSelectedDepartment(),parent));
        } 
        else if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("Faculty")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getViewFacultyRoot(getSelectedDepartment(),parent));
        } 
        else if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("Class Details")) {
            SystemUtils.setDepartment(getSelectedDepartment());
            SwitchRoot.switchRoot(Start.st, RootFactory.getClassDetailsRoot(parent,"N/A"));
        } 
        else if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("Daily Class Details")) {
            SystemUtils.setDepartment(getSelectedDepartment());
            SwitchRoot.switchRoot(Start.st, RootFactory.getDailyStatsRoot(parent,"N/A"));
        } 
        else if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("verifyhod")) {
            SystemUtils.setDepartment(getSelectedDepartment());
            SwitchRoot.switchRoot(Start.st, RootFactory.getPendingRequestRoot(parent, SystemUtils.getDepartment()));
        }
        else if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("settings")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getDeleteLoginUserRoot(parent, getSelectedDepartment()));
        }
        
        else if (getSelectedDepartment()!=null && !getSelectedDepartment().isEmpty() && type.equals("BlockLogin")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getBlockLoginUserRoot(parent, getSelectedDepartment()));
        }
    }
}
