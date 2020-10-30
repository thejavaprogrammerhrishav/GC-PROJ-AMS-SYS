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
import com.sun.javafx.application.PlatformImpl;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author Programmer Hrishav
 */
public class SplashController extends VBox {

    @FXML
    private Label t1;

    @FXML
    private Label t2;

    @FXML
    private Label t3;
    
    @FXML
    private ImageView image;

    private FXMLLoader fxml;
    private FadeTransition tt1;
    private FadeTransition tt2;
    private static FadeTransition tt3;
    private FadeTransition tt;

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
        t1.setOpacity(0.0);
        t2.setOpacity(0.0);
        t3.setOpacity(0.0);
        image.setOpacity(0.0);
        
        tt3=new FadeTransition(Duration.seconds(1));
        tt3.setNode(t3);
        tt3.setFromValue(0);
        tt3.setToValue(1);        
        
        tt2=new FadeTransition(Duration.seconds(2));
        tt2.setNode(t2);
        tt2.setFromValue(0);
        tt2.setToValue(1);
        tt2.setOnFinished(e->{
            tt3.play();
        });
        
        
        tt1=new FadeTransition(Duration.seconds(2));
        tt1.setNode(t1);
        tt1.setFromValue(0);
        tt1.setToValue(1);
        tt1.setOnFinished(e->{
            tt2.play();
        });
        
        tt=new FadeTransition(Duration.seconds(2));
        tt.setNode(image);
        tt.setFromValue(0);
        tt.setToValue(1);
        tt.setOnFinished(e->{
            tt1.play();
        });
        
        CompletableFuture.runAsync(()->{
            try {
                Thread.sleep(500);
                Platform.runLater(()->tt.play());
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });
        
    }
}
