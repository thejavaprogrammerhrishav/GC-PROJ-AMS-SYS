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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class FacultySignUpController extends AnchorPane {

    @FXML
    private JFXTextField fullname;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirmpassword;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXButton abort;

    private FXMLLoader fxml;
    private Login login;
    private User user;

    public FacultySignUpController() {
        fxml = Fxml.getFacultySignupFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultySignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

        login = (Login) Start.app.getBean("userlogin");
        user = new User();
    }

    @FXML
    private void initialize() {
        abort.setOnAction(e -> ((Node) e.getSource()).getScene().getWindow().hide());
        signup.setOnAction(e -> {
            if (login.isUsernameExists(username.getText()) > 0) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Faculty Sign Up");
                al.setContentText("Username already taken");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            } else if (password.getText().equals(confirmpassword.getText())) {
                user.setName(fullname.getText());
                user.setEmail(email.getText());
                user.setContact(contact.getText());
                user.setPassword(password.getText());
                user.setType("Faculty");
                user.setUsername(username.getText());

                login.save(user);

                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText("Faculty Sign Up");
                al.setContentText("Sign Up Successful\nFaculty account created successfully");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText("Faculty Sign Up");
                al.setContentText("Passwords don't match\nCheck passwords again");
                al.initOwner(((Node) e.getSource()).getScene().getWindow());
                al.initModality(Modality.WINDOW_MODAL);
                al.initStyle(StageStyle.UNDECORATED);
                al.show();
            }
        });
    }

}
