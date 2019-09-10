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
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class UpdatePaperController extends AnchorPane {

    @FXML
    private TextField papercode;

    @FXML
    private TextField papername;

    @FXML
    private JFXButton updatepaper;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton search;

    @FXML
    private TableView<Paper> table;

    @FXML
    private TableColumn<Paper, String> code;

    @FXML
    private TableColumn<Paper, String> name;

    @FXML
    private TableColumn<Paper, String> semester;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXComboBox<String> semesterlist;

    @FXML
    private TextField searchpaper;

    @FXML
    private JFXButton refresh;

    @FXML
    private TextField papersemester;

    @FXML
    private TextField department;

    @FXML
    private TextField coursetype;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXComboBox<String> scoursetype;

    @FXML
    private JFXCheckBox filterbydepartment;

    @FXML
    private JFXComboBox<String> sdepartment;
    
    @FXML
    private JFXButton applyfilter;
    

    private PapersDao dao;
    private Paper paper;

    private FXMLLoader fxml;

    public UpdatePaperController() {
        fxml = Fxml.getUpdatePaperFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UpdatePaperController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (PapersDao) Start.app.getBean("papers");
        semesterlist.getItems().addAll("1st", "2nd", "3rd", "4th", "5th", "6th");
        scoursetype.getItems().addAll("Honours", "Pass");
        sdepartment.getItems().setAll(SystemUtils.getDepartments());
        initFilters();
        initTable();
        populateTable(null);

        search.setOnAction(this::search);
        refresh.setOnAction(this::refresh);
        cancel.setOnAction(this::cancel);
        updatepaper.setOnAction(this::update);
        table.setOnMouseClicked(this::tableClick);
        applyfilter.setOnAction(this::apply);
    }

    private void initFilters() {
        semesterlist.disableProperty().bind(filterbysemester.selectedProperty().not());
        sdepartment.disableProperty().bind(filterbydepartment.selectedProperty().not());
        scoursetype.disableProperty().bind(filterbycoursetype.selectedProperty().not());
      
        
    }

    private void initTable() {
        code.setCellValueFactory(new PropertyValueFactory<Paper, String>("paperCode"));
        name.setCellValueFactory(new PropertyValueFactory<Paper, String>("paperName"));
        semester.setCellValueFactory(new PropertyValueFactory<Paper, String>("semester"));
    }

    private void populateTable(ActionEvent evt) {
        List<Paper> list = dao.findAll();
        table.getItems().setAll(list);
    }

    private void search(ActionEvent evt) {
        List<Paper> list = table.getItems();
        list = list.stream().filter(f -> f.getPaperCode().contains(searchpaper.getText())).collect(Collectors.toList());
        table.getItems().setAll(list);
    }

    private void refresh(ActionEvent evt) {
        populateTable(evt);
    }

    private void cancel(ActionEvent evt) {
        ((BorderPane) this.getParent()).setCenter(null);
    }

    private void tableClick(MouseEvent evt) {
        paper = table.getSelectionModel().getSelectedItem();
        papercode.setText(paper.getPaperCode());
        papername.setText(paper.getPaperName());
        papersemester.setText(paper.getSemester());
        department.setText(paper.getDepartment());
        coursetype.setText(paper.getCoursetype());
    }

    private void update(ActionEvent evt) {
        Paper updatePaper = new Paper();
        updatePaper.setPaperName(papername.getText());
        updatePaper.setSemester(papersemester.getText());
        updatePaper.setDepartment(department.getText());
        updatePaper.setCoursetype(coursetype.getText());
        updatePaper.setPaperCode(paper.getPaperCode());

        boolean b1 = dao.update(updatePaper);

        updatePaper.setPaperCode(papercode.getText());

        boolean b2 = dao.updatePaperId(updatePaper.getPaperCode(), paper.getPaperCode());

        if (b1 && b2) {
            MessageUtil.showError(Message.INFORMATION, "Update Paper", "Paper Updated Successfully", UpdatePaperController.this.getScene().getWindow());
        } else {
            MessageUtil.showError(Message.ERROR, "Update Paper", "Paper Updation Failed", UpdatePaperController.this.getScene().getWindow());
        }
    }
    
    private void apply(ActionEvent evt){
        if(filterbydepartment.isSelected()){
            List<Paper> list=table.getItems();
            list=list.stream().filter(f->f.getDepartment().equals(sdepartment.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
            table.getItems().setAll(list);
        }
          if(filterbycoursetype.isSelected()){
            List<Paper> list=table.getItems();
            list=list.stream().filter(f->f.getCoursetype().equals(scoursetype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
            table.getItems().setAll(list);
        }
          if(filterbysemester.isSelected()){
            List<Paper> list=table.getItems();
            list=list.stream().filter(f->f.getSemester().equals(semesterlist.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
            table.getItems().setAll(list);
        }
       
    }
}
