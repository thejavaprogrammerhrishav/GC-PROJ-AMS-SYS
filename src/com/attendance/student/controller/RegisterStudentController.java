/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.student.controller;

import com.attendance.main.Start;
import com.attendance.student.model.Student;
import com.attendance.student.service.StudentService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.ValidationUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.validation.ConstraintViolation;

/**
 *
 * @author Programmer Hrishav
 */
public class RegisterStudentController extends AnchorPane {

    @FXML
    private TextField name;

    @FXML
    private TextField rollno;

    @FXML
    private TextField contact;

    @FXML
    private TextField year;

    @FXML
    private CheckBox male;

    @FXML
    private CheckBox female;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXButton register;

    @FXML
    private JFXButton cancel;

    @FXML
    private CheckBox honours;

    @FXML
    private CheckBox pass;

    @FXML
    private Label department;

    private FXMLLoader fxml;
    private Student student;
    private StudentService dao;
    private Parent parent;

    private ExceptionDialog dialog;

    public RegisterStudentController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getRegisterStudentFXML();
        fxml.setController(RegisterStudentController.this);
        fxml.setRoot(RegisterStudentController.this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(RegisterStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        acadamicyear.getItems().addAll("1st", "2nd", "3rd");
        dao = (StudentService) Start.app.getBean("studentservice");
        dao.setParent(this);
        dialog = dao.getEx();

        student = new Student();
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

        cancel.setOnAction(evt -> SwitchRoot.switchRoot(Start.st, parent));
        register.setOnAction(evt -> {
            student.setName(name.getText());
            student.setRollno(Integer.parseInt(rollno.getText()));
            student.setYear(Integer.parseInt(year.getText()));
            if (male.isSelected()) {
                student.setGender("MALE");
            } else if (female.isSelected()) {
                student.setGender("FEMALE");
            } else {
                student.setGender("UNKNOWN");
            }
            student.setContact(contact.getText());
            student.setAcadamicyear(acadamicyear.getSelectionModel().getSelectedItem());
            if (honours.isSelected()) {
                student.setCourseType("Honours");
            } else if (pass.isSelected()) {
                student.setCourseType("Pass");
            } else {
                student.setCourseType("UNKNOWN");
            }
            student.setId("GC" + acadamicyear.getSelectionModel().getSelectedItem().charAt(0) + "_" + year.getText() + "_" + rollno.getText() + student.getCourseType().charAt(0) + "_" + SystemUtils.getDepartmentCode());
            student.setDepartment(department.getText());

            Set<ConstraintViolation<Student>> validate = ValidationUtils.getValidator().validate(student);
            if (validate.isEmpty()) {
                String id = dao.saveStudent(student);
                if (id.isEmpty()) {
                    dialog.showError(this, "Register New Student", "Student Registration Failed");
                } else {
                    dialog.showSuccess(this, "Register New Student", "Student Registered Successfully");
                }
            }else {
                validate.stream().forEach(c->{
                    dialog.showError(this, "Register Student Details", c.getMessage());
                });
            }
        });
    }

}
