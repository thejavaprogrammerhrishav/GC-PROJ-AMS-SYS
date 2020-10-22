/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

/**
 *
 * @author Programmer Hrishav
 */
public class Crypter {

    public static final String path = "db.sys.config";

    public boolean exists() {
        return new File(path).exists();
    }

    public byte[] read(String path) throws IOException {
        FileInputStream fin = new FileInputStream(path);
        byte[] data = new byte[fin.available()];
        fin.read(data);
        fin.close();
        return data;
    }

    public void write(byte[] data) throws IOException {
        FileOutputStream fout = new FileOutputStream(path);
        fout.write(data);
        fout.flush();
        fout.close();
    }

    public Properties getData() {
        try {
            byte[] data = read(path);
            byte[] decode = Base64.getDecoder().decode(data);

            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(decode));

            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setData(Properties properties) {
        try {
            properties.store(new FileOutputStream("test.prop"), "");

            byte[] data = read("test.prop");
            System.out.println(data);

            byte[] encode = Base64.getEncoder().encode(data);

            write(encode);

            File file = new File("test.prop");
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
