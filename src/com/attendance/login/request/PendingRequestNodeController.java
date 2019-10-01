/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.request;

import com.attendance.login.dao.Login;
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
 * @author pc
 */
public class PendingRequestNodeController extends AnchorPane {

    @FXML
    private Label slno;

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label username;

    @FXML
    private JFXButton accept;

    @FXML
    private JFXButton onhold;

    @FXML
    private JFXButton decline;

    private FXMLLoader fxml;

    private Login login;
    private User user;

    public PendingRequestNodeController(User user) {
        
        this.user = user;
        fxml = Fxml.getPendingRequestNodeNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PendingRequestNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
;    }

    
}
