/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.main.Start;
import com.attendance.util.ExportUserLoginActivity;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.joda.time.DateTimeComparator;

/**
 *
 * @author Programmer Hrishav
 */
public class PrincipalLoginActivitySettingsController extends AnchorPane {

    @FXML
    private JFXCheckBox filterbyname;

    @FXML
    private JFXCheckBox filterbydate;


    @FXML
    private JFXCheckBox ascending;

    @FXML
    private JFXCheckBox descending;

    @FXML
    private TableView<LoginActivity> table;

    @FXML
    private TableColumn<LoginActivity, String> name;

    @FXML
    private TableColumn<LoginActivity, String> username;

    @FXML
    private TableColumn<LoginActivity, String> status;

    @FXML
    private TableColumn<LoginActivity, String> date;

    @FXML
    private TableColumn<LoginActivity, String> logintime;

    @FXML
    private TableColumn<LoginActivity, String> logouttime;

    @FXML
    private TextField searchname;

    @FXML
    private JFXDatePicker searchdate;

    @FXML
    private JFXCheckBox sortbydate;

    @FXML
    private JFXButton applyfilters;

    @FXML
    private JFXButton refresh;

    @FXML
    private JFXButton export2excel;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton sort;

    private Parent parent;

    private FXMLLoader fxml;

    private Activity dao;
    private List<LoginActivity> list;

    public PrincipalLoginActivitySettingsController(Parent parent) {
        this.parent = parent;
        fxml = Fxml.getPrincipalLoginActivitySettingsFXML();
        fxml.setRoot(this);
        fxml.setController(this);

        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalLoginActivitySettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (Activity) Start.app.getBean("loginactivity");
        searchname.disableProperty().bind(filterbyname.selectedProperty().not());
        searchdate.disableProperty().bind(filterbydate.selectedProperty().not());

        ascending.disableProperty().bind(sortbydate.selectedProperty().not());
        descending.disableProperty().bind(sortbydate.selectedProperty().not());
        
        ascending.selectedProperty().addListener((ol,o,n)->{
            if(n) {
                ascending.setSelected(true);
                descending.setSelected(false);
            }
        });
        
        descending.selectedProperty().addListener((ol,o,n)->{
            if(n) {
                descending.setSelected(true);
                ascending.setSelected(false);
            }
        });

        initTable();
        populateTable(null);

        cancel.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        refresh.setOnAction(this::populateTable);
        applyfilters.setOnAction(this::filters);
        export2excel.setOnAction(this::export);
        sort.setOnAction(this::sorted);
    }

    private void initTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        date.setCellValueFactory(new PropertyValueFactory<>("logindate"));
        logintime.setCellValueFactory(new PropertyValueFactory<>("logintime"));
        logouttime.setCellValueFactory(new PropertyValueFactory<>("logouttime"));
    }

    private void populateTable(ActionEvent evt) {
        reinit();
        table.getItems().setAll(list);
    }

    private void reinit() {
        list = dao.findByUserType("Principal");
    }

    private void filters(ActionEvent evt) {
        List<LoginActivity> filterdList = table.getItems();
        if (filterbydate.isSelected()) {
            filterdList = filterdList.stream().filter(l -> l.getLogindate().equals(searchdate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))).collect(Collectors.toList());
        }
        if (filterbyname.isSelected()) {
            filterdList = filterdList.stream().filter(l -> l.getName().startsWith(searchname.getText())).collect(Collectors.toList());
        }
        table.getItems().setAll(filterdList);
    }

    private void export(ActionEvent evt) {
        ExportUserLoginActivity exp = new ExportUserLoginActivity(table);
        try {
            exp.createFile().convertToExcel("User Login Activity").exportToFile();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalLoginActivitySettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sorted(ActionEvent evt) {
        List<LoginActivity> list;

        if (sortbydate.isSelected()) {
            if (ascending.isSelected()) {
                list = dao.get(" select * from loginactivity where usertype = 'Principal' order by str_to_date(logindate, '%d-%m-%y') asc");
            } else if (descending.isSelected()) {
                list = dao.get(" select * from loginactivity where usertype = 'Principal' order by str_to_date(logindate, '%d-%m-%y') desc");
            } else {
                list = dao.findByUserType("Principal");
            }
        } else {
            list = dao.findByUserType("Principal");
        }
        table.getItems().setAll(list);
    }
}
