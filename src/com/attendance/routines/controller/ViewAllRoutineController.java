/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.main.Start;
import com.attendance.routine.service.RoutineService;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.settings.sub.LoadingController;
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
public class ViewAllRoutineController extends AnchorPane {

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

    public ViewAllRoutineController() {
        fxml = Fxml.getViewAllRoutineFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        dao = (RoutineService) Start.app.getBean("routineservice");
        dao.setParent(this);

        date.disableProperty().bind(filterbydate.selectedProperty().not());

        loadData(null);

        refresh.setOnAction(this::loadData);
        apply.setOnAction(this::filter);
        asc.setOnAction(this::sortAscending);
        desc.setOnAction(this::sortDescending);
    }

    private void loadData(ActionEvent evt) {
        Task<List<ViewAllRoutineNodeController>> task = new Task<List<ViewAllRoutineNodeController>>() {
            @Override
            protected List<ViewAllRoutineNodeController> call() throws Exception {

                List<Routine> nodes = dao.findByDepartment(SystemUtils.getCurrentUser().getDepartment());
                List<ViewAllRoutineNodeController> collect = nodes.stream().map(ViewAllRoutineNodeController::new).collect(Collectors.toList());
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
        Task<List<ViewAllRoutineNodeController>> task = new Task<List<ViewAllRoutineNodeController>>() {
            @Override
            protected List<ViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.findByDepartment(SystemUtils.getCurrentUser().getDepartment());
                nodes = nodes.stream().filter(f -> f.getDate().equals(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date.getValue()))).collect(Collectors.toList());
                List<ViewAllRoutineNodeController> collect = nodes.stream().map(ViewAllRoutineNodeController::new).collect(Collectors.toList());
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
        Task<List<ViewAllRoutineNodeController>> task = new Task<List<ViewAllRoutineNodeController>>() {
            @Override
            protected List<ViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.sortByDepartment(SystemUtils.getCurrentUser().getDepartment(), "asc");
                List<ViewAllRoutineNodeController> collect = nodes.stream().map(ViewAllRoutineNodeController::new).collect(Collectors.toList());
                System.out.println(nodes.size() + "    " + collect.size());
                return collect;
            }
        };
        task.setOnRunning(e -> LoadingController.show(this.getScene()));
        task.setOnFailed(e -> LoadingController.hide());
        task.setOnSucceeded(e -> {
            LoadingController.hide();

            try {
                list.getChildren().clear();
                Thread.sleep(700);
                list.getChildren().setAll(task.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ViewAllRoutineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        SystemUtils.getService().execute(task);
    }

    private void sortDescending(ActionEvent evt) {
        Task<List<ViewAllRoutineNodeController>> task = new Task<List<ViewAllRoutineNodeController>>() {
            @Override
            protected List<ViewAllRoutineNodeController> call() throws Exception {
                List<Routine> nodes = dao.sortByDepartment(SystemUtils.getCurrentUser().getDepartment(), "desc");
                List<ViewAllRoutineNodeController> collect = nodes.stream().map(ViewAllRoutineNodeController::new).collect(Collectors.toList());
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
