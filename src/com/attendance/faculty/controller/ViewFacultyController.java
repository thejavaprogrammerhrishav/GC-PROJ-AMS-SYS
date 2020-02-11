/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Programmer Hrishav
 */
public class ViewFacultyController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton allfaculties;

    @FXML
    private TextField searchinput;

    @FXML
    private JFXButton search;

    @FXML
    private VBox list;

    @FXML
    private Label department;

    private FXMLLoader fxml;
    private LoginService dao;
    private String cdepartment;
    private Parent parent;

    public ViewFacultyController(String cdepartment, Parent parent) {
        this.parent = parent;
        this.cdepartment = cdepartment;
        fxml = Fxml.getViewFacultyDetailsFXML();
        fxml.setRoot(ViewFacultyController.this);
        fxml.setController(ViewFacultyController.this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (LoginService) Start.app.getBean("loginservice");
        dao.setParent(this);
        department.setText("Department: " + cdepartment);

        loadAllFaculty(null);
        allfaculties.setOnAction(this::loadAllFaculty);
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        search.setOnAction(this::searchFaculty);
    }

    private void loadAllFaculty(ActionEvent e) {
        List<User> details = Utils.util.getFacultyUsers(cdepartment);
        details.addAll(Utils.util.getHODUsers(cdepartment));
        List<ViewFacultyNodeController> collect = details.stream().map(ViewFacultyNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void searchFaculty(ActionEvent e) {
        List<User> details = Utils.util.getFacultyUsers(cdepartment);
        details.addAll(Utils.util.getHODUsers(cdepartment));        
        List<ViewFacultyNodeController> collect = details.stream().map(ViewFacultyNodeController::new).filter(f -> f.getName().contains(searchinput.getText())).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

}
