/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author pc
 */
public class PrincipalDashboardController extends AnchorPane {

    @FXML
    private JFXButton student;

    @FXML
    private JFXButton faculty;

    @FXML
    private JFXButton classdetails;

    @FXML
    private JFXButton dailyclassreport;

    @FXML
    private JFXButton settings;

    @FXML
    private JFXButton myprofile;

    @FXML
    private JFXButton changepassword;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXComboBox<?> department;

    @FXML
    private JFXComboBox<?> year;

    @FXML
    private JFXButton open;

    @FXML
    private JFXButton refresh;

    @FXML
    private FontAwesomeIconView refr;

    @FXML
    private VBox list;

    @FXML
    private JFXButton totalstudents;

    @FXML
    private JFXButton totalfaculties;

    @FXML
    private JFXButton totaldepartments;

    @FXML
    private JFXButton principalaccounts;

    @FXML
    private JFXButton hodaccounts;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton verifyhod;

    @FXML
    private Label pending;

    private User principal;
    private LoginActivity activity;

    private FXMLLoader fxml;

    public PrincipalDashboardController(User principal, LoginActivity activity) {
        this.principal = principal;
        this.activity = activity;

        fxml = Fxml.getPrincipalDashboardFXML();
        fxml.setController(this);
        fxml.setRoot(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        buttonActions();

    }

    private void buttonActions() {
        student.setOnAction(new ShowDepartmentPage("Student"));
        logout.setOnAction(new Logout(activity));
        faculty.setOnAction(new ShowDepartmentPage("Faculty"));
        classdetails.setOnAction(new ShowDepartmentPage("Class Details"));
        dailyclassreport.setOnAction(new ShowDepartmentPage("Daily Class Details"));
        totalstudents.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardStudentNodeRoot()));
        totalfaculties.setOnAction(e -> pane.getChildren().setAll(RootFactory.getPrincipalDashboardFacultyNodeRoot()));

    }

    private class ShowDepartmentPage implements EventHandler<ActionEvent> {

        private final String type;

        public ShowDepartmentPage(String type) {
            this.type = type;
        }

        @Override
        public void handle(ActionEvent t) {
            Stage st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                    .initModality(Modality.WINDOW_MODAL).initOwner(((Node) t.getSource()).getScene().getWindow()).initStyle(StageStyle.UNDECORATED)
                    .build();
            if (type.equals("Student")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Student", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("Faculty")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Faculty", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("Class Details")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Class Details", PrincipalDashboardController.this.getScene().getRoot()));

            } else if (type.equals("Daily Class Details")) {
                st.getScene().setRoot(RootFactory.getSelectDepartmentRoot("Daily Class Details", PrincipalDashboardController.this.getScene().getRoot()));

            }
            st.show();
        }
    }

}
