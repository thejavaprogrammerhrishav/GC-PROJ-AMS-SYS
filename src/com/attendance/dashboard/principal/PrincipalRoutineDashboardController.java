/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard.principal;

import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.personal.model.PersonalDetails;
import com.attendance.routine.service.RoutineService;
import com.attendance.routines.controller.ViewActiveRoutineController;
import com.attendance.routines.controller.ViewAllRoutineController;
import com.attendance.routines.dao.RoutineDao;
import com.attendance.routines.model.Routine;
import com.attendance.settings.sub.LoadingController;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author pc
 */
public class PrincipalRoutineDashboardController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private Label usertype;

    @FXML
    private Label department;

    @FXML
    private Label date;

    @FXML
    private Label contact;

    @FXML
    private JFXButton addnewroutine;

    @FXML
    private JFXButton updateroutine;

    @FXML
    private JFXButton viewroutine;

    @FXML
    private JFXButton viewallroutine;

    @FXML
    private AnchorPane list;

    @FXML
    private Label name;

    @FXML
    private ImageView image;

    private FXMLLoader fxml;

    private Parent parent;

    private User currentUser;

    private RoutineService rdao;
    private ExceptionDialog dialog;

    private String currentdepartment;
    private String year;

    public PrincipalRoutineDashboardController(Parent parent, String currentdepartment, String year) {
        this.parent = parent;
        this.currentdepartment = currentdepartment;
        this.year = year;
        fxml = Fxml.getRoutineDashboardFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalRoutineDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        rdao = (RoutineService) Start.app.getBean("routineservice");
        rdao.setParent(this);
        dialog = rdao.getEx();
        
        currentUser = SystemUtils.getCurrentUser();
        PersonalDetails personalDetails = currentUser.getDetails();

        image.setImage(new Image(new ByteArrayInputStream(currentUser.getImage())));
        name.setText(personalDetails.getName());
        contact.setText(personalDetails.getContact());
        department.setText(currentdepartment);
        usertype.setText(currentUser.getUsername());
        date.setText(currentUser.getDate());

        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));

        addnewroutine.setDisable(true);
        updateroutine.setDisable(true);
        viewroutine.setOnAction(this::viewactiveRoutine);
        viewallroutine.setOnAction(this::viewallRoutine);
    }

    private void viewactiveRoutine(ActionEvent evt) {
        Task<List<ViewActiveRoutineController>> task = new Task<List<ViewActiveRoutineController>>() {
            @Override
            protected List<ViewActiveRoutineController> call() throws Exception {
                if (rdao.hasActiveRoutine(currentdepartment, Integer.parseInt(year)) == 1) {
                    Routine r = rdao.findByDepartmentAndDateAndStatus(currentdepartment, year, "Active");
                    return Arrays.asList(new ViewActiveRoutineController(r));
                } else {
                    Routine rs = new Routine();
                    byte[] nor = SystemUtils.getByteArrayFromImage(SystemUtils.getICONS().get("noroutine"));
                    rs.setImage(nor);
                    return Arrays.asList(new ViewActiveRoutineController(rs));
                }
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

    private void viewallRoutine(ActionEvent evt) {
        list.getChildren().setAll(Arrays.asList(new PrincipalViewAllRoutineController(currentdepartment, year)));
    }
}
