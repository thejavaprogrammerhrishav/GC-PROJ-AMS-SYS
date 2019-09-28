/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private Login dao;
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
        dao = (Login) Start.app.getBean("userlogin");
        department.setText("Department: " + cdepartment);

        loadAllFaculty(null);

        allfaculties.setOnAction(this::loadAllFaculty);

        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));

        search.setOnAction(this::searchFaculty);
        list.setOnMouseClicked(this::displayFacultyDetails);
    }

    private void loadAllFaculty(ActionEvent e) {
        List<User> details = Utils.util.getFacultyUsers(cdepartment);
        List<ViewFacultyNodeController> collect = details.stream().map(ViewFacultyNodeController::new).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

    private void searchFaculty(ActionEvent e) {
        List<User> details = Utils.util.getFacultyUsers(cdepartment);
        List<ViewFacultyNodeController> collect = details.stream().map(ViewFacultyNodeController::new).filter(f -> f.getName().equals(searchinput.getText())).collect(Collectors.toList());
        list.getChildren().setAll(collect);
    }

}
