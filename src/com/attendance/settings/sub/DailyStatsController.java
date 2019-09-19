/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.main.Start;
import com.attendance.student.dao.StudentDao;
import com.attendance.studentattendance.dao.DailyStatsDao;
import com.attendance.studentattendance.model.DailyStats;
import com.attendance.util.DailyStatsUtilModel;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
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
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Programmer Hrishav
 */
public class DailyStatsController extends AnchorPane {

    @FXML
    private TableView<DailyStatsUtilModel> table;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tclassdate;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tclasstime;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tsemester;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tyear;

    @FXML
    private TableColumn<DailyStatsUtilModel, Integer> tstudents;

    @FXML
    private TableColumn<DailyStatsUtilModel, Integer> tpresent;

    @FXML
    private TableColumn<DailyStatsUtilModel, Integer> tabsent;

    @FXML
    private TableColumn<DailyStatsUtilModel, Double> tpresentper;

    @FXML
    private TableColumn<DailyStatsUtilModel, Double> tabsentper;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tacadamicyear;

    @FXML
    private TableColumn<DailyStatsUtilModel, String> tcoursetype;

    @FXML
    private JFXCheckBox filterbysemester;

    @FXML
    private JFXCheckBox filterbymonth;

    @FXML
    private JFXComboBox<String> semester;

    @FXML
    private JFXComboBox<String> month;

    @FXML
    private JFXCheckBox filterbyyear;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private JFXButton applyfilters;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton close;

    @FXML
    private JFXCheckBox filterbyacadamicyear;

    @FXML
    private JFXComboBox<String> acadamicyear;

    @FXML
    private JFXCheckBox filterbycoursetype;

    @FXML
    private JFXComboBox<String> coursertype;

    @FXML
    private Label department;

    private FXMLLoader fxml;

    private DailyStatsDao dao;
    private StudentDao sdao;

    private Parent parent;

    public DailyStatsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getDailyStatsFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(DailyStatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        department.setText(SystemUtils.getDepartment());
        sdao = (StudentDao) Start.app.getBean("studentregistration");
        dao = (DailyStatsDao) Start.app.getBean("dailystats");
        initFilters();
        initTable();
        populateTable(null);

        applyfilters.setOnAction(this::applyfilters);
        refresh.setOnAction(this::populateTable);
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
    }

    private void initFilters() {
        semester.disableProperty().bind(filterbysemester.selectedProperty().not());
        year.disableProperty().bind(filterbyyear.selectedProperty().not());
        month.disableProperty().bind(filterbymonth.selectedProperty().not());
        coursertype.disableProperty().bind(filterbycoursetype.selectedProperty().not());
        acadamicyear.disableProperty().bind(filterbyacadamicyear.selectedProperty().not());

        acadamicyear.getItems().setAll("1st", "2nd", "3rd");
        coursertype.getItems().setAll("Honours", "Pass");

        acadamicyear.getSelectionModel().selectedItemProperty().addListener((ol, o, n) -> {
            if (n.equals("1st")) {
                semester.getItems().setAll("1st Semester", "2nd Semester");
            } else if (n.equals("2nd")) {
                semester.getItems().setAll("3rd Semester", "4th Semester");
            } else {
                semester.getItems().setAll("5th Semester", "6th Semester");
            }
        });

        filterbyacadamicyear.selectedProperty().addListener((ol, o, n) -> {
            if (!n) {
                filterbysemester.setSelected(false);
            }
        });

        month.getItems().setAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        List<String> years = sdao.get("select distinct(year) from student order by year", String.class);
        year.getItems().setAll(years);
    }

