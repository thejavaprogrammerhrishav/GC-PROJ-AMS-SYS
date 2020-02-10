/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.model.LoginActivity;
import com.attendance.util.Fxml;
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
public class PrincipalLoginActivityController extends AnchorPane {

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private Label logouttime;

    @FXML
    private Label status;

    @FXML
    private Label date;

    @FXML
    private Label logintime;

    private FXMLLoader fxml;
    private LoginActivity activity;

    public PrincipalLoginActivityController(LoginActivity activity) {
        this.activity = activity;
        fxml = Fxml.getPrincipalLoginActivityFXML();
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
        date.setText(activity.getLogindate());
        logintime.setText(activity.getLogintime());
        logouttime.setText(activity.getLogouttime());
        status.setText(activity.getStatus());
        username.setText("@" + activity.getUsername());

        if (status.getText().equalsIgnoreCase("ACTIVE")) {
            status.setStyle("-fx-background-color: green; -fx-text-fill: #fff;"
                    + "-fx-font-size: 16; -fx-background-radius: 50;");
        } else {
            status.setStyle("-fx-background-color: red; -fx-text-fill: #fff;"
                    + "-fx-font-size: 16; -fx-background-radius: 50;");
        }
    }

}
