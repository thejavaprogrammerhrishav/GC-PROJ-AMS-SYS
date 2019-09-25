/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.model.LoginActivity;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class HODLoginActivityController extends AnchorPane {

     @FXML
    private Label name;

    @FXML
    private Label department;

    @FXML
    private Label username;

    @FXML
    private Label logintime;

    @FXML
    private Label logouttime;

    @FXML
    private Label status;

    @FXML
    private Label date;

    private FXMLLoader fxml;
    private LoginActivity activity;

    public HODLoginActivityController(LoginActivity activity) {
        this.activity = activity;
        fxml = Fxml.getHODLoginActivityFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(HODLoginActivityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        name.setText(activity.getName());
       // usertype.setText(activity.getUserType());
        date.setText(activity.getLogindate());
        logintime.setText(activity.getLogintime());
        logouttime.setText(activity.getLogouttime());
        status.setText(activity.getStatus());
        department.setText(SystemUtils.getDepartment());
        username.setText("@" + activity.getUsername());

        if (status.getText().equalsIgnoreCase("ACTIVE")) {
            status.setStyle("-fx-background-color: green; -fx-text-fill: #fff;");
        } else {
            status.setStyle("-fx-background-color: red; -fx-text-fill: #fff;");
        }
    }
}
