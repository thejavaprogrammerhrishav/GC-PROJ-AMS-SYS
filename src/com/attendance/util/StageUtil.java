/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Programmer Hrishav
 */
public class StageUtil {

    private static Stage st;

    private StageUtil() {
        st=new Stage();
    }
    
    private StageUtil(Stage st){
        this.st=st;
    }

    public static StageUtil newStage() {
        return new StageUtil();
    }
    
    public static StageUtil initStage(Stage st){
        return new StageUtil(st);
    }

    public StageUtil initModality(Modality modality) {
        st.initModality(modality);
        return this;
    }

    public StageUtil initOwner(Window window) {
        st.initOwner(window);
        return this;
    }

    public StageUtil initStyle(StageStyle style) {
        st.initStyle(style);
        return this;
    }

    public StageUtil centerOnScreen() {
        st.centerOnScreen();
        return this;
    }

    public StageUtil fullScreen(boolean full) {
        st.setFullScreen(full);
        return this;
    }

    public StageUtil fullScreenExitHint(String hint) {
        st.setFullScreenExitHint(hint);
        return this;
    }

    public StageUtil fullScreenExitKeyCombination(KeyCombination kc) {
        st.setFullScreenExitKeyCombination(kc);
        return this;
    }

    public Stage build() {
        st.setScene(new Scene(new AnchorPane()));
        return st;
    }
}
