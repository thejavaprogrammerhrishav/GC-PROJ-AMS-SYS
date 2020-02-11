/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.controller;

import com.attendance.main.Start;
import com.attendance.marks.dao.UnitTestDao;
import com.attendance.marks.model.UnitTest;
import com.attendance.marks.service.MarksService;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.ExportUTReport;
import com.attendance.util.ExportUTReportTable;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class UnitTestReportController extends AnchorPane {

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXComboBox<String> coursetype;

    @FXML
    private JFXComboBox<String> unittest;

    @FXML
    private JFXButton load;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXButton table;

    @FXML
    private JFXButton report;

    @FXML
    private JFXButton print;

    @FXML
    private TableView<UTReportModel> list;

    @FXML
    private TableColumn<UTReportModel, Integer> slno;

    @FXML
    private TableColumn<UTReportModel, Integer> roll;

    @FXML
    private TableColumn<UTReportModel, String> name;

    @FXML
    private TableColumn<UTReportModel, Integer> total;

    @FXML
    private TableColumn<UTReportModel, Integer> pass;

    @FXML
    private TableColumn<UTReportModel, Integer> obt;

    private Parent parent;
    private FXMLLoader fxml;

    private MarksService dao;
    private ExceptionDialog dialog;

    public UnitTestReportController(Parent parent) {
        this.parent = parent;

        fxml = Fxml.getUnitTestReportFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(UnitTestReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (MarksService) Start.app.getBean("marksservice");
        dao.setParent(this);
        dialog = dao.getEx();
        
        initFilter();
        initializeTable();
        load.setOnAction(this::populateTable);
        clear.setOnAction(eh -> list.getItems().clear());
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        report.setOnAction(this::exportReport);
        table.setOnAction(this::exportTable);
    }

    private void initFilter() {
        acadamicyear.getItems().setAll("1st", "2nd", "3rd");
        acadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            switch (n) {
                case "1st":
                    semester.getItems().setAll("1st", "2nd");
                    break;
                case "2nd":
                    semester.getItems().setAll("3rd", "4th");
                    break;
                case "3rd":
                    semester.getItems().setAll("5th", "6th");
                    break;
            }
        });
        List<Integer> years = dao.get("select distinct(year) from unittest;", Integer.class);
        year.getItems().setAll(years.stream().map(s -> "" + s).collect(Collectors.toList()));

        unittest.getItems().setAll("Unit Test 1", "Unit Test 2");
        coursetype.getItems().setAll("Honours", "Pass");
    }

    private void initializeTable() {
        slno.setCellValueFactory(new PropertyValueFactory<>("slno"));
        roll.setCellValueFactory(new PropertyValueFactory<>("rollno"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        pass.setCellValueFactory(new PropertyValueFactory<>("passing"));
        obt.setCellValueFactory(new PropertyValueFactory<>("marks"));
    }

    private void populateTable(ActionEvent evt) {
        List<UnitTest> utlist = dao.findBySemesterAndAcademicyearAndYear(semester.getSelectionModel().getSelectedItem(), acadamicyear.getSelectionModel().getSelectedItem(), Integer.parseInt(year.getSelectionModel().getSelectedItem()));
        utlist = utlist.stream().filter(f->f.getDepartment().equals(SystemUtils.getDepartment())).filter(p -> p.getCoursetype().equals(coursetype.getSelectionModel().getSelectedItem())
                && p.getUnitTest().equals(unittest.getSelectionModel().getSelectedItem())
                && p.getDepartment().equals(SystemUtils.getDepartment()))
                .collect(Collectors.toList());

        List<UTReportModel> collect = utlist.stream().map(new Function<UnitTest, UTReportModel>() {

            private int i = 1;

            @Override
            public UTReportModel apply(UnitTest t) {
                return new UTReportModel(i++, t.getRollno(), t.getName(), t.getTotalMarks(), t.getPassingMarks(), t.getMarksObtained());
            }
        }).collect(Collectors.toList());

        list.getItems().setAll(collect);
    }

    private void exportReport(ActionEvent evt) {
        ExportUTReport exp = new ExportUTReport(list);
        try {
            exp.createFile().convertToExcel("Unit Test Report").exportToFile(this);
            dialog.showSuccess(this, "Export Unit Test Report", "Report Exported Successfully");
        } catch (IOException ex) {
            dialog.showError(this, "Export Unit Test Report", "Report Export Failed\n"+ex.getLocalizedMessage());
        }
    }
    private void exportTable(ActionEvent evt) {
        ExportUTReportTable exp = new ExportUTReportTable(list);
        try {
            exp.createFile().convertToExcel("Unit Test Report Table").exportToFile(this);
            dialog.showSuccess(this, "Export Unit Test ", "Exported Successfully");
        } catch (IOException ex) {
            dialog.showError(this, "Export Unit Test ", "Export Failed\n"+ex.getLocalizedMessage());
        }
    }
}
