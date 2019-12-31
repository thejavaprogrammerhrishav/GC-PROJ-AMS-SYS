/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ModifyUsertypeControllerNode extends AnchorPane {
    
    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private JFXButton hod;

    @FXML
    private JFXButton faculty;
    
    private FXMLLoader fxml;
    private User user;
    
    private Login dao;

    public ModifyUsertypeControllerNode(User user) {
        this.user = user;
        fxml = Fxml.getModifyUserNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ModifyUsertypeControllerNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        name.setText(user.getDetails().getName());
        image.setImage(new Image(new ByteArrayInputStream(user.getImage())));
        
        if(user.getType().equals("HOD")) {
            hod.setDisable(true);
        } 
        
        if(user.getType().equals("Faculty")) {
            faculty.setDisable(true);
        }
    }
    
}
