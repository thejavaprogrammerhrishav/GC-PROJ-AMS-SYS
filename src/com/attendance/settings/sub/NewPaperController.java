/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;

/**
 *
 * @author Programmer Hrishav
 */
public class NewPaperController extends AnchorPane {

    @FXML
    private TextField papercode;

    @FXML
    private TextField papername;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXButton savepaper;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXComboBox<String> department;

    @FXML
    private JFXComboBox<String> coursetype;

    private Paper paper;
    private PapersDao dao;
    private FXMLLoader fxml;

    public NewPaperController() {
        fxml = Fxml.getNewPaperFXML();
        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(NewPaperController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (PapersDao) Start.app.getBean("papers");
        semester.getItems().addAll("1st", "2nd", "3rd", "4th", "5th", "6th");
        coursetype.getItems().addAll("Honours", "Pass");
        department.getItems().setAll(SystemUtils.getDepartments());
        savepaper.setOnAction(this::save);
        cancel.setOnAction(this::cancel);
    }

    private void save(ActionEvent evt) {
        paper = new Paper();
        paper.setPaperCode(papercode.getText());
        paper.setPaperName(papername.getText());
        paper.setSemester(semester.getSelectionModel().getSelectedItem());
        paper.setDepartment(department.getSelectionModel().getSelectedItem());
        paper.setCoursetype(coursetype.getSelectionModel().getSelectedItem());
        String id = dao.save(paper);

        if (!id.isEmpty()) {
            MessageUtil.showInformation(Message.INFORMATION, "New Paper", "Paper Added Successfully", ((Node) evt.getSource()).getScene().getWindow());
        } else {
            MessageUtil.showInformation(Message.ERROR, "New Paper", "Paper Addition Failed", ((Node) evt.getSource()).getScene().getWindow());
        }
    }

    private void cancel(ActionEvent evt) {
        ((BorderPane) this.getParent()).setCenter(null);
    }

}
