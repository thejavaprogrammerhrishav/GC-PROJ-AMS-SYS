/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.main;

import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Programmer Hrishav
 */
public class Start extends Application {

    public static Stage st;
    public static ClassPathXmlApplicationContext app;

    public static void main(String[] args) throws Exception {
        //initApp();

        initContext();
        SystemUtils.init();
        launch(args);

    }

    private static void initApp() throws FileNotFoundException, IOException {
        FileOutputStream fout = new FileOutputStream("output.txt", true);
        fout.write(new Date().toString().getBytes());
        fout.write("\n=========================================================================".getBytes());
        PrintStream p = new PrintStream(fout, true);
        System.setOut(p);
        System.setErr(p);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        st = StageUtil.initStage(primaryStage).fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(null)
                .initStyle(StageStyle.UNDECORATED).centerOnScreen().build();
        SwitchRoot.switchRoot(st, RootFactory.getSplashRoot());
        //SwitchRoot.switchRoot(st, RootFactory.getUserType1Root());
        
        st.show();
    }

    public static void initContext() {
        app = new ClassPathXmlApplicationContext();
        app.setConfigLocation("/com/attendance/xml/Application.xml");
        app.refresh();
    }
}
