/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class LoginUserSettingsController extends AnchorPane {

    @FXML
    private JFXButton updateadmin;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton deleteadmin;

    @FXML
    private JFXButton updatefaculty;

    @FXML
    private JFXButton deletefaculty;

    @FXML
    private BorderPane pane;

    private FXMLLoader fxml;
    private Parent parent;

    public LoginUserSettingsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getSettingsLoginUserFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(LoginUserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        updateadmin.setOnAction(e->pane.setCenter(RootFactory.getUpdateAdminUserRoot()));
        deleteadmin.setOnAction(e->pane.setCenter(RootFactory.getDeleteAdminUserRoot()));
        updatefaculty.setOnAction(e->pane.setCenter(RootFactory.getUpdateFacultyUserRoot()));
    }

}
