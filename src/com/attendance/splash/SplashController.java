/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.splash;

import com.attendance.main.Start;
import com.jfoenix.controls.JFXProgressBar;
import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Programmer Hrishav
 */
public class SplashController extends AnchorPane {

    @FXML
    private JFXProgressBar progress;

    private FXMLLoader fxml;
    private Task<Integer> worker;
    private Thread thread;

    public SplashController() {
        fxml = Fxml.getSplashFXML();
        fxml.setRoot(SplashController.this);
        fxml.setController(SplashController.this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void initialize() {
        worker = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                Thread.sleep(3000);
                updateProgress(0, 1);
                Thread.sleep(1689);
                updateProgress(0.2, 1);
                Thread.sleep(1851);
                updateProgress(0.5, 1);
                Thread.sleep(2058);
                updateProgress(0.6, 1);
                Thread.sleep(1000);
                updateProgress(0.7, 1);
                Thread.sleep(1200);
                updateProgress(0.8, 1);
                Thread.sleep(1200);
                updateProgress(0.9, 1);
                Thread.sleep(1400);
                updateProgress(1, 1);
                Thread.sleep(1300);
                return 0;
            }
        };
        worker.setOnSucceeded(e -> SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root()));
        progress.progressProperty().bind(worker.progressProperty());
        thread = new Thread(worker);
        thread.start();
    }

}
