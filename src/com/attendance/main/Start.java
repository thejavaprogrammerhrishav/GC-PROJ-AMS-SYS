/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.main;

import com.attendance.config.SysConfig;
import com.attendance.settings.DatabaseServerSettingsController;
import com.attendance.splash.SplashController;
import com.attendance.util.RootFactory;
import com.attendance.util.StageUtil;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import com.sun.javafx.application.PlatformImpl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCombination;
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
    private static Task<Void> task;

    public static void main(String[] args) throws Exception {
        //initApp();
        SysConfig.load();
        sysload();
        launch(args);

    }

    public static void sysload() {
        System.setProperty("driver", SysConfig.get("sys.driver"));
        System.setProperty("host", SysConfig.get("sys.host"));
        System.setProperty("port", SysConfig.get("sys.port"));
        System.setProperty("dbname", SysConfig.get("sys.database.name"));
        System.setProperty("dialect", SysConfig.get("sys.database.dialect"));
        System.setProperty("url", SysConfig.get("sys.url"));
        System.setProperty("username", SysConfig.get("sys.username"));
        System.setProperty("password", SysConfig.get("sys.password"));
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
        st = StageUtil.initStage(primaryStage).fullScreen(false).fullScreenExitHint("").fullScreenExitKeyCombination(KeyCombination.NO_MATCH)
                .initStyle(StageStyle.UNDECORATED).centerOnScreen().build();        

        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                initContext();
                SystemUtils.init();
                return null;
            }
        };
        
        task.setOnFailed(e->{
            SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
        });
        
        task.setOnRunning(e->{
            PlatformImpl.runAndWait(()->{
                SwitchRoot.switchRoot(st, RootFactory.getSplashRoot());
                st.show();
            });
        });

        task.setOnSucceeded(e->{
            
            SplashController.close();
        });
        
        new Thread(task).start();
    }

    public static void initContext() throws Exception{
        app = new ClassPathXmlApplicationContext();
        app.setConfigLocation("/com/attendance/xml/Application.xml");
        if(SysConfig.validate()){
            throw  new Exception("Invalid Config");
        }
        app.refresh();
        
    }
}
