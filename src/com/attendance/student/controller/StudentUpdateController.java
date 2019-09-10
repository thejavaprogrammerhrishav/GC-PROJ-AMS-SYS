/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.controller;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

/**
 *
 * @author Programmer Hrishav
 */
public class StudentUpdateController extends AnchorPane {

    @FXML
    private TextField studentName;

    @FXML
    private TextField studentRollNumber;

    @FXML
    private TextField studentContact;

    @FXML
    private TextField studentYear;

    @FXML
    private CheckBox genMale;

    @FXML
    private CheckBox genFemale;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private TextField searchid;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton cancel;

    @FXML
    private CheckBox honours;

    @FXML
    private CheckBox pass;

    @FXML
    private Label department;

    @FXML
    private TextField honourssubject;

    private FXMLLoader fxml;
    private StudentDao dao;
    private Student searchStudent;
    private Student updateStudent;

    public StudentUpdateController() {
        fxml = Fxml.getStudentUpdateFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(StudentUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        acadamicyear.getItems().addAll("1st", "2nd", "3rd");
        dao = (StudentDao) Start.app.getBean("studentregistration");
        buttonInit();
        checkinit();
    }

    private void checkinit() {
        genFemale.setOnMouseClicked(e -> {
            if (!genFemale.isSelected()) {
                genFemale.setSelected(true);
            }
            genMale.setSelected(false);
        });
        genMale.setOnMouseClicked(e -> {
            if (!genMale.isSelected()) {
                genMale.setSelected(true);
            }
            genFemale.setSelected(false);
        });

        honours.setOnMouseClicked(e -> {
            if (!honours.isSelected()) {
                honours.setSelected(true);
            }
            pass.setSelected(false);
        });
        pass.setOnMouseClicked(e -> {
            if (!pass.isSelected()) {
                pass.setSelected(true);
            }
            honours.setSelected(false);
        });

        honours.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                honourssubject.setText(SystemUtils.getDepartment());
            }
        });

        pass.selectedProperty().addListener((ol, o, n) -> {
            if (n) {
                honourssubject.setText("N/A");
            }
        });
    }

    private void buttonInit() {
        cancel.setOnAction(e -> ((Node) e.getSource()).getScene().getWindow().hide());
        clear.setOnAction(this::clearAll);
        search.setOnAction(this::searchStudent);
        update.setOnAction(this::updateStudent);
    }

    private void clearAll(ActionEvent e) {
        studentName.setText("");
        studentContact.setText("");
        studentRollNumber.setText("");
        studentYear.setText("");
        acadamicyear.getSelectionModel().select(-1);

        genFemale.setSelected(false);
        genMale.setSelected(false);

        searchid.setText("");
    }

    private void searchStudent(ActionEvent e) {
        searchStudent = dao.findById(searchid.getText());
        studentContact.setText(searchStudent.getContact());
        studentName.setText(searchStudent.getName());
        studentRollNumber.setText("" + searchStudent.getRollno());
        acadamicyear.getSelectionModel().select(searchStudent.getAcadamicyear());
        studentYear.setText("" + searchStudent.getYear());
        if (searchStudent.getGender().equalsIgnoreCase("male")) {
            genMale.setSelected(true);
            genFemale.setSelected(false);
        } else {
            genMale.setSelected(false);
            genFemale.setSelected(true);
        }
        if (searchStudent.getCourseType().equalsIgnoreCase("Honours")) {
            honours.setSelected(true);
            pass.setSelected(false);
        } else {
            honours.setSelected(false);
            pass.setSelected(true);
        }
        honourssubject.setText(searchStudent.getDepartment());
    }

    private void updateStudent(ActionEvent e) {
        updateStudent = new Student();
        updateStudent.setName(studentName.getText());
        updateStudent.setContact(studentContact.getText());
        updateStudent.setRollno(Integer.parseInt(studentRollNumber.getText()));
        updateStudent.setAcadamicyear(acadamicyear.getSelectionModel().getSelectedItem());
        updateStudent.setYear(Integer.parseInt(studentYear.getText()));
        if (genMale.isSelected()) {
            updateStudent.setGender("Male");
        } else {
            updateStudent.setGender("Female");
        }
        updateStudent.setId(searchStudent.getId());

        if (honours.isSelected()) {
            updateStudent.setCourseType("Honours");
        } else if (pass.isSelected()) {
            updateStudent.setCourseType("Pass");
        } else {
            updateStudent.setCourseType("UNKNOWN");
        }
        updateStudent.setDepartment(honourssubject.getText());
        boolean updateDetails = dao.updateStudent(updateStudent);
        updateStudent.setId(studentRollNumber.getText() + "_" + acadamicyear.getSelectionModel().getSelectedItem() + "@" + studentYear.getText()+"#"+updateStudent.getCourseType().charAt(0));
        boolean updateId = dao.updateStudentId(updateStudent.getId(), searchStudent.getId());
        if (updateDetails && updateId) {
            MessageUtil.showInformation(Message.INFORMATION, "Student Details Updation", "Updated Successfully", ((Node) e.getSource()).getScene().getWindow());
        }
    }
}
