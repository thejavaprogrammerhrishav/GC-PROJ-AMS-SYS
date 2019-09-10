/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

/**
 *
 * @author Programmer Hrishav
 */
public class SystemUtils {

    private static String department;
    private static String[] departments = {"Anthropology", "Assamese", "Bengali", "Biotechnology", "Botany", "Business Administration", "Chemistry", "Commerce",
        "Computer Science", "Ecology & Environmental Science", "Economics", "English", "Geology", "Hindi", "History", "Mathematics", "Manipuri", "Mass Communication",
        "Persian", "Philosophy", "Physics", "Political Science", "Sanskrit", "Statistics", "Zoology"};

    public static String getDepartment() {
        return department;
    }

    public static void setDepartment(String department) {
        SystemUtils.department = department;
    }

    public static String[] getDepartments() {
        return departments;
    }
}
