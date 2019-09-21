/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class PrincipalDashboardPrincipalAccountNodeController extends AnchorPane {
    
    
    @FXML
    private Label total;
    
    private FXMLLoader fxml;
    private Login login;

    public PrincipalDashboardPrincipalAccountNodeController() {
        fxml = Fxml.getPrincipalDashboardPrincipalAccountNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalDashboardPrincipalAccountNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        login = (Login) Start.app.getBean("userlogin");
        count();
    }
    
    private void count() {
        List<User> list = login.findByType("Principal");
        total.setText(""+list.size());
    }
}
