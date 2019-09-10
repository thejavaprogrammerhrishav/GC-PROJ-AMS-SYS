/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.usertype.controller;

import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class UserTypeController extends AnchorPane {

    @FXML
    private JFXButton exit;

    @FXML
    private ToggleButton admin;

    @FXML
    private ToggleButton faculty;

    @FXML
    private JFXButton proceed;

    private FXMLLoader fxml;
    
    private String selType;

    public UserTypeController() {
        fxml = Fxml.getUserTypeFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UserTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        
        admin.selectedProperty().addListener((ol,o,n)->{
            if(n){
                admin.setStyle("-fx-background-color: #425C96;"
                        + "-fx-background-radius: 22px;");
                faculty.setSelected(false);
                selType="Admin";
            }
            else{
                admin.setStyle("-fx-background-color: transparent;"
                        + "-fx-background-radius: 22px;");
            }
        });
        
         faculty.selectedProperty().addListener((ol,o,n)->{
            if(n){
                faculty.setStyle("-fx-background-color: #425C96;"
                        + "-fx-background-radius: 22px;");
                admin.setSelected(false);
                selType="Faculty";
            }
            else{
                faculty.setStyle("-fx-background-color: transparent;"
                        + "-fx-background-radius: 22px;");
            }
        });
        
        proceed.setOnAction(this::proceed);
         
        exit.setOnAction(evt -> {
            Alert al = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            al.setHeaderText("Are you sure you want to quit?");
            Optional<ButtonType> opt = al.showAndWait();
            opt.ifPresent(c -> {
                if (c.equals(ButtonType.YES)) {
                    System.exit(0);
                } else {
                    al.close();
                }
            });
        });
    }

    private void proceed(ActionEvent evt){
        if(selType==null || selType.isEmpty()){
            MessageUtil.showError(Message.ERROR, "Attendance Management System", "Login Error\nPlease Select A Valid Login Option", ((Node)evt.getSource()).getScene().getWindow());
        }
        else if(selType.equals("Admin")){
            SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot());
        }
        else{
            SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyLoginRoot());
        }
    }
}
