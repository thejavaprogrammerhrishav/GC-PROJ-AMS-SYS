/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.node;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import java.io.IOException;
import java.util.List;
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
public class PrincipalDashboardHODAccountNodeController extends AnchorPane {
        @FXML
    private Label total;
        
    private FXMLLoader fxml;
    private LoginService login;

    public PrincipalDashboardHODAccountNodeController() {
        fxml = Fxml.getPrincipalDashboardHODAccountNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
            try {
                fxml.load();
            } catch (IOException ex) {
                Logger.getLogger(PrincipalDashboardHODAccountNodeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    @FXML
    private void initialize() {
        login = (LoginService) Start.app.getBean("loginservice");
        login.setParent(this);
        count();
    }
    
    private void count() {
        List<User> list = login.findByType("HOD");
        total.setText(""+list.size());
    }
    

}
