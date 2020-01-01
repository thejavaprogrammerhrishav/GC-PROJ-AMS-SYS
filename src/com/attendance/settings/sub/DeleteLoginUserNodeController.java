/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.user.model.User;
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
 * @author Programmer Hrishav
 */
public class DeleteLoginUserNodeController extends AnchorPane{
    
    @FXML
    private Label name;

    @FXML
    private JFXButton view;
    
    private FXMLLoader fxml;
    private User user;
    

    public DeleteLoginUserNodeController(User user) {
        this.user = user;
        fxml = Fxml.getDeleteLoginUserNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DeleteLoginUserNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
    }
    
    @FXML
    private void initialize() {
        name.setText(user.getDetails().getName());
    }
    
    public User getUser() {
        return user;
    }
    
    public JFXButton getAction() {
        return view;
    }    
   
}
