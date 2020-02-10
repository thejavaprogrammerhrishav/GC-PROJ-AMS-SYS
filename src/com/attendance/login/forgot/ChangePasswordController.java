/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.forgot;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class ChangePasswordController extends AnchorPane {

    @FXML
    private PasswordField oldpassword;

    @FXML
    private PasswordField newpassword;

    @FXML
    private PasswordField confirmpassword;

    @FXML
    private JFXButton change;

    @FXML
    private JFXButton back;

    private User user;

    private LoginService login;
    private ExceptionDialog dialog;

    private Parent parent;

    private FXMLLoader fxml;

    public ChangePasswordController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getChangePasswordFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        login = (LoginService) Start.app.getBean("loginservice");
        login.setParent(this);
        dialog = login.getEx();
        
        user = SystemUtils.getCurrentUser();
        change.setOnAction(this::changePassword);
        back.setOnAction(e-> SwitchRoot.switchRoot(Start.st, parent));

    }

    private void changePassword(ActionEvent evt) {
        if (user.getPassword().equals(oldpassword.getText())) {
            if (newpassword.getText().equals(confirmpassword.getText())) {
                user.setPassword(newpassword.getText());
                boolean b = login.updateUser(user);
                if(b) {
                SystemUtils.setCurrentUser(user);
                dialog.showSuccess(this, "Update User Password", "Password Updated Successfully");
            } 
                else{
                    dialog.showError(this, "Update User Password", "Password Updation Failed");
                }
            }else {
                dialog.showError(this, "Update User Password", "Password Didn't Match");
            }
        } else {
            dialog.showError(this, "Update User Password", "Old Password Mismatch");
        }
    }

}
