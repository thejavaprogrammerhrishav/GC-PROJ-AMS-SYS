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
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Programmer Hrishav
 */
public class BlockLoginUserController extends AnchorPane {
    
      @FXML
    private Label department;

    @FXML
    private JFXButton close;

    @FXML
    private TextField entername;

    @FXML
    private JFXButton search;

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private Label email;

    @FXML
    private Label contact;

    @FXML
    private ImageView image;

    @FXML
    private JFXButton onhold;

    @FXML
    private Label usertype;

    @FXML
    private JFXButton decline;

    @FXML
    private VBox list;

    @FXML
    private JFXButton refresh;
    
    private FXMLLoader fxml;
    private String currentdepartment;
    private Parent parent;
    private Login dao;
    private User user;

    public BlockLoginUserController(Parent parent,String currentdepartment) {
        this.parent = parent;
        this.currentdepartment = currentdepartment;
        fxml = Fxml.getBlockLoginUserFXML();
        fxml.setController(this);
        fxml.setRoot(this);
          try {
              fxml.load();
          } catch (IOException ex) {
              Logger.getLogger(BlockLoginUserController.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
     @FXML
    private void initialize() {
        dao = (Login) Start.app.getBean("userlogin");
        close.setOnAction(eh -> SwitchRoot.switchRoot(Start.st, parent));
        department.setText(currentdepartment);

        load(null);

        search.setOnAction(this::search);
        refresh.setOnAction(this::load);
        onhold.setOnAction(this::onhold);
        decline.setOnAction(this::decline);
    }

    private void load(ActionEvent evt) {
        List<User> users = dao.findByDepartment(currentdepartment);
        List<User> departmentusers = users.stream().filter(f -> f.getType().equals("HOD") || f.getType().equals("Faculty")).collect(Collectors.toList());

        List<DeleteLoginUserNodeController> nodes = departmentusers.stream().map(DeleteLoginUserNodeController::new).collect(Collectors.toList());
        nodes.stream().forEach(c -> {
            c.getAction().setOnAction(e -> {
                display(c.getUser());
                user = c.getUser();
            });

        });
        list.getChildren().setAll(nodes);
    }

    private void display(User user) {
        name.setText(user.getDetails().getName());
        username.setText(user.getUsername());
        email.setText(user.getDetails().getEmailId());
        contact.setText(user.getDetails().getContact());
        usertype.setText(user.getType());
        image.setImage(new Image(new ByteArrayInputStream(user.getImage())));
    }

    private void onhold(ActionEvent evt) {
        user.setStatus("OnHold");
        dao.update(user);
        MessageUtil.showInformation(Message.INFORMATION, "BLOCK LOGIN USER", "User status set to Onhold Successfully", Start.st);
    }
    
    private void decline(ActionEvent evt) {
        user.setStatus("Decline");
        dao.update(user);
        MessageUtil.showInformation(Message.INFORMATION, "BLOCK LOGIN USER", "User status set to Decline Successfully", Start.st);
    }

    private void search(ActionEvent evt) {
        List<DeleteLoginUserNodeController> nodes = list.getChildren().stream().map(m -> (DeleteLoginUserNodeController) m).collect(Collectors.toList());
        List<DeleteLoginUserNodeController> filtered = nodes.stream().filter(f -> f.getUser().getDetails().getName().contains(entername.getText())).collect(Collectors.toList());
        list.getChildren().setAll(filtered);
    }
}
