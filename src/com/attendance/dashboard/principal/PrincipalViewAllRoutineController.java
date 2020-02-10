/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.principal;

import com.attendance.main.Start;
import com.attendance.routine.service.RoutineService;
import com.attendance.routines.controller.ViewAllRoutineController;
import com.attendance.routines.controller.ViewAllRoutineNodeController;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.settings.sub.LoadingController;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author pc
 */
public class PrincipalViewAllRoutineController extends AnchorPane {

    @FXML
    private JFXCheckBox filterbydate;

    @FXML
    private JFXButton asc;

    @FXML
    private JFXButton desc;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXButton apply;

    @FXML
    private JFXButton refresh;

    @FXML
    private VBox list;

    private FXMLLoader fxml;
    private RoutineService dao;
    private ExceptionDialog dialog;
    

    private String department;
    private String year;

    public PrincipalViewAllRoutineController(String department, String year) {
        this.department = department;
        this.year = year;
        fxml = Fxml.getViewAllRoutineFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (RoutineService) Start.app.getBean("routineservice");
        dao.setParent(this);
        dialog = dao.getEx();
        
        date.disableProperty().bind(filterbydate.selectedProperty().not());

        loadData(null);

        refresh.setOnAction(this::loadData);
        apply.setOnAction(this::filter);
        asc.setOnAction(this::sortAscending);
        desc.setOnAction(this::sortDescending);
    }

    private void loadData(ActionEvent evt) {
        Task<List<PrincipalViewAllRoutineNodeController>> task = new Task<List<PrincipalViewAllRoutineNodeController>>() {
            @Override
            protected List<PrincipalViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.findByDepartmentAndYear(department, Integer.parseInt(year));
                List<PrincipalViewAllRoutineNodeController> collect = nodes.stream().map(PrincipalViewAllRoutineNodeController::new).collect(Collectors.toList());
                return collect;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            }
            LoadingController.hide();
        });
        SystemUtils.getService().execute(task);
    }

    private void filter(ActionEvent evt) {
        Task<List<PrincipalViewAllRoutineNodeController>> task = new Task<List<PrincipalViewAllRoutineNodeController>>() {
            @Override
            protected List<PrincipalViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.findByDepartmentAndYear(department, Integer.parseInt(year));
                nodes = nodes.stream().filter(f -> f.getDate().equals(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date.getValue()))).collect(Collectors.toList());
                List<PrincipalViewAllRoutineNodeController> collect = nodes.stream().map(PrincipalViewAllRoutineNodeController::new).collect(Collectors.toList());
                return collect;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            }
            LoadingController.hide();
        });
        SystemUtils.getService().execute(task);
    }

    private void sortAscending(ActionEvent evt) {
        Task<List<PrincipalViewAllRoutineNodeController>> task = new Task<List<PrincipalViewAllRoutineNodeController>>() {
            @Override
            protected List<PrincipalViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.sortByDepartment(department, "asc");
                nodes = nodes.stream().filter(p -> p.getDate().endsWith(year)).collect(Collectors.toList());
                List<PrincipalViewAllRoutineNodeController> collect = nodes.stream().map(PrincipalViewAllRoutineNodeController::new).collect(Collectors.toList());
                list.getChildren().setAll(collect);
                return collect;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            }
            LoadingController.hide();
        });
        SystemUtils.getService().execute(task);
    }

    private void sortDescending(ActionEvent evt) {
        Task<List<PrincipalViewAllRoutineNodeController>> task = new Task<List<PrincipalViewAllRoutineNodeController>>() {
            @Override
            protected List<PrincipalViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.sortByDepartment(department, "desc");
                nodes = nodes.stream().filter(p -> p.getDate().endsWith(year)).collect(Collectors.toList());
                List<PrincipalViewAllRoutineNodeController> collect = nodes.stream().map(PrincipalViewAllRoutineNodeController::new).collect(Collectors.toList());
                return collect;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnSucceeded(e -> {
            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            }
            LoadingController.hide();
        });
        SystemUtils.getService().execute(task);
    }
}
