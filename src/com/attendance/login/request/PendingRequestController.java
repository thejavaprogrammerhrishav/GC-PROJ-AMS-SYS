/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.login.request;

import com.attendance.login.dao.Login;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.util.Fxml;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author pc
 */
public class PendingRequestController extends AnchorPane {

    @FXML
    private JFXButton today;

    @FXML
    private JFXButton thismonth;

    @FXML
    private JFXButton older;

    @FXML
    private JFXButton pending;

    @FXML
    private JFXButton accepted;

    @FXML
    private JFXButton onhold;

    @FXML
    private JFXButton decline;

    @FXML
    private JFXButton close;

    @FXML
    private TextField searchhere;

    @FXML
    private JFXButton search;

    @FXML
    private Label requesttype;

    @FXML
    private Label department;

    @FXML
    private Label totalpendingrequest;

    @FXML
    private Label usertype;

    @FXML
    private VBox list;

    private FXMLLoader fxml;
    private Parent parent;
    private String sdepartment;

    private Login login;

    private String reqtype;

    private Thread thread;
    private Runnable main, run;
    
    private boolean isPrincipal;

    public PendingRequestController(Parent parent, String sdepartment,boolean isPrincipal) {

        this.parent = parent;
        this.sdepartment = sdepartment;
        this.isPrincipal = isPrincipal;
        fxml = Fxml.getPendingRequestFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(PendingRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        run = () -> {
            int count = login.count("select count(*) from loginuser where status = 'Pending' and type = '" + reqtype + "' and department = '" + sdepartment + "'");
            totalpendingrequest.setText("" + count);
        };

        main = () -> {
            while (true) {
                Platform.runLater(run);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PendingRequestController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        login = (Login) Start.app.getBean("userlogin");
        department.setText(sdepartment);
        usertype.setText(SystemUtils.getCurrentUser().getType());
        switch (SystemUtils.getCurrentUser().getType()) {
            case "Principal":
                reqtype = "HOD";
                break;
            case "HOD":
                reqtype = "Faculty";
                break;
            default:
                reqtype = "";
        }
        if(isPrincipal) {
            reqtype = "Principal";
        }
        requesttype.setText(reqtype);

        thread = new Thread(main);
        thread.start();
        pending(null);
        buttonActions();
    }

    private void buttonActions() {
        close.setOnAction(e -> SwitchRoot.switchRoot(Start.st, parent));
        pending.setOnAction(this::pending);
        accepted.setOnAction(this::accept);
        decline.setOnAction(this::decline);
        onhold.setOnAction(this::onhold);
        search.setOnAction(this::search);

        today.setOnAction(this::today);
        thismonth.setOnAction(this::month);
        older.setOnAction(this::older);
    }

    private void pending(ActionEvent evt) {
        List<User> users = login.findByStatusAndDepartment("Pending", sdepartment).stream().filter(p -> p.getType().equals(reqtype)).collect(Collectors.toList());
        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            private int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void accept(ActionEvent evt) {
        List<User> users = login.findByStatusAndDepartment("Accept", sdepartment).stream().filter(p -> p.getType().equals(reqtype)).collect(Collectors.toList());
        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void onhold(ActionEvent evt) {
        List<User> users = login.findByStatusAndDepartment("OnHold", sdepartment).stream().filter(p -> p.getType().equals(reqtype)).collect(Collectors.toList());
        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void decline(ActionEvent evt) {
        List<User> users = login.findByStatusAndDepartment("Decline", sdepartment).stream().filter(p -> p.getType().equals(reqtype)).collect(Collectors.toList());
        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void search(ActionEvent evt) {
        ObservableList<Node> children = list.getChildren();
        List<PendingRequestNodeController> items = children.stream().map(m -> (PendingRequestNodeController) m).collect(Collectors.toList());
        List<PendingRequestNodeController> collected = items.stream().filter(f -> f.getName().contains(searchhere.getText())).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void today(ActionEvent evt) {
        List<User> users = login.findByDepartment(sdepartment).stream().filter(p -> p.getType().equals(reqtype) && p.getDate().equals(DateTime.now().toString(DateTimeFormat.forPattern("dd-MM-yyyy")))).collect(Collectors.toList());
        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void month(ActionEvent evt) {
        List<User> users = login.findByDepartment(sdepartment).stream().filter(p -> p.getType().equals(reqtype)).collect(Collectors.toList());
        users = users.stream().filter(p -> {
            DateTime dt = DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(p.getDate());
            return DateTime.now().toString(DateTimeFormat.forPattern("MM-yyyy")).equals(dt.toString(DateTimeFormat.forPattern("MM-yyyy")));
        }).collect(Collectors.toList());

        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            private int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

    private void older(ActionEvent evt) {
         List<User> users = login.findByDepartment(sdepartment).stream().filter(p -> p.getType().equals(reqtype)).collect(Collectors.toList());
        users = users.stream().filter(p -> {
            DateTime dt = DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(p.getDate());
            DateTime ndt=DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime("01-"+DateTime.now().toString(DateTimeFormat.forPattern("MM-yyyy")));
            return dt.isBefore(ndt);
        }).collect(Collectors.toList());

        List<PendingRequestNodeController> collected = users.stream().map(new Function<User, PendingRequestNodeController>() {
            private int i = 1;

            @Override
            public PendingRequestNodeController apply(User t) {

                return new PendingRequestNodeController(i++, t);
            }
        }).collect(Collectors.toList());
        list.getChildren().setAll(collected);
    }

}
