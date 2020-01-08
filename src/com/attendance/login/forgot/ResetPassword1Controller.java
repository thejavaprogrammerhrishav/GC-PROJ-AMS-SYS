/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.forgot;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ResetPassword1Controller extends AnchorPane {
    
    @FXML
    private TextField username;
    
    @FXML
    private TextField email;
    
    @FXML
    private JFXButton proceed;
    
    @FXML
    private Label usertype;
    
    @FXML
    private Label department;
    
    @FXML
    private JFXButton close;
    
    private FXMLLoader fxml;
    private Parent parent;
    
    private Login dao;
    
    public ResetPassword1Controller(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getResetPassword1FXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ResetPassword1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        usertype.setText(SystemUtils.getCurrentUser().getType());
        department.setText(SystemUtils.getDepartment());
        proceed.setOnAction(this::proceed);
    }
    
    private void proceed(ActionEvent evt) {
        String usrname = username.getText();
        String mail = email.getText();
        if (usrname != null && !usrname.isEmpty() && mail != null && !mail.isEmpty()) {
            User forgot = dao.findByUsername(usrname);
            System.out.println(forgot.getDetails()==null);
            if (forgot.getDetails().getEmailId().equals(mail)) {                
                SystemUtils.setCurrentUser(forgot);
                if (SystemUtils.getCurrentUser().hasSecurityQuestion()) {
                    SwitchRoot.switchRoot(Start.st, RootFactory.getSecurityQuestionRoot(Start.st.getScene().getRoot(), "Forgot"));
                } else {
                    SwitchRoot.switchRoot(Start.st, RootFactory.getRestPassword3Root(Start.st.getScene().getRoot()));
                }
            }
        }
    }
    
}
