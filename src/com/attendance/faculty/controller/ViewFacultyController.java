/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.login.dao.Login;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ViewFacultyController extends AnchorPane {

    @FXML
    private TableView<PersonalDetails> facultyList;

    @FXML
    private TableColumn<PersonalDetails, String> facultyListName;

    @FXML
    private TableColumn<PersonalDetails, String> facultyListContact;

    @FXML
    private TextField searchFaculty;

    @FXML
    private JFXButton search;

    @FXML
    private TextField firstName;

    @FXML
    private TextField emailId;

    @FXML
    private TextField contact;

    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    @FXML
    private Button close;

    @FXML
    private Button refresh;

    @FXML
    private Label department;

    private FXMLLoader fxml;
    private Login dao;
    private String cdepartment;
    private Parent parent;
    
    public ViewFacultyController(String cdepartment,Parent parent) {
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
        facultyListName.setCellValueFactory(new PropertyValueFactory<PersonalDetails, String>("name"));
        facultyListContact.setCellValueFactory(new PropertyValueFactory<PersonalDetails, String>("contact"));

        loadAllFaculty(null);

        refresh.setOnAction(this::loadAllFaculty);

        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));

        search.setOnAction(this::searchFaculty);
        facultyList.setOnMouseClicked(this::displayFacultyDetails);
    }

    private void loadAllFaculty(ActionEvent e) {
        facultyList.setItems(FXCollections.observableArrayList(Utils.util.getDetails(cdepartment)));
    }

    private void searchFaculty(ActionEvent e) {
        List<PersonalDetails> searchList = Utils.util.getDetails(cdepartment);
        searchList = searchList.stream().filter(p -> p.getName().contains(searchFaculty.getText())).collect(Collectors.toList());
        facultyList.setItems(FXCollections.observableArrayList(searchList));
    }

    private void displayFacultyDetails(MouseEvent evt) {
        PersonalDetails selected = facultyList.getSelectionModel().getSelectedItem();
        firstName.setText(selected.getName());
        emailId.setText(selected.getEmailId());
        contact.setText(selected.getContact());
        if (selected.getGender().equalsIgnoreCase("Male")) {
            male.setSelected(true);
            female.setSelected(false);
        } else if (selected.getGender().equalsIgnoreCase("Female")) {
            female.setSelected(true);
            male.setSelected(false);
        } else {
            male.setSelected(false);
            female.setSelected(false);
        }
    }

}
