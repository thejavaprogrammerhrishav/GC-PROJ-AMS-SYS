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
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class DepartmentPageController extends AnchorPane {

    @FXML
    private JFXButton anthropology;

    @FXML
    private JFXButton assamese;

    @FXML
    private JFXButton bengali;

    @FXML
    private JFXButton biotechnology;

    @FXML
    private JFXButton botany;

    @FXML
    private JFXButton businessadministration;

    @FXML
    private JFXButton chemistry;

    @FXML
    private JFXButton computerscience;

    @FXML
    private JFXButton economics;

    @FXML
    private JFXButton english;

    @FXML
    private JFXButton geology;

    @FXML
    private JFXButton hindi;

    @FXML
    private JFXButton manipuri;

    @FXML
    private JFXButton mathematics;

    @FXML
    private JFXButton history;

    @FXML
    private JFXButton persian;

    @FXML
    private JFXButton masscommunication;

    @FXML
    private JFXButton philosophy;

    @FXML
    private JFXButton physics;

    @FXML
    private JFXButton politicalscience;

    @FXML
    private JFXButton sanskrit;

    @FXML
    private JFXButton statistics;

    @FXML
    private JFXButton zoology;

    @FXML
    private JFXButton commerce;

    @FXML
    private JFXButton evs;

    @FXML
    private JFXButton back;

    private FXMLLoader fxml;

    public DepartmentPageController() {
        fxml = Fxml.getDepartmentFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DepartmentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        anthropology.setOnAction(this::anthropology);
        assamese.setOnAction(this::assamese);
        bengali.setOnAction(this::bengali);
        biotechnology.setOnAction(this::biotechnology);
        botany.setOnAction(this::botany);
        businessadministration.setOnAction(this::businessadministration);
        chemistry.setOnAction(this::chemistry);
        computerscience.setOnAction(this::computerscience);
        economics.setOnAction(this::economics);
        english.setOnAction(this::english);
        geology.setOnAction(this::geology);
        hindi.setOnAction(this::hindi);
        manipuri.setOnAction(this::manipuri);
        mathematics.setOnAction(this::mathematics);
        history.setOnAction(this::history);
        persian.setOnAction(this::persian);
        masscommunication.setOnAction(this::masscommunication);
        philosophy.setOnAction(this::philosophy);
        physics.setOnAction(this::physics);
        politicalscience.setOnAction(this::politicalscience);
        sanskrit.setOnAction(this::sanskrit);
        statistics.setOnAction(this::statistics);
        zoology.setOnAction(this::zoology);
        commerce.setOnAction(this::commerce);
        evs.setOnAction(this::evs);
        back.setOnAction(this::closeAction);
    }
    
    private void closeAction(ActionEvent evt) {
        SystemUtils.setDepartment("");
        SystemUtils.logout();
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
    }

    private void anthropology(ActionEvent evt) {
        SystemUtils.setDepartment("Anthropology");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void assamese(ActionEvent evt) {
        SystemUtils.setDepartment("Assamese");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void bengali(ActionEvent evt) {
        SystemUtils.setDepartment("Bengali");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void biotechnology(ActionEvent evt) {
        SystemUtils.setDepartment("Biotechnology");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void botany(ActionEvent evt) {
        SystemUtils.setDepartment("Botany");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void businessadministration(ActionEvent evt) {
        SystemUtils.setDepartment("Business Administration");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void chemistry(ActionEvent evt) {
        SystemUtils.setDepartment("Chemistry");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void computerscience(ActionEvent evt) {
        SystemUtils.setDepartment("Computer Science");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void economics(ActionEvent evt) {
        SystemUtils.setDepartment("Economics");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void english(ActionEvent evt) {
        SystemUtils.setDepartment("English");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void geology(ActionEvent evt) {
        SystemUtils.setDepartment("Geology");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void hindi(ActionEvent evt) {
        SystemUtils.setDepartment("Hindi");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void manipuri(ActionEvent evt) {
        SystemUtils.setDepartment("Manipuri");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void mathematics(ActionEvent evt) {
        SystemUtils.setDepartment("Mathematics");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void history(ActionEvent evt) {
        SystemUtils.setDepartment("History");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void persian(ActionEvent evt) {
        SystemUtils.setDepartment("Persian");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void masscommunication(ActionEvent evt) {
        SystemUtils.setDepartment("Mass Communication");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void philosophy(ActionEvent evt) {
        SystemUtils.setDepartment("Philosophy");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void physics(ActionEvent evt) {
        SystemUtils.setDepartment("Physics");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void politicalscience(ActionEvent evt) {
        SystemUtils.setDepartment("Political Science");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void sanskrit(ActionEvent evt) {
        SystemUtils.setDepartment("Sanskrit");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void statistics(ActionEvent evt) {
        SystemUtils.setDepartment("Statistics");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void zoology(ActionEvent evt) {
        SystemUtils.setDepartment("Zoology");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void commerce(ActionEvent evt) {
        SystemUtils.setDepartment("Commerce");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

    private void evs(ActionEvent evt) {
        SystemUtils.setDepartment("Ecology & Environmental Science");
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType2Root());
    }

}
