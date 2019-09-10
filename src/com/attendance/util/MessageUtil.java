/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author Programmer Hrishav
 */
public class MessageUtil {
    public static void showInformation(int type,String header,String content, Window owner){
        Stage st=new Stage(StageStyle.UNDECORATED);
        st.initOwner(owner);
        st.initModality(Modality.WINDOW_MODAL);
        st.setScene(new Scene(RootFactory.getMessageRoot(type, header, content)));
        st.centerOnScreen();
        st.setFullScreen(false);
        st.setFullScreenExitHint("");
        st.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        st.show();
    }
    
    public static void showError(int type,String header,String content, Window owner){
        Stage st=new Stage(StageStyle.UNDECORATED);
        st.initOwner(owner);
        st.initModality(Modality.WINDOW_MODAL);
        st.setScene(new Scene(RootFactory.getMessageRoot(type, header, content)));
        st.centerOnScreen();
        st.setFullScreen(false);
        st.setFullScreenExitHint("");
        st.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        st.show();
    }
    
    public static void showError(int type,String header,Exception content,Window owner){
        Stage st=new Stage(StageStyle.UNDECORATED);
        st.initOwner(owner);
        st.initModality(Modality.WINDOW_MODAL);
        st.setScene(new Scene(RootFactory.getMessageRoot(type, header, content)));
        st.centerOnScreen();
        st.setFullScreen(false);
        st.setFullScreenExitHint("");
        st.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        st.show();
    }
    
}
