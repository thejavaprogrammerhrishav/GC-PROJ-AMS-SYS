/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Programmer Hrishav
 */
public class SysConfig {

    private static Crypter crypt = new Crypter();

    private static Properties prop = new Properties();

    public static void load() {
        if (!crypt.exists()) {
            try {
                new File(Crypter.path).createNewFile();
                initial();
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            prop = crypt.getData();
        }
    }

    public static void set(String key, String value) {
        prop.setProperty(key, value);
    }

    public static String get(String key) {
        return (String) prop.getOrDefault(key, "");
    }

    public static void save() {
        crypt.setData(prop);
        prop = crypt.getData();
    }

    private static void initial() {
        prop.setProperty("sys.driver", "");
        prop.setProperty("sys.host", "");
        prop.setProperty("sys.port", "");
        prop.setProperty("sys.database.name", "");
        prop.setProperty("sys.database.dialect", "");
        prop.setProperty("sys.url", "");
        prop.setProperty("sys.username", "");
        prop.setProperty("sys.password", "");
    }

    public static Crypter crypt() {
        return crypt;
    }

}
