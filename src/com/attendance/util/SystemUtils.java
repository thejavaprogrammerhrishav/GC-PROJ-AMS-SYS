/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.login.activity.dao.Activity;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.user.model.User;
import com.attendance.main.Start;
import com.attendance.notes.controller.NotesNodeController;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
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

    private static User currentUser;

    private static byte[] accountImage;

    private static final HashMap<String, Image> ICONS = new HashMap<>();

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
        act = (Activity) Start.app.getBean("loginactivity");
        deptcodes = new HashMap<>();
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

        try {
            BufferedInputStream bin = (BufferedInputStream) SystemUtils.class.getResourceAsStream("/com/attendance/resources/account.png");
            accountImage = new byte[bin.available()];
            bin.read(accountImage);
            bin.close();
        } catch (IOException e) {
            System.out.println("Error Loading Default Account Image");
        }
        
        ICONS.put("file", new Image(SystemUtils.class.getResourceAsStream("/com/attendance/resources/file.png")));
        ICONS.put("image", new Image(SystemUtils.class.getResourceAsStream("/com/attendance/resources/image.png")));
        ICONS.put("pdf", new Image(SystemUtils.class.getResourceAsStream("/com/attendance/resources/pdf.png")));
        ICONS.put("doc", new Image(SystemUtils.class.getResourceAsStream("/com/attendance/resources/doc.png")));
        ICONS.put("noroutine",new Image(SystemUtils.class.getResourceAsStream("/com/attendance/resources/noroutinefound.png")));
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

    public static void logout() {
        if (activity != null) {
            activity.setStatus("NOT ACTIVE");
            activity.setLogouttime(DateTime.now().toString(DateTimeFormat.forPattern("hh:mm:ss a")));
            act.update(activity);
        }
    }

    public static void setCurrentUser(User currentUser) {
        SystemUtils.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static byte[] getDefaultAccountIcon() {
        return accountImage;
    }

    public static HashMap<String, Image> getICONS() {
        return ICONS;
    }

    public static byte[] getByteArrayFromImage(Image img) {
        BufferedImage image = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bout);
        } catch (IOException ex) {
            Logger.getLogger(SystemUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bout.toByteArray();
    }

}
