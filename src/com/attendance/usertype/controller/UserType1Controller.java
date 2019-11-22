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
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class UserType1Controller extends AnchorPane{
    @FXML 
    private JFXButton principal;
    
    @FXML
    private JFXButton department;
    
    @FXML
    private JFXButton exit;
    
    
    private FXMLLoader fxml ;

    public UserType1Controller() {
        fxml = Fxml.getUserType1FXML();
        fxml.setController(this);
        fxml.setRoot(this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UserType1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize(){
        this.addEventHandler(KeyEvent.KEY_PRESSED, e->{
            if(e.getCode().equals(KeyCode.ESCAPE)){
                System.exit(0);
            }
        });
        principal.setOnAction(this::principalAction);
        department.setOnAction(this::departmentAction);
        exit.setOnAction(e->System.exit(0));
    }
    
    private void principalAction(ActionEvent evt){
        SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalLoginRoot());
    }
    
    private void departmentAction(ActionEvent evt){
        SwitchRoot.switchRoot(Start.st, RootFactory.getDepartmentRoot());
    }
}
