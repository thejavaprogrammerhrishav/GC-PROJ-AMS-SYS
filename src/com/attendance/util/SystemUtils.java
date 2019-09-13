/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.main.Start;
import java.util.HashMap;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author Programmer Hrishav
 */
public class SystemUtils {

    private static Activity act;
    private static LoginActivity activity;
    private static String department;
    private static String[] departments = {"Anthropology", "Assamese", "Bengali", "Biotechnology", "Botany", "Business Administration", "Chemistry", "Commerce",
        "Computer Science", "Ecology & Environmental Science", "Economics", "English", "Geology", "Hindi", "History", "Mathematics", "Manipuri", "Mass Communication",
        "Persian", "Philosophy", "Physics", "Political Science", "Sanskrit", "Statistics", "Zoology"};

    private static HashMap<String, String> deptcodes;

    public static String getDepartment() {
        return department;
    }

    public static void setDepartment(String department) {
        SystemUtils.department = department;
    }

    public static String[] getDepartments() {
        return departments;
    }

    public static String getDepartmentCode() {
        return deptcodes.get(department);
    }

    public static void init() {
        act=(Activity) Start.app.getBean("loginactivity");
        deptcodes=new HashMap<>();
        deptcodes.put("Anthropology", "ANTH");
        deptcodes.put("Assamese", "ASSM");
        deptcodes.put("Bengali", "BENG");
        deptcodes.put("Biotechnology", "BIO");
        deptcodes.put("Botany", "BOT");
        deptcodes.put("Business Administration", "BBA");
        deptcodes.put("Chemistry", "CHEM");
        deptcodes.put("Commerce", "COM");
        deptcodes.put("Computer Science", "CS");
        deptcodes.put("Ecology & Environmental Science", "EVS");
        deptcodes.put("Economics", "ECO");
        deptcodes.put("English", "ENG");
        deptcodes.put("Geology", "GEO");
        deptcodes.put("Hindi", "HIN");
        deptcodes.put("History", "HIST");
        deptcodes.put("Mathematics", "MATH");
        deptcodes.put("Manipuri", "MANP");
        deptcodes.put("Mass Communication", "MAC");
        deptcodes.put("Persian", "PERS");
        deptcodes.put("Philosophy", "PHIL");
        deptcodes.put("Physics", "PHY");
        deptcodes.put("Political Science", "POLSC");
        deptcodes.put("Sanskrit", "SANS");
        deptcodes.put("Statistics", "STATS");
        deptcodes.put("Zoology", "ZOO");

    }

    public static void setActivity(LoginActivity activity) {
        SystemUtils.activity = activity;
    }

    public static LoginActivity getActivity() {
        return activity;
    }

    public static HashMap<String, String> getDeptcodes() {
        return deptcodes;
    }
    
    public static void logout(){
        if (activity != null) {
            activity.setStatus("NOT ACTIVE");
        activity.setLogouttime(DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")));
        act.update(activity);
        }
    }
    
}
