/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Programmer Hrishav
 */
public class Excel {

    public static Workbook createWorkBook(String format) {
        if (format.equals("xls")) {
            return new HSSFWorkbook();
        } else if (format.contains("xlsx")) {
            return new XSSFWorkbook();
        } else {
            return null;
        }
    }

    public static Font getHeaderFont(Workbook wb) {
        Font top = wb.createFont();
        top.setFontHeightInPoints((short) 15D);
        top.setFontName("Calibri");
        top.setBold(true);
        return top;
    }

    public static Font getBodyFont(Workbook wb) {
        Font bottom = wb.createFont();
        bottom.setFontHeightInPoints((short) 11D);
        bottom.setFontName("Calibri");
        return bottom;
    }

    public static CellStyle getHeaderCellStyle(Workbook wb, Font font) {
        CellStyle head = wb.createCellStyle();
        head.setFont(font);
        head.setAlignment(HorizontalAlignment.CENTER);
        head.setVerticalAlignment(VerticalAlignment.CENTER);
        return head;
    }

    public static CellStyle getBodyCellStyle(Workbook wb, Font font) {
        CellStyle body = wb.createCellStyle();
        body.setFont(font);
        body.setAlignment(HorizontalAlignment.CENTER);
        body.setVerticalAlignment(VerticalAlignment.CENTER);
        return body;
    }

    public static Row createRow(Sheet sheet, int count, int height) {
        Row r = sheet.createRow(count);
        r.setHeightInPoints(height);
        return r;
    }

    public static Cell createCell(Row r, int count, String value, CellStyle style) {
        Cell c = r.createCell(count);
        c.setCellStyle(style);
        c.setCellValue(value);
        return c;
    }
}
