/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.login.user.model.User;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class ViewFacultyDetailsController extends AnchorPane {

    @FXML
    private Label type;

    @FXML
    private Label created;

    @FXML
    private JFXButton close;

    @FXML
    private Label name;

    @FXML
    private Label gender;

    @FXML
    private Label number;

    @FXML
    private Label email;
    
    private FXMLLoader fxml;
    
    private User user;
    
    private PersonalDetails details;
    private PersonalDetailsDao dao;

    public ViewFacultyDetailsController(User user) {
        
        this.user = user;
        
        fxml = Fxml.getViewFacultyDetailsFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewFacultyDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        
    }

}
