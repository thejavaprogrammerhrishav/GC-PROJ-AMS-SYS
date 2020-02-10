/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.papers.controller;

import com.attendance.main.Start;
import com.attendance.papers.dao.PapersDao;
import com.attendance.papers.model.Paper;
import com.attendance.papers.service.PapersService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.Message;
import com.attendance.util.MessageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.attendance.util.ValidationUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.validation.ConstraintViolation;

/**
 *
 * @author pc
 */
public class PapersController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private TextField papercode;

    @FXML
    private TextField papername;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private Label department;

    @FXML
    private JFXComboBox<String> coursetype;

    @FXML
    private JFXButton addpaper;

    @FXML
    private JFXButton updatepaper;

    @FXML
    private JFXButton deletepaper;

    @FXML
    private TextField searchbar;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXComboBox<String> selecttype;

    @FXML
    private JFXComboBox<String> selectsemester;

    @FXML
    private JFXButton applyfilter;

    @FXML
    private JFXButton refresh;

    @FXML
    private TableView<Paper> list;

    @FXML
    private TableColumn<Paper, String> lpapercode;

    @FXML
    private TableColumn<Paper, String> lpapername;

    @FXML
    private TableColumn<Paper, String> lsemester;

    @FXML
    private TableColumn<Paper, String> ldepartment;

    @FXML
    private TableColumn<Paper, String> lcoursetype;

    private FXMLLoader fxml;

    private PapersService dao;

    private Paper paper;

    private Parent parent;

    private ExceptionDialog dialog;

    private long id;

    public PapersController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getPaperFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PapersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (PapersService) Start.app.getBean("papersservice");
        dao.setParent(this);
        dialog = dao.getEx();

        selecttype.disableProperty().bind(filterbycoursetype.selectedProperty().not());
        selectsemester.disableProperty().bind(filterbysemester.selectedProperty().not());

        selecttype.getItems().addAll("Honours", "Pass");
        selectsemester.getItems().addAll("1st", "2nd", "3rd", "4th", "5th", "6th");

        coursetype.getItems().addAll("Honours", "Pass");
        semester.getItems().addAll("1st", "2nd", "3rd", "4th", "5th", "6th");

        department.setText(SystemUtils.getDepartment());

        initializetable();
        refreshtable(null);

        list.setOnMouseClicked(this::tableclick);

        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        refresh.setOnAction(this::refreshtable);
        search.setOnAction(this::search);
        applyfilter.setOnAction(this::filter);
        addpaper.setOnAction(this::addpaper);
        updatepaper.setOnAction(this::updatepaper);
        deletepaper.setOnAction(this::deletepaper);
        clear.setOnAction(this::clear);
    }

    private void initializetable() {
        lpapercode.setCellValueFactory(new PropertyValueFactory<>("paperCode"));
        lpapername.setCellValueFactory(new PropertyValueFactory<>("paperName"));
        lsemester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        ldepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        lcoursetype.setCellValueFactory(new PropertyValueFactory<>("coursetype"));

    }

    private void refreshtable(ActionEvent evt) {
        List<Paper> allpapers = dao.findByDepartment(SystemUtils.getDepartment());
        list.getItems().setAll(allpapers);
    }

    private void search(ActionEvent evt) {
        List<Paper> items = list.getItems();
        items = items.stream().filter(p -> p.getPaperCode().toLowerCase().contains(searchbar.getText().trim().toLowerCase())).collect(Collectors.toList());
        list.getItems().setAll(items);
    }

    private void filter(ActionEvent evt) {
        List<Paper> items = list.getItems();
        if (filterbycoursetype.isSelected()) {
            items = items.stream().filter(p -> p.getCoursetype().equals(selecttype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbysemester.isSelected()) {
            items = items.stream().filter(p -> p.getSemester().equals(selectsemester.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        list.getItems().setAll(items);

    }

    private void tableclick(MouseEvent evt) {
        Paper item = list.getSelectionModel().getSelectedItem();
        if (item != null) {
            papercode.setText(item.getPaperCode());
            papername.setText(item.getPaperName());
            semester.getSelectionModel().select(item.getSemester());
            coursetype.getSelectionModel().select(item.getCoursetype());
            id = item.getId();
        }

    }

    private void addpaper(ActionEvent evt) {
        if (!dao.exists(papercode.getText())) {
            paper = new Paper();
            id = -1;
            paper.setPaperCode(papercode.getText());
            paper.setPaperName(papername.getText());
            paper.setSemester(semester.getSelectionModel().getSelectedItem());
            paper.setDepartment(department.getText());
            paper.setCoursetype(coursetype.getSelectionModel().getSelectedItem());

            Set<ConstraintViolation<Paper>> validate = ValidationUtils.getValidator().validate(paper);
            if (validate.size() == 0) {
                long id = dao.savePaper(paper);
                if (id > 0) {
                    dialog.showSuccess(this, "Add New Paper", "New Paper Added Successfully");
                    refreshtable(evt);
                }
            } else {
                validate.stream().forEach(c -> {
                    dialog.showError(this, "Add New Paper", c.getMessage());
                });
            }
        } else {
            dialog.showError(this, "Add New Paper", "Paper Code Already Exists");
        }
    }

    private void updatepaper(ActionEvent evt) {
        paper = new Paper();
        paper.setId(id);
        paper.setPaperCode(papercode.getText());
        paper.setPaperName(papername.getText());
        paper.setSemester(semester.getSelectionModel().getSelectedItem());
        paper.setDepartment(department.getText());
        paper.setCoursetype(coursetype.getSelectionModel().getSelectedItem());

        Set<ConstraintViolation<Paper>> validate = ValidationUtils.getValidator().validate(paper);
        if (validate.size() == 0) {
            boolean id = dao.updatePaper(paper);
            if (id) {
                dialog.showSuccess(this, "Update Paper Details", "Paper Details Updated Successfully");
                refreshtable(evt);
            } else {
                dialog.showError(this, "Update Paper Details", "Paper Details Updation Failed");
            }
        } else {
            validate.stream().forEach(c -> {
                dialog.showError(this, "Add New Paper", c.getMessage());
            });
        }

    }

    private void deletepaper(ActionEvent evt) {
        paper = new Paper();
        paper.setId(id);
        paper.setPaperCode(papercode.getText());
        paper.setPaperName(papername.getText());
        paper.setSemester(semester.getSelectionModel().getSelectedItem());
        paper.setDepartment(department.getText());
        paper.setCoursetype(coursetype.getSelectionModel().getSelectedItem());

        boolean id = dao.deletePaper(paper);
        if (id) {
            dialog.showSuccess(this, "Delete Paper Details", "Paper Details Deleted Successfully");
            refreshtable(evt);
        } else {
            dialog.showError(this, "Delete Paper Details", "Paper Details Deletion Failed");
        }
    }

    private void clear(ActionEvent evt) {
        papercode.setText("");
        papername.setText("");
        semester.getSelectionModel().clearSelection();
        coursetype.getSelectionModel().clearSelection();

        filterbycoursetype.setSelected(false);
        filterbysemester.setSelected(false);

        selectsemester.getSelectionModel().clearSelection();
        selecttype.getSelectionModel().clearSelection();
    }

}