    private void initTable() {
        tclassdate.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("date"));
        tclasstime.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("time"));
        tsemester.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("semester"));
        tyear.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("year"));
        tstudents.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Integer>("totalStudent"));
        tpresent.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Integer>("totalPresent"));
        tabsent.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Integer>("totalAbsent"));
        tpresentper.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Double>("presentPercentage"));
        tabsentper.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, Double>("absentPercentage"));
        tacadamicyear.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("acadamicyear"));
        tcoursetype.setCellValueFactory(new PropertyValueFactory<DailyStatsUtilModel, String>("coursetype"));
    }

    private void populateTable(ActionEvent evt) {
        List<DailyStats> list = dao.findAll();
        List<DailyStatsUtilModel> nlist = list.stream().map(a -> {
            DailyStatsUtilModel at = new DailyStatsUtilModel();
            at.setTotalStudent(a.getTotalStudent());
            at.setTotalPresent(a.getTotalPresent());
            at.setTotalAbsent(a.getTotalAbsent());

            at.setPresentPercentage(a.getPresentPercentage());
            at.setAbsentPercentage(a.getAbsentPercentage());

            String s = a.getClassId();
            String[] sa = s.split("/");
            String[] ss = sa[1].split("@");
            at.setDate(ss[0]);

            ss = ss[1].split("#");
            at.setTime(ss[0]);

            ss = ss[1].split("__");
            at.setAcadamicyear(ss[0]);

            ss = ss[1].split("_");
            at.setSemester(ss[0] + " Semester");
            System.out.println(ss[1]);
            ss = ss[1].split("&");
            System.out.println(ss.length);
            at.setYear(ss[0]);

            if (a.getClassId().charAt(a.getClassId().length() - 1) == 'H') {
                at.setCoursetype("Honours");
            }
            if (a.getClassId().charAt(a.getClassId().length() - 1) == 'P') {
                at.setCoursetype("Pass");
            }

            return at;
        }).collect(Collectors.toList());
        table.getItems().setAll(nlist);
    }

    private void applyfilters(ActionEvent evt) {
        List<DailyStats> list = dao.findAll();
        List<DailyStatsUtilModel> nlist = list.stream().map(a -> {
            DailyStatsUtilModel at = new DailyStatsUtilModel();
            at.setTotalStudent(a.getTotalStudent());
            at.setTotalPresent(a.getTotalPresent());
            at.setTotalAbsent(a.getTotalAbsent());

            at.setPresentPercentage(at.getPresentPercentage());
            at.setAbsentPercentage(a.getAbsentPercentage());

            String s = a.getClassId();
            String[] sa = s.split("/");
            String[] ss = sa[1].split("@");
            at.setDate(ss[0]);

            ss = ss[1].split("#");
            at.setTime(ss[0]);

            ss = ss[1].split("__");
            at.setAcadamicyear(ss[0]);

            ss = ss[1].split("_");
            at.setSemester(ss[0] + " Semester");
            ss = ss[1].split("&");
            at.setYear(ss[0]);

            if (a.getClassId().charAt(a.getClassId().length() - 1) == 'H') {
                at.setCoursetype("Honours");
            }
            if (a.getClassId().charAt(a.getClassId().length() - 1) == 'P') {
                at.setCoursetype("Pass");
            }

            return at;
        }).collect(Collectors.toList());

        if (filterbyacadamicyear.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getAcadamicyear().equals(acadamicyear.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }
        if (filterbymonth.isSelected()) {
            int mm = month.getSelectionModel().getSelectedIndex() + 1;
            if (mm < 10) {
                nlist = nlist.stream().filter(s -> s.getDate().contains("/0" + mm + "/")).collect(Collectors.toList());
            } else {
                nlist = nlist.stream().filter(s -> s.getDate().contains("/" + mm + "/")).collect(Collectors.toList());
            }
        }
        if (filterbysemester.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getSemester().equals(semester.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        if (filterbyyear.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getYear().equals(year.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        if (filterbycoursetype.isSelected()) {
            nlist = nlist.stream().filter(s -> s.getCoursetype().equals(coursertype.getSelectionModel().getSelectedItem())).collect(Collectors.toList());
        }

        table.getItems().setAll(nlist);
    }
}
