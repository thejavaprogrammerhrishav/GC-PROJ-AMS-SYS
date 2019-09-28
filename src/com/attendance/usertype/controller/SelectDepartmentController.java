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
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class SelectDepartmentController extends AnchorPane {

    @FXML
    private JFXComboBox<String> department;

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
        department.getItems().setAll(SystemUtils.getDepartments());

        proceed.setOnAction(this::proceed);
        cancel.setOnAction(this::close);
    }

    private void close(ActionEvent evt) {
        ((Node) evt.getSource()).getScene().getWindow().hide();
    }

    private void proceed(ActionEvent evt) {
        close(evt);
        if (department.getSelectionModel().getSelectedIndex() != -1 && type.equals("Student")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getViewStudentDetailsRoot(department.getSelectionModel().getSelectedItem(),parent));
        } 
        else if (department.getSelectionModel().getSelectedIndex() != -1 && type.equals("Faculty")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getViewFacultyRoot(department.getSelectionModel().getSelectedItem(),parent));
        } 
        else if (department.getSelectionModel().getSelectedIndex() != -1 && type.equals("Class Details")) {
            SystemUtils.setDepartment(department.getSelectionModel().getSelectedItem());
            SwitchRoot.switchRoot(Start.st, RootFactory.getClassDetailsRoot(parent,"N/A"));
        } 
        else if (department.getSelectionModel().getSelectedIndex() != -1 && type.equals("Daily Class Details")) {
            SystemUtils.setDepartment(department.getSelectionModel().getSelectedItem());
            SwitchRoot.switchRoot(Start.st, RootFactory.getDailyStatsRoot(parent));
        } 

    }
}
