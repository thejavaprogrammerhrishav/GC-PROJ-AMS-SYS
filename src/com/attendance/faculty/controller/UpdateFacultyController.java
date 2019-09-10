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
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class UpdateFacultyController extends AnchorPane {

    @FXML
    private TextField searchid;
    
    @FXML
    private Button search;
    
    @FXML
    private TextField firstname;
    
    @FXML
    private TextField lastname;
    
    @FXML
    private TextField emailid;
    
    @FXML
    private TextField contact;
    
    @FXML
    private JFXCheckBox male;
    
    @FXML
    private JFXCheckBox female;
    
    @FXML
    private Button update;
    
    @FXML
    private Label result;
    
    @FXML
    private Button close;
    
    private Faculty faculty;
    private FacultyDao dao;
    private FXMLLoader fxml;
    
    public UpdateFacultyController() {
        fxml = Fxml.getUpdateFacultyFXML();
        fxml.setRoot(UpdateFacultyController.this);
        fxml.setController(UpdateFacultyController.this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdateFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        dao = (FacultyDao) Start.app.getBean("facultyregistration");
        result.setText("");
        search.setOnAction(this::searchFaculty);
        close.setOnAction(e -> ((Node) e.getSource()).getScene().getWindow().hide());
        update.setOnAction(this::updateFaculty);
    }
    
    private void searchFaculty(ActionEvent e) {
        result.setText("");
        faculty = dao.findById(searchid.getText());
        if (faculty != null) {
            firstname.setText(faculty.getFirstName());
            lastname.setText(faculty.getLastName());
            emailid.setText(faculty.getEmailId());
            contact.setText(faculty.getContact());
            if (faculty.getGender().equalsIgnoreCase("Male")) {
                male.setSelected(true);
            }
            if (faculty.getGender().equalsIgnoreCase("Female")) {
                female.setSelected(true);
            }
        } else {
            result.setText("Faculty Not Found");
        }
    }
    
    private void updateFaculty(ActionEvent e){
        Faculty update=new Faculty();
        update.setEmailId(emailid.getText());
        update.setFirstName(firstname.getText());
        update.setLastName(lastname.getText());
        if(male.isSelected()){
            update.setGender("Male");
        }
        else if(female.isSelected()){
            update.setGender("Female");
        }
        else{
            update.setGender("Unknown");
        }
        update.setContact(faculty.getContact());
        update.setDepartment(faculty.getDepartment());
        boolean up1=dao.updateFaculty(update);
        update.setContact(contact.getText());
        boolean up2=dao.updateFacultyId(update.getContact(), faculty.getContact());
        
        if(up1&&up2){
            result.setText("Faculty Profile Updated Successfully");
        }
        else{
            result.setText("Faculty Profile Not Updated");
        }
    }
    
}
