/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.forgot;

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Programmer Hrishav
 */
public class ResetPasswordResultController extends AnchorPane {

    @FXML
    private ImageView image;

    @FXML
    private JFXButton proceed;

    @FXML
    private Label usertype;

    @FXML
    private Label department;

    @FXML
    private Pane pane;

    @FXML
    private Label bigmessage;

    @FXML
    private Label smallmessage1;

    @FXML
    private Label smallmessage2;

    private FXMLLoader fxml;
    private String type;

    public ResetPasswordResultController(String type) {
        this.type =type;
        fxml = Fxml.getResetPasswordResultFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ResetPasswordResultController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        usertype.setText(SystemUtils.getCurrentUser().getType());
        department.setText(SystemUtils.getDepartment());
        
        if(type.equals("Success")){
            success();
        }
        
        if(type.equals("Failed")){
            failed();
        }
        
        proceed.setOnAction(this::proceed);
    }

    private void success() {
        pane.getStyleClass().clear();
        pane.getStyleClass().addAll("alert", "alert-success");
        bigmessage.getStyleClass().clear();
        bigmessage.getStyleClass().addAll("h2", "b");
        smallmessage1.getStyleClass().clear();
        smallmessage1.getStyleClass().add("h5");
        smallmessage2.getStyleClass().clear();
        smallmessage2.getStyleClass().add("h5");

        bigmessage.setText("Success");
        smallmessage1.setText("Your account password has been successfully changed .");
        smallmessage2.setText("You can now login to your account");

        image.setImage(new Image(this.getClass().getResourceAsStream("/com/attendance/resources/success.jpg")));

        proceed.setText("Login to your account");
    }

    private void failed() {
        pane.getStyleClass().clear();
        pane.getStyleClass().addAll("alert", "alert-danger");
        bigmessage.getStyleClass().clear();
        bigmessage.getStyleClass().addAll("h2", "b");
        smallmessage1.getStyleClass().clear();
        smallmessage1.getStyleClass().add("h5");
        smallmessage2.getStyleClass().clear();
        smallmessage2.getStyleClass().add("h5");

        bigmessage.setText("Failed");
        smallmessage1.setText("Your account password couldn't be changed .");
        smallmessage2.setText("Please try again from login page");

        image.setImage(new Image(this.getClass().getResourceAsStream("/com/attendance/resources/failure.jpg")));

        proceed.setText("Try again");
    }

    private void proceed(ActionEvent evt) {
        if (SystemUtils.getCurrentUser().getType().equals("Principal")) {
            SystemUtils.setCurrentUser(null);
            SwitchRoot.switchRoot(Start.st, RootFactory.getPrincipalLoginRoot());
        } else if (SystemUtils.getCurrentUser().getType().equals("HOD")) {
            SystemUtils.setDepartment(SystemUtils.getCurrentUser().getDepartment());
            SystemUtils.setCurrentUser(null);
            SwitchRoot.switchRoot(Start.st, RootFactory.getHODLoginRoot());
        } else {
            SystemUtils.setDepartment(SystemUtils.getCurrentUser().getDepartment());
            SystemUtils.setCurrentUser(null);
            SwitchRoot.switchRoot(Start.st, RootFactory.getFacultyLoginRoot());
        }
    }
}
