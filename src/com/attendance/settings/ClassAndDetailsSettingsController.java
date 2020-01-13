/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.main.Start;
import com.attendance.settings.sub.AttendanceController;
import com.attendance.settings.sub.ClassDetailsController;
import com.attendance.settings.sub.DailyStatsController;
import com.attendance.settings.sub.UpdateClassDetailsController;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ClassAndDetailsSettingsController extends AnchorPane {

    @FXML
    private JFXButton classdetails;

    @FXML
    private JFXButton updateclassdetails;

    @FXML
    private JFXButton attendance;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton dailyattendancestats;

    @FXML
    private BorderPane pane;
    
    @FXML
    private Label department;

    private FXMLLoader fxml;
    private Parent parent;

    public ClassAndDetailsSettingsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getSettingsClassAndDetailsFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ClassAndDetailsSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        pane.setCenter(new ClassDetailsController(Start.st.getScene().getRoot(),"N/A"));
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        classdetails.setOnAction(e->pane.setCenter(new ClassDetailsController(Start.st.getScene().getRoot(),"N/A")));
        updateclassdetails.setOnAction(e->pane.setCenter(new UpdateClassDetailsController()));
        attendance.setOnAction(e->pane.setCenter(new AttendanceController(Start.st.getScene().getRoot())));
        dailyattendancestats.setOnAction(e->pane.setCenter(new DailyStatsController(Start.st.getScene().getRoot())));
    }

}
