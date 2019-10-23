/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.routines.controller;

import com.attendance.routines.model.Routine;
import com.attendance.util.Fxml;
import com.attendance.util.StageUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.management.Query.value;

/**
 *
 * @author pc
 */
public class ViewRoutineController extends AnchorPane {

    @FXML
    private JFXButton close;

    @FXML
    private JFXSlider zoom;

    @FXML
    private ImageView image;

    @FXML
    private ScrollPane scroll;

    private FXMLLoader fxml;

    private Routine routine;

    private Image source;

    private double initx;
    private double inity;
    private int height;
    private int width;
    private double offSetX, offSetY, zoomlvl;

    private ViewRoutineController(Routine routine) {
        this.routine = routine;
        fxml = Fxml.getViewRoutineFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewRoutineController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void initialize() {
        zoom.setMin(1.0);
        zoom.setMax(4.0);
        zoom.setMinorTickCount(0);
        zoom.setValue(1.0);
        source = new Image(new ByteArrayInputStream(routine.getImage()));
        image.setImage(source);
        initView();
        close.setOnAction(e -> this.getScene().getWindow().hide());

    }

    public void initView() {

       
        image.setPreserveRatio(true);
        
        height = (int) source.getHeight();
        width = (int) source.getWidth();

        offSetX = width / 2;
        offSetY = height / 2;

        Slider Hscroll = new Slider();
        Hscroll.setMin(0);
        Hscroll.setMax(width);
        Hscroll.setMaxWidth(image.getFitWidth());
        Hscroll.setMinWidth(image.getFitWidth());
        Hscroll.setTranslateY(-20);
        Slider Vscroll = new Slider();
        Vscroll.setMin(0);
        Vscroll.setMax(height);
        Vscroll.setMaxHeight(image.getFitHeight());
        Vscroll.setMinHeight(image.getFitHeight());
        Vscroll.setOrientation(Orientation.VERTICAL);
        Vscroll.setTranslateX(-20);

        Hscroll.valueProperty().addListener(e -> {
            offSetX = Hscroll.getValue();
            zoomlvl = zoom.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            if (offSetX < (width / newValue) / 2) {
                offSetX = (width / newValue) / 2;
            }
            if (offSetX > width - ((width / newValue) / 2)) {
                offSetX = width - ((width / newValue) / 2);
            }

            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        Vscroll.valueProperty().addListener(e -> {
            offSetY = height - Vscroll.getValue();
            zoomlvl = zoom.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            if (offSetY < (height / newValue) / 2) {
                offSetY = (height / newValue) / 2;
            }
            if (offSetY > height - ((height / newValue) / 2)) {
                offSetY = height - ((height / newValue) / 2);
            }
            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        
        zoom.valueProperty().addListener(e -> {
            zoomlvl = zoom.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            if (offSetX < (width / newValue) / 2) {
                offSetX = (width / newValue) / 2;
            }
            if (offSetX > width - ((width / newValue) / 2)) {
                offSetX = width - ((width / newValue) / 2);
            }
            if (offSetY < (height / newValue) / 2) {
                offSetY = (height / newValue) / 2;
            }
            if (offSetY > height - ((height / newValue) / 2)) {
                offSetY = height - ((height / newValue) / 2);
            }
            Hscroll.setValue(offSetX);
            Vscroll.setValue(height - offSetY);
            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
//        imageView.setCursor(Cursor.OPEN_HAND);
        image.setOnMousePressed(e -> {
            initx = e.getSceneX();
            inity = e.getSceneY();
//            imageView.setCursor(Cursor.CLOSED_HAND);
        });
        image.setOnMouseReleased(e -> {
//            imageView.setCursor(Cursor.OPEN_HAND);
        });
        image.setOnMouseDragged(e -> {
            Hscroll.setValue(Hscroll.getValue() + (initx - e.getSceneX()));
            Vscroll.setValue(Vscroll.getValue() - (inity - e.getSceneY()));
            initx = e.getSceneX();
            inity = e.getSceneY();
        });

    }

    public static void show(Routine routine, Scene scene) {
        Stage st = StageUtil.newStage().centerOnScreen().fullScreen(true).fullScreenExitHint(null).fullScreenExitKeyCombination(KeyCombination.NO_MATCH)
                .initModality(Modality.WINDOW_MODAL).initOwner(scene.getWindow()).initStyle(StageStyle.UNDECORATED).build();

        st.setScene(new Scene(new ViewRoutineController(routine)));
        st.show();
    }

}
