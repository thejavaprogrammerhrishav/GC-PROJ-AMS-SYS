/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.dao.PersonalDetailsDao;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.hslf.record.CurrentUserAtom;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class RoutineDashboardController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private Label usertype;

    @FXML
    private Label department;

    @FXML
    private Label date;

    @FXML
    private Label contact;

    @FXML
    private JFXButton addnewroutine;

    @FXML
    private JFXButton updateroutine;

    @FXML
    private JFXButton viewroutine;
    
    @FXML
    private JFXButton viewallroutine;

    @FXML
    private AnchorPane list;
    
    @FXML
    private Label name;
    
    @FXML
    private ImageView image;

    private FXMLLoader fxml;

    private Parent parent;
    
    private User currentUser;
    
    private PersonalDetailsDao dao;
    private RoutineDao rdao;

    public RoutineDashboardController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getRoutineDashboardFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(RoutineDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (PersonalDetailsDao) Start.app.getBean("personal");
        rdao = (RoutineDao) Start.app.getBean("routine");
        currentUser = SystemUtils.getCurrentUser();
        PersonalDetails personalDetails = dao.findById(currentUser.getPersonalid());
        
        image.setImage(new Image(new ByteArrayInputStream(currentUser.getImage())));
        name.setText(personalDetails.getName());
        contact.setText(personalDetails.getContact());
        department.setText(currentUser.getDepartment());
        usertype.setText(currentUser.getUsername());
        date.setText(currentUser.getDate());
        
        
        close.setOnAction(e-> SwitchRoot.switchRoot(Start.st, parent));
        
        addnewroutine.setOnAction(this::addRoutine);
        updateroutine.setOnAction(this::updateRoutine);
        viewroutine.setOnAction(this::viewactiveRoutine);
        viewallroutine.setOnAction(this::viewallRoutine);
    }
    
    private void addRoutine(ActionEvent evt) {
        list.getChildren().setAll(Arrays.asList(new AddNewRoutineController()));
    }
    
    private void updateRoutine(ActionEvent evt) {
        list.getChildren().setAll(Arrays.asList(new UpdateActiveRoutineController()));
    }
    
    private void viewactiveRoutine(ActionEvent evt) {
        if (rdao.hasActiveRoutine(currentUser.getDepartment(), DateTime.now().toString(DateTimeFormat.forPattern("yyyy")))== 1) {
            Routine r = rdao.findByDepartmentAndDateAndStatus(currentUser.getDepartment(), DateTime.now().toString(DateTimeFormat.forPattern("yyyy")), "Active");
        list.getChildren().setAll(Arrays.asList(new ViewActiveRoutineController(r)));
        }else {
            Routine rs = new Routine();
            byte[] nor = SystemUtils.getByteArrayFromImage(SystemUtils.getICONS().get("noroutine"));
            rs.setImage(nor);
            list.getChildren().setAll(Arrays.asList(new ViewActiveRoutineController(rs)));
        }
    }
    
    private void viewallRoutine(ActionEvent evt) {
        list.getChildren().setAll(Arrays.asList(new ViewAllRoutineController()));
    }
}
