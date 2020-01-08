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
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ResetPassword3Controller extends AnchorPane {

    @FXML
    private JFXButton proceed;

    @FXML
    private Label usertype;

    @FXML
    private Label department;

    @FXML
    private Label lnew;

    @FXML
    private Label lconfirm;

    @FXML
    private PasswordField newpassword;

    @FXML
    private PasswordField confirmpassword;

    @FXML
    private JFXButton close;

    private FXMLLoader fxml;
    private Parent parent;
    private Login dao;

    public ResetPassword3Controller(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getResetPassword3FXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ResetPassword3Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao  = (Login) Start.app.getBean("userlogin");
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        usertype.setText(SystemUtils.getCurrentUser().getType());
        department.setText(SystemUtils.getDepartment());
        
        newpassword.textProperty().addListener((ol,o,n)->{
            if(n.length()<8 || n.length()>24) {
                lnew.setText("Password length must be in between 8 to 24 characters ");
                lnew.setStyle("-fx-text-fill : red;-fx-font-family : 'Arial Black',arial;-fx-font-size : 13px;");
            }
            else{
                lnew.setText("Password length is good ");
                lnew.setStyle("-fx-text-fill : #2ecc40;-fx-font-family : 'Arial Black',arial;-fx-font-size : 13px;");
            }
        });
        
        confirmpassword.textProperty().addListener((ol,o,n)->{
             if(!n.equals(newpassword.getText())) {
                lconfirm.setText("Password doesn't match ");
                lconfirm.setStyle("-fx-text-fill : red;-fx-font-family : 'Arial Black',arial;-fx-font-size : 13px;");
            }
            else{
                lconfirm.setText("Password matched ");
                lconfirm.setStyle("-fx-text-fill : #2ecc40;-fx-font-family : 'Arial Black',arial;-fx-font-size : 13px;");
            }
        });

        this.proceed.setOnAction(this::password);
    }

    private void password(ActionEvent evt) {
        if (newpassword.getText().equals(confirmpassword.getText())) {
            SystemUtils.getCurrentUser().setPassword(newpassword.getText());
            dao.update(SystemUtils.getCurrentUser());
            User user = dao.findById(SystemUtils.getCurrentUser().getId());
            if(user.getPassword().equals(newpassword.getText())) {
                SwitchRoot.switchRoot(Start.st, RootFactory.getResetPasswordResultRoot("Success"));
            }else{
                SwitchRoot.switchRoot(Start.st, RootFactory.getResetPasswordResultRoot("Failed"));
            }
        }
    }

}
