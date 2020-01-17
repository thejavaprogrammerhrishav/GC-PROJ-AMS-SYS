/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.settings.sub;

import com.attendance.util.Fxml;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Programmer Hrishav
 */
public class LoadingController extends AnchorPane {

    @FXML
    private Label loading;

    private FXMLLoader fxml;

    private static Thread thread;
    private static Stage st;

    public LoadingController() {
        fxml = Fxml.getLoadingFXML();
        fxml.setController(this);
        fxml.setRoot(this);
        try {
            fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(LoadingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize() {
        String l1 = "Loading";
        String l2 = "Loading .";
        String l3 = "Loading . .";
        String l4 = "Loading . . .";
        Runnable r = () -> {
            int x = 0;
            while (true) {
                try {
                    switch (x) {
                        case 0:
                            Platform.runLater(() -> loading.setText(l1));
                            x = 1;
                            break;
                        case 1:
                            Platform.runLater(() -> loading.setText(l2));
                            x = 2;
                            break;
                        case 2:
                            Platform.runLater(() -> loading.setText(l3));
                            x = 3;
                            break;
                        case 3:
                            Platform.runLater(() -> loading.setText(l4));
                            x = 0;
                            break;
                    }
                    Thread.sleep(600);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoadingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        thread = new Thread(r);
        thread.start();
    }

    public static void show(Scene scene) {
        st = StageUtil.newStage().centerOnScreen().fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(KeyCombination.NO_MATCH)
                .initModality(Modality.WINDOW_MODAL).initOwner(scene.getWindow()).initStyle(StageStyle.TRANSPARENT).build();
        Scene sc = new Scene(RootFactory.getLoadingRoot());
        sc.setFill(Color.TRANSPARENT);
        st.setScene(sc);
        st.setOnCloseRequest(e -> thread.stop());
        st.show();
    }

    
    public static void hide() {
        st.close();
    }
}
