/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.usertype.controller;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class SelectDepartmentController extends AnchorPane {

    @FXML
    private JFXButton anthropology;

    @FXML
    private JFXButton biotechnology;

    @FXML
    private JFXButton botany;

    @FXML
    private JFXButton assamese;

    @FXML
    private JFXButton bengali;

    @FXML
    private JFXButton hindi;

    @FXML
    private JFXButton history;

    @FXML
    private JFXButton chemistry;

    @FXML
    private JFXButton mathematics;

    @FXML
    private JFXButton zoology;

    @FXML
    private JFXButton persian;

    @FXML
    private JFXButton manipuri;

    @FXML
    private JFXButton geology;

    @FXML
    private JFXButton politicalscience;

    @FXML
    private JFXButton philosophy;

    @FXML
    private JFXButton economics;

    @FXML
    private JFXButton statistics;

    @FXML
    private JFXButton physics;

    @FXML
    private JFXButton commerce;

    @FXML
    private JFXButton english;

    @FXML
    private JFXButton sanskrit;

    @FXML
    private JFXButton computerscience;

    @FXML
    private JFXButton ecologyenvironmentalscience;

    @FXML
    private JFXButton bba;

    @FXML
    private JFXButton masscomm;

    @FXML
    private JFXButton close;

    private FXMLLoader fxml;

    private String type;
    private Parent parent;
    private String selectedDepartment;

    public SelectDepartmentController(String type, Parent parent) {
        this.type = type;
        this.parent = parent;
        fxml = Fxml.getSelectDepartmentFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(SelectDepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        close.setOnAction(this::close);

        selectedDepartment = "";

        btnActions();
    }

    private void btnActions() {
        anthropology.setOnAction(evt -> {
            selectedDepartment = "Anthropology";
            proceed(evt);
        });
        biotechnology.setOnAction(evt -> {
            selectedDepartment = "Biotechnology";
            proceed(evt);
        });
        botany.setOnAction(evt -> {
            selectedDepartment = "Botany";
            proceed(evt);
        });
        assamese.setOnAction(evt -> {
            selectedDepartment = "Assamese";
            proceed(evt);
        });
        bengali.setOnAction(evt -> {
            selectedDepartment = "Bengali";
            proceed(evt);
        });
        hindi.setOnAction(evt -> {
            selectedDepartment = "Hindi";
            proceed(evt);
        });
        history.setOnAction(evt -> {
            selectedDepartment = "History";
            proceed(evt);
        });
        chemistry.setOnAction(evt -> {
            selectedDepartment = "Chemistry";
            proceed(evt);
        });
        mathematics.setOnAction(evt -> {
            selectedDepartment = "Mathematics";
            proceed(evt);
        });
        zoology.setOnAction(evt -> {
            selectedDepartment = "Zoology";
            proceed(evt);
        });
        persian.setOnAction(evt -> {
            selectedDepartment = "Persian";
            proceed(evt);
        });
        manipuri.setOnAction(evt -> {
            selectedDepartment = "Manipuri";
            proceed(evt);
        });
        geology.setOnAction(evt -> {
            selectedDepartment = "Geology";
            proceed(evt);
        });
        politicalscience.setOnAction(evt -> {
            selectedDepartment = "Political Science";
            proceed(evt);
        });
        philosophy.setOnAction(evt -> {
            selectedDepartment = "Philosophy";
            proceed(evt);
        });
        economics.setOnAction(evt -> {
            selectedDepartment = "Economics";
            proceed(evt);
        });
        statistics.setOnAction(evt -> {
            selectedDepartment = "Statistics";
            proceed(evt);
        });
        physics.setOnAction(evt -> {
            selectedDepartment = "Physics";
            proceed(evt);
        });
        commerce.setOnAction(evt -> {
            selectedDepartment = "Commerce";
            proceed(evt);
        });
        english.setOnAction(evt -> {
            selectedDepartment = "English";
            proceed(evt);
        });
        sanskrit.setOnAction(evt -> {
            selectedDepartment = "Sanskrit";
            proceed(evt);
        });
        computerscience.setOnAction(evt -> {
            selectedDepartment = "Computer Science";
            proceed(evt);
        });
        ecologyenvironmentalscience.setOnAction(evt -> {
            selectedDepartment = "Ecology & Environmental Science";
            proceed(evt);
        });
        bba.setOnAction(evt -> {
            selectedDepartment = "Business Administration";
            proceed(evt);
        });
        masscomm.setOnAction(evt -> {
            selectedDepartment = "Mass Communication";
            proceed(evt);
        });
    }

    private void close(ActionEvent evt) {
        ((Node) evt.getSource()).getScene().getWindow().hide();
    }

    private void proceed(ActionEvent evt) {
        close(evt);
        if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("Student")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getViewStudentDetailsRoot(selectedDepartment, parent));
        } else if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("Faculty")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getViewFacultyRoot(selectedDepartment, parent));
        } else if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("Class Details")) {
            SystemUtils.setDepartment(selectedDepartment);
            SwitchRoot.switchRoot(Start.st, RootFactory.getClassDetailsRoot(parent, "N/A"));
        } else if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("Daily Class Details")) {
            SystemUtils.setDepartment(selectedDepartment);
            SwitchRoot.switchRoot(Start.st, RootFactory.getDailyStatsRoot(parent, "N/A"));
        } else if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("verifyhod")) {
            SystemUtils.setDepartment(selectedDepartment);
            SwitchRoot.switchRoot(Start.st, RootFactory.getPendingRequestRoot(parent, SystemUtils.getDepartment(), false));
        } else if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("settings")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getDeleteLoginUserRoot(parent, selectedDepartment));
        } else if (selectedDepartment != null && !selectedDepartment.isEmpty() && type.equals("BlockLogin")) {
            SwitchRoot.switchRoot(Start.st, RootFactory.getBlockLoginUserRoot(parent, selectedDepartment));
        }
    }
}
