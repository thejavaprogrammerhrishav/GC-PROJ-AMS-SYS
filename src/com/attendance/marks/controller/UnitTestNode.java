/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.controller;

import com.attendance.student.model.Student;
import com.attendance.util.Fxml;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class UnitTestNode extends AnchorPane{
    
    @FXML
    private Label slno;

    @FXML
    private Label rollno;

    @FXML
    private Label name;

    @FXML
    private TextField marksobtained;
    
    private FXMLLoader fxml;

    private Student student;
    private int count;
    
    public UnitTestNode(int count,Student student) {
        this.count=count;
        this.student=student;
        fxml=Fxml.getUploadMarksNodeFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UnitTestNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void initialize(){
        slno.setText(""+count);
        name.setText(student.getName());
        rollno.setText(""+student.getRollno());
    }
    
    public int getMarksObtained(){
        return Integer.parseInt(marksobtained.getText());
    }
    
    public Integer getRollNo(){
        return Integer.parseInt(rollno.getText());
    }
    
    public Student getStudent(){
        return student;
    }
    
    public Integer getSerial() {
        return count;
    }
    
}
