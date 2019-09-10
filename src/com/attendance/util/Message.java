/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

/**
 *
 * @author Programmer Hrishav
 */
public class Message extends AnchorPane {

    public static final int INFORMATION = 0;
    public static final int ERROR = -1;

    @FXML
    private AnchorPane messagebackground;

    @FXML
    private Label messagetype;

    @FXML
    private Label heading;

    @FXML
    private TextArea content;

    @FXML
    private JFXButton close;

    private int type;
    private String header;
    private String cont;
    private Exception ex;

    private FXMLLoader fxml;

    public Message(int type, String header, String cont) {
        this.type = type;
        this.header = header;
        this.cont = cont;

        fxml = Fxml.getMessageFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Message(int type, String header, Exception ex) {
        this.type = type;
        this.header = header;
        this.ex = ex;

        fxml = Fxml.getMessageFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex1) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    @FXML
    private void initialize() {
        switch (type) {
            case 0:
                messagebackground.setStyle("-fx-background-color: green;");
                messagetype.setText("Information Message");
                break;
            case -1:
                messagebackground.setStyle("-fx-background-color: red;");
                messagetype.setText("Error Message");
                break;
            default:
                messagebackground.setStyle("-fx-background-color: black;");
                messagetype.setText("Message");
        }
        
        heading.setText(header);
        if(!cont.isEmpty()){
            content.setText(cont);
        }
        if(ex!=null){
            StringWriter st = new StringWriter();
            ex.printStackTrace(new PrintWriter(st));
            content.setText(st.toString());
        }
        
        close.setOnAction(e->((Node)e.getSource()).getScene().getWindow().hide());
    }
}
