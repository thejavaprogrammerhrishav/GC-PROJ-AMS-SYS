/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.request;

import com.attendance.login.dao.Login;
import com.attendance.main.Start;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author pc
 */
public class PendingRequestController extends AnchorPane {
    
    @FXML
    private JFXButton today;
    
    @FXML
    private JFXButton thismonth;
    
    @FXML
    private JFXButton older;
    
    @FXML
    private JFXButton allpendingrequest;
    
    @FXML
    private JFXButton accepted;
    
    @FXML
    private JFXButton onhold;
    
    @FXML
    private JFXButton decline;
    
    @FXML
    private JFXButton close;
    
    @FXML
    private TextField searchhere;
    
    @FXML
    private JFXButton search;
    
    @FXML
    private JFXButton refresh;
    
    @FXML
    private Label requesttype;
    
    @FXML
    private Label department;
    
    @FXML
    private Label totalpendingrequest;
    
    @FXML
    private Label usertype;
    
    @FXML
    private VBox list;
    
    private FXMLLoader fxml;
    private Parent parent;
    private String sdepartment;
    
    private Login login;
    
    private String reqtype;
    
    public PendingRequestController(Parent parent, String sdepartment) {
        
        this.parent = parent;
        this.sdepartment = sdepartment;
        fxml = Fxml.getPendingRequestFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PendingRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize() {
        login = (Login) Start.app.getBean("userlogin");
        department.setText(sdepartment);
        usertype.setText(SystemUtils.getCurrentUser().getType());
        switch (SystemUtils.getCurrentUser().getType()) {
            case "Principal":
                reqtype = "HOD";
                break;
            case "HOD":
                reqtype = "Faculty";
                break;
            default:
                reqtype = "";
        }
        requesttype.setText(reqtype);
        
        int count = login.count("select count(*) from loginuser where status = 'Pending' and type = '" + reqtype + "' and department = '" + sdepartment + "'");
        totalpendingrequest.setText("" + count);
        
         buttonActions() ;
    }
    
    private void buttonActions() {
        close.setOnAction(e-> SwitchRoot.switchRoot(Start.st, parent));
    }
    
}
