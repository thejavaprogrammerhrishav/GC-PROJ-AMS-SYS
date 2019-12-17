/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.faculty.controller;

import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.util.Fxml;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author pc
 */
public class ViewFacultyNodeController extends AnchorPane {

    @FXML
    private ImageView icon;

    @FXML
    private Label name;

    @FXML
    private Label type;

    @FXML
    private JFXButton view;

    private FXMLLoader fxml;
    private User user;

    private PersonalDetails details;

    public ViewFacultyNodeController(User user) {
        this.user = user;

        fxml = Fxml.getViewFacultyNodeFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewFacultyNodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        details = user.getDetails();
        setvalues();
    }

    private void setvalues() {
        icon.setImage(new Image(new ByteArrayInputStream(user.getImage())));
        name.setText(details.getName());
        type.setText(user.getType());
        view.setOnAction(e->SwitchRoot.switchRoot(Start.st, new ViewFacultyDetailsController(user,Start.st.getScene().getRoot())));
    }

    public String getName() {
        return details.getName();
    }
}
