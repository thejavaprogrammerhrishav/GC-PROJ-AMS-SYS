/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class ModifyUsertypeControllerNode extends AnchorPane {

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private JFXButton hod;

    @FXML
    private JFXButton faculty;

    private FXMLLoader fxml;
    private User user;

    private LoginService dao;
    private ExceptionDialog dialog;

    public ModifyUsertypeControllerNode(User user) {
        this.user = user;
        fxml = Fxml.getModifyUserNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ModifyUsertypeControllerNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (LoginService) Start.app.getBean("loginservice");
        dao.setParent(this);
        dialog = dao.getEx();

        name.setText(user.getDetails().getName());
        image.setImage(new Image(new ByteArrayInputStream(user.getImage())));

        if (user.getType().equals("HOD")) {
            hod.setDisable(true);
        }

        if (user.getType().equals("Faculty")) {
            faculty.setDisable(true);
        }

        hod.setOnAction(this::hod);
        faculty.setOnAction(this::faculty);
    }

    public User getUser() {
        return user;
    }

    private void faculty(ActionEvent evt) {
        user.setType("Faculty");
        boolean b = dao.updateUser(user);
        if (b) {
            dialog.showSuccess(this, "Modify User Type", "User Type Changed Successfully");
        } else {
            dialog.showError(this, "Modify User Type", "User Type Changing Failed");
        }

    }

    private void hod(ActionEvent evt) {
        List<User> users = dao.findByDepartment(user.getDepartment());
        users.stream().forEach(c -> {
            c.setType("Faculty");
            dao.updateUser(c);
        });

        user.setType("HOD");
        boolean b = dao.updateUser(user);
        if (b) {
            dialog.showSuccess(this, "Modify User Type", "User Type Changed Successfully");
        } else {
            dialog.showError(this, "Modify User Type", "User Type Changing Failed");
        }
    }
}
