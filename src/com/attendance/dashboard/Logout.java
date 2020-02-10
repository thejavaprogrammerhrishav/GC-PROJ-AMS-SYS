/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.dashboard;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.activity.service.LoginActivityService;
import com.attendance.main.Start;
import com.attendance.util.ExceptionDialog;
import com.attendance.util.RootFactory;
import com.attendance.util.SwitchRoot;
import com.attendance.util.SystemUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class Logout implements EventHandler<ActionEvent>{
    
    private final LoginActivity activity;
    private final LoginActivityService act;
    private ExceptionDialog dialog;

    public Logout(LoginActivity activity) {
        this.activity = activity;
        act=(LoginActivityService) Start.app.getBean("loginactivityservice");
    }
    
    @Override
    public void handle(ActionEvent event) {
        activity.setStatus("NOT ACTIVE");
        activity.setLogouttime(DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")));
        act.updateactivity(activity);
      
        SystemUtils.setDepartment("");
        SystemUtils.logout();
        SystemUtils.setActivity(null);
        SwitchRoot.switchRoot(Start.st, RootFactory.getUserType1Root());
        
    }
    
}
