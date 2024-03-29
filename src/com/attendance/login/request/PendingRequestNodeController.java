/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.request;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import static javassist.CtMethod.ConstParameter.string;

/**
 *
 * @author pc
 */
public class PendingRequestNodeController extends AnchorPane {

    @FXML
    private Label slno;

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label username;

    @FXML
    private JFXButton accept;

    @FXML
    private JFXButton onhold;

    @FXML
    private JFXButton decline;

    @FXML
    private Label result;

    private FXMLLoader fxml;

    private LoginService login;
    private ExceptionDialog dialog;
    private User user;
    private int no;

    private PersonalDetails details;

    public PendingRequestNodeController(int no, User user) {
        this.no = no;
        this.user = user;
        fxml = Fxml.getPendingRequestNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PendingRequestNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        login = (LoginService) Start.app.getBean("loginservice");
        login.setParent(this);
        dialog = login.getEx();

        details = user.getDetails();
        disableButtons();
        slno.setText("" + no);
        name.setText(details.getName());
        date.setText(user.getDate());
        username.setText(user.getUsername());
        result.setVisible(false);

        accept.setOnAction(this::accept);
        onhold.setOnAction(this::hold);
        decline.setOnAction(this::decline);
    }

    private void accept(ActionEvent evt) {
        user.setStatus("Accept");
        hide();
        boolean b = login.updateUser(user);
        if (b) {
            result.setText("Accepted");
            result.setStyle("-fx-background-color : green;"
                    + "-fx-text-fill : white;"
                    + "-fx-font-family : 'Tw Cen MT',arial;"
                    + "-fx-font-size : 20;"
                    + "-fx-alignment : center;"
                    + "-fx-background-radius : 20;");
            result.setVisible(true);
        } else {
            dialog.showError(this, "Pending Request", "User Status Update Failed");
        }
    }

    private void hold(ActionEvent evt) {
        user.setStatus("OnHold");
        hide();
        boolean b = login.updateUser(user);
        if (b) {
            result.setText("On Hold");
            result.setStyle("-fx-background-color : yellow;"
                    + "-fx-text-fill : black;"
                    + "-fx-font-family : 'Tw Cen MT',arial;"
                    + "-fx-font-size : 20;"
                    + "-fx-alignment : center;"
                    + "-fx-background-radius : 20;");
            result.setVisible(true);
        } else {
            dialog.showError(this, "Pending Request", "User Status Update Failed");
        }
    }

    private void decline(ActionEvent evt) {
        user.setStatus("Decline");
        hide();
        boolean b = login.updateUser(user);
        if (b) {
            result.setText("Declined");
            result.setStyle("-fx-background-color : red;"
                    + "-fx-text-fill : white;"
                    + "-fx-font-family : 'Tw Cen MT',arial;"
                    + "-fx-font-size : 20;"
                    + "-fx-alignment : center;"
                    + "-fx-background-radius : 20;");
            result.setVisible(true);
        } else {
            dialog.showError(this, "Pending Request", "User Status Update Failed");
        }
    }

    private void hide() {
        accept.setVisible(false);
        onhold.setVisible(false);
        decline.setVisible(false);
    }

    private void disableButtons() {
        switch (user.getStatus()) {
            case "Accept":
                accept.setDisable(true);
                break;
            case "OnHold":
                onhold.setDisable(true);
                break;
            case "Decline":
                decline.setDisable(true);
                break;
            default:
                accept.setDisable(false);
                onhold.setDisable(false);
                decline.setDisable(false);
        }
    }

    public String getName() {
        return name.getText();
    }
}
