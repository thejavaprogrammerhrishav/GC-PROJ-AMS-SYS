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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class UserType2Controller extends AnchorPane {

    @FXML
    private JFXButton hodlogin;

    @FXML
    private JFXButton facultylogin;

    @FXML
    private Label department;

    @FXML
    private JFXButton back;

    private FXMLLoader fxml;

    public UserType2Controller() {
        fxml = Fxml.getUserType2FXML();
        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UserType2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        hodlogin.setOnAction(this::hod);
        facultylogin.setOnAction(this::faculty);
        back.setOnAction(this::closeAction);
    }

    private void closeAction(ActionEvent evt) {
        SystemUtils.setDepartment("");
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
    }

    private void hod(ActionEvent evt) {
        SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot());
    }

    private void faculty(ActionEvent evt) {
        SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyLoginRoot());
    }

}
