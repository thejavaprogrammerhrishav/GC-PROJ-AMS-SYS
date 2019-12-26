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

/**
 *
 * @author Programmer Hrishav
 */
public class SettingsController extends AnchorPane {

    @FXML
    private JFXButton loginactivity;

    @FXML
    private JFXButton notes;

    @FXML
    private JFXButton classattendance;

    @FXML
    private JFXButton importexport;

    @FXML
    private JFXButton attendance;

    @FXML
    private JFXButton reports;

    @FXML
    private JFXButton back;

    private FXMLLoader fxml;

    private Parent parent;

    public SettingsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getSettingsFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        back.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        loginactivity.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUserLoginActivitySettingsRoot(Start.st.getScene().getRoot())));

        classattendance.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getSettingsClassAndDetailsRoot(Start.st.getScene().getRoot())));
        importexport.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getSettingsExportRoot(Start.st.getScene().getRoot())));
        attendance.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUserLoginActivitySettingsRoot(Start.st.getScene().getRoot())));
        
        notes.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getNotesSettingsRoot(Start.st.getScene().getRoot())));
        reports.setOnAction(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getDatabaseServerRoot(Start.st.getScene().getRoot())));
    }
}
