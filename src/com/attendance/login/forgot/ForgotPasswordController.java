/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.forgot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.attendance.login.controller.HODLoginController;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Programmer Hrishav
 */
public class ForgotPasswordController extends AnchorPane {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton resetPassword;

    @FXML
    private JFXButton reset;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirmpassword;

    @FXML
    private Label result;

    @FXML
    private Pane resetpane;

    public static String LoginType = "UNKNOWN";

    private FXMLLoader fxml;
    private final Login login;
    private User user;

    public ForgotPasswordController() {
        fxml = Fxml.getForgotPasswordFXML();
        fxml.setRoot(ForgotPasswordController.this);
        fxml.setController(ForgotPasswordController.this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ForgotPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        login = (Login) Start.app.getBean("userlogin");
        resetpane.setVisible(false);
    }

    @FXML
    private void initialize() {
        result.setText("");
        resetPassword.setOnAction(evt -> {
            user = login.findByUsernameDepartmentType(username.getText(), SystemUtils.getDepartment(),LoginType);
            if (user != null) {
                resetpane.setVisible(true);
            } else {
                result.setText("User Not Found");
            }
        });

        reset.setOnAction(evt -> {
            if (password.getText().equals(confirmpassword.getText())) {
                user.setPassword(password.getText());
                boolean update = login.update(user);
                if (update) {
                    result.setText("User Updated Successfully ");
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            for (int i = 3; i >= 0; i--) {
                                final int x = i;
                                Platform.runLater(() -> result.setText("Redirecting To Login Page In " + x + " Sec"));
                                Thread.sleep(1000);
                            }
                            if (LoginType.equals("HOD")) {
                                Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot()));
                            }
                            if (LoginType.equals("FACULTY")) {
                                Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyLoginRoot()));
                            }
                             if (LoginType.equals("PRINCIPAL")) {
                                Platform.runLater(() -> SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalLoginRoot()));
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(HODLoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }).start();
                } else {
                    result.setText("Password reset failed");
                }
            } else {
                result.setText("Password Doesnot Match...");
            }
        });
    }

}
