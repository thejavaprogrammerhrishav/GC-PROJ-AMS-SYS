/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.faculty.dao.FacultyDao;
import com.attendance.faculty.model.Faculty;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class RegisterFacultyController extends AnchorPane {

    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    @FXML
    private JFXButton registerfaculty;

    @FXML
    private JFXButton cancel;

    @FXML
    private Label department;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField emailid;

    @FXML
    private TextField contact;

    private Faculty faculty;
    private FacultyDao dao;
    private FXMLLoader fxml;

    public RegisterFacultyController() {
        fxml = Fxml.getRegisterFacultyFXML();
        fxml.setRoot(RegisterFacultyController.this);
        fxml.setController(RegisterFacultyController.this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(RegisterFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        dao = (FacultyDao) Start.app.getBean("facultyregistration");

        male.setOnMouseClicked(e -> {
            if (!male.isSelected()) {
                male.setSelected(true);
            }
            female.setSelected(false);
        });

        female.setOnMouseClicked(e -> {
            if (!female.isSelected()) {
                female.setSelected(true);
            }
            male.setSelected(false);
        });

        registerfaculty.setOnAction(this::register);
        cancel.setOnAction(e -> ((Node) e.getSource()).getScene().getWindow().hide());

    }

    private void register(ActionEvent evt) {
        faculty = new Faculty();
        faculty.setContact(contact.getText());
        faculty.setEmailId(emailid.getText());
        faculty.setFirstName(firstname.getText());
        faculty.setLastName(lastname.getText());

        if (male.isSelected()) {
            faculty.setGender("Male");
        } else if (female.isSelected()) {
            faculty.setGender("Female");
        } else {
            faculty.setGender("Unknown");
        }

        faculty.setDepartment(department.getText());

        String id = dao.saveFaculty(faculty);
        MessageUtil.showInformation(Message.INFORMATION, "Faculty Registration", "Faculty Registration Successful\nFacullty ID: " + id, ((Node)evt.getSource()).getScene().getWindow());
    }
}
