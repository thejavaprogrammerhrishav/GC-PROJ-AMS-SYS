/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.LcdFont;
import javafx.scene.paint.Color;

/**
 *
 * @author Programmer Hrishav
 */
public class ClockUtil {
    
    public static Clock getAnalogClock(){
        return ClockBuilder.create().running(true).textVisible(true).skinType(Clock.ClockSkinType.CLOCK).dateVisible(true)
                .secondsVisible(true).hourColor(Color.web("#1c3de7")).minuteColor(Color.web("#1c3de7")).secondColor(Color.web("#1c3de7"))
                .hourTickMarkColor(Color.web("#1c3de7")).minuteTickMarkColor(Color.web("#1c3de7")).hourTickMarksVisible(true)
                .minuteTickMarksVisible(true).knobColor(Color.web("#1c3de7")).dateColor(Color.web("#1c3de7")).textColor(Color.web("#1c3de7"))
                .build();
    }
    
    public static Clock getDigitalClock(){
        return ClockBuilder.create().running(true).textVisible(true).skinType(Clock.ClockSkinType.LCD).
                lcdDesign(LcdDesign.BLUE_LIGHTBLUE).lcdFont(LcdFont.LCD).secondsVisible(true).dateVisible(true).build();
    }
}
