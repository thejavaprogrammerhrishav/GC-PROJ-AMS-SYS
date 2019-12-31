/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Programmer Hrishav
 */
public class ModifyUsertypeController extends AnchorPane {
    
     @FXML
    private TextField entername;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton search;

    @FXML
    private JFXComboBox<String> selectdepartment;

    @FXML
    private JFXButton apply;

    @FXML
    private VBox list;
    
    private FXMLLoader fxml;
    private Parent parent;
    
    private Login dao;

    public ModifyUsertypeController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getModifyUserFXML();
        fxml.setController(this);
        fxml.setRoot(this);
         try {
             fxml.load();
         } catch (IOException ex) {
             Logger.getLogger(ModifyUsertypeController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        close.setOnAction(e->SwitchRoot.switchRoot(Start.st, parent));
    }
    
    //private 
    
    
}
