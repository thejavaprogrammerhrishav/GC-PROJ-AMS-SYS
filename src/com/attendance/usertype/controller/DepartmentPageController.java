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
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class DepartmentPageController extends AnchorPane{
    
    @FXML
    private JFXButton back;
    
    private FXMLLoader fxml;

    public DepartmentPageController() {
        fxml = Fxml.getDepartmentFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DepartmentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void initialize(){
        back.setOnAction(e-> SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalSignUpRoot(Start.st.getScene().getRoot())));
    }
}
