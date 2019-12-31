/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Programmer Hrishav
 */
public class ModifyUsertypeController extends AnchorPane {

    @FXML
    private TextField entername;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXComboBox<String> selectdepartment;

    @FXML
    private JFXButton apply;

    @FXML
    private VBox list;

    private FXMLLoader fxml;
    private Parent parent;

    private Login dao;

    public ModifyUsertypeController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getModifyUserFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ModifyUsertypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        selectdepartment.getItems().setAll(Arrays.asList(SystemUtils.getDepartments()));
        load(null);
        refresh.setOnAction(this::load);
        apply.setOnAction(this::filterdepartment);
        search.setOnAction(this::search);
    }

    private void load(ActionEvent evt) {
        List<User> users = Stream.concat(dao.findByType("HOD").stream(), dao.findByType("Faculty").stream()).collect(Collectors.toList());
        List<ModifyUsertypeControllerNode> nodes = users.stream().map(ModifyUsertypeControllerNode::new).collect(Collectors.toList());

        list.getChildren().setAll(nodes);
    }

    private void filterdepartment(ActionEvent evt) {
        String department = selectdepartment.getSelectionModel().getSelectedItem();
        if (department != null || !department.isEmpty()) {
            List<User> users = Stream.concat(dao.findByType("HOD").stream(), dao.findByType("Faculty").stream()).collect(Collectors.toList());
            users = users.stream().filter(f->f.getDepartment().equals(department)).collect(Collectors.toList());
            List<ModifyUsertypeControllerNode> nodes = users.stream().map(ModifyUsertypeControllerNode::new).collect(Collectors.toList());

            list.getChildren().setAll(nodes);
        }
    }
    
    private void search(ActionEvent evt) {
        List<ModifyUsertypeControllerNode> nodes = list.getChildren().stream().map(m->(ModifyUsertypeControllerNode)m).collect(Collectors.toList());
        List<ModifyUsertypeControllerNode> filtered = nodes.stream().filter(f->f.getUser().getDetails().getName().contains(entername.getText())).collect(Collectors.toList());
        list.getChildren().setAll(filtered);
    }

}
