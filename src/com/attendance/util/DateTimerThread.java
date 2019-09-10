/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Programmer Hrishav
 */
public class DateTimerThread {

    public static final String DATE = "dateType";
    public static final String TIME = "timeType";

    private Task<Void> timerTask;
    private Task<Void> dateTask;

    private Label date;
    private Label time;

    private String type;

    private DateTimerThread() {
        date = new Label();
        time = new Label();
    }

    public static DateTimerThread newInstance() {
        return new DateTimerThread();
    }

    public DateTimerThread forLabel(String type, Label label) {
        this.type = type;
        if (type.equals(DATE)) {
            this.date = label;
        } else if (type.equals(TIME)) {
            this.time = label;
        } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setHeaderText("Error on date and time");
            al.setContentText("Type doesnot match");
            al.show();
        }
        return this;
    }

    public DateTimerThread init() {
        timerTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    DateTimeFormatter format = DateTimeFormat.forPattern("hh : mm: ss a");
                    Platform.runLater(new TimerSetter(DateTime.now().toString(format), time));
                    Thread.sleep(1000);
                }
            }
        };
        dateTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    DateTimeFormatter format = DateTimeFormat.forPattern("EEEE, dd MMMMM yyyy");
                    Platform.runLater(new DateSetter(DateTime.now().toString(format),date));
                    Thread.sleep(1000);
                }
            }
        };
        return this;
    }

    public Thread thread() {
        if (type.equals(DATE)) {
            return new Thread(dateTask);
        } else if (type.equals(TIME)) {
            return new Thread(timerTask);
        } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setHeaderText("Error on date and time");
            al.setContentText("Type doesnot match");
            al.show();
            return null;
        }
    }
    
    private class TimerSetter implements Runnable{
        private final String time;
        private final Label timer;

        public TimerSetter(String time, Label timer) {
            this.time = time;
            this.timer=timer;
        }

        @Override
        public void run() {
            timer.setText(time);
        }        
    }

    private class DateSetter implements Runnable{
        private final String date;
        private final Label dater;

        public DateSetter(String date, Label dater) {
            this.date = date;
            this.dater=dater;
        }

        @Override
        public void run() {
            dater.setText(date);
        }        
    }
}
