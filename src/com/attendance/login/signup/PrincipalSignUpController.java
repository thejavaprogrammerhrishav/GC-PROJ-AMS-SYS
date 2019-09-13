/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.signup;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class PrincipalSignUpController extends AnchorPane {

    @FXML
    private TextField fullname;

    @FXML
    private TextField contact;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXButton abort;

    @FXML
    private JFXButton loginbutton;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmpassword;

    private FXMLLoader fxml;
    private User user;
    private Login login;
    private Parent parent;

    public PrincipalSignUpController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getPrincipalSignUpFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        user = new User();
        login = (Login) Start.app.getBean("userlogin");

        abort.setOnAction(E -> SwitchRoot.switchRoot(Start.st, parent));
        signup.setOnAction(E -> {
            if (login.isUsernameExists(username.getText()) > 0) {
                MessageUtil.showError(Message.ERROR, "Principal SignUp", "Username already taken", ((Node) E.getSource()).getScene().getWindow());
            } else if (password.getText().equals(confirmpassword.getText())) {
                user.setName(fullname.getText());
                user.setEmail(email.getText());
                user.setContact(contact.getText());
                user.setType("Principal");
                user.setUsername(username.getText());
                user.setPassword(password.getText());

                login.save(user);
                MessageUtil.showError(Message.INFORMATION, "Principal SignUp", "User signup successful\nPrincipal account created successfully", ((Node) E.getSource()).getScene().getWindow());

            } else {
                MessageUtil.showError(Message.ERROR, "Principal SignUp", "Passwords dont match \nCheck password again", ((Node) E.getSource()).getScene().getWindow());

            }
        });
    }
    
    private void LoginAction(ActionEvent evt) {
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalLoginRoot());
    }
            
}
