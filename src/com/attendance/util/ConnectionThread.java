/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Programmer Hrishav
 */
public class ConnectionThread extends Thread implements Runnable {

    private Properties prop;
    private Connection conn;

    private final Label serverName;
    private final Label serverUsername;
    private final Label serverStatus;

    private String url;
    private String username;
    private String password;

    public ConnectionThread(Label serverName, Label serverUsername, Label serverStatus) {
        this.serverName = serverName;
        this.serverUsername = serverUsername;
        this.serverStatus = serverStatus;
    }

    @Override
    public void run() {
        while (true) {
            try {
                initProps();
                conn = DriverManager.getConnection(url, username, password);

                if (conn == null) {
                    setVals("Unknown", "Unknown", "Not Connected");
                } else {
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("select 'hello' as C");
                    if (rs.next()) {
                        if (rs.getString("C").equals("hello")) {
                            DatabaseMetaData md = conn.getMetaData();
                            setVals(md.getDatabaseProductName(), md.getUserName().split("@")[0], "Connected");
                        } else {
                            setVals("Unknown", "Unknown", "Not Connected");
                        }
                    } else {
                        setVals("Unknown", "Unknown", "Not Connected");
                    }
                }
                Thread.sleep(1000);
            } catch (SQLException | IOException | InterruptedException ex) {
                setVals("Unknown", "Unknown", "Not Connected");
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void initProps() throws IOException {
        prop = new Properties();
        prop.load(new FileInputStream("db.properties"));
        url = prop.getProperty("db.url");
        username = prop.getProperty("db.username");
        password = prop.getProperty("db.password");
    }

    private void setVals(final String sname, final String susername, final String status) {
        Platform.runLater(() -> {
            serverName.setText(sname);
            serverUsername.setText(susername);
            serverStatus.setText(status);
        });
    }

}
