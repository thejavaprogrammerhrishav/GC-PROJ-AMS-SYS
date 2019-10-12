/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.marks.controller.UTReportModel;
import com.attendance.studentattendance.model.ClassDetails;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Programmer Hrishav
 */
public class ExportUTReport {

    private final String[] columnNames = {"Roll Number" , "Marks Obtained" , "Passing Marks" , "Total Marks"};

    private String path;
    private FileOutputStream fout;
    private Workbook wb;
    private Sheet sheet;
    private CellStyle head;
    private CellStyle body;
    private Font top;
    private Font bottom;

    private Row r;
    private Cell c;

    private int RowCount;

    private TableView<UTReportModel> list;

    public ExportUTReport(TableView<UTReportModel> list) {
        this.list = list;
        RowCount = 0;
    }

    public ExportUTReport createFile() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Microsoft Excel Files [.xlsx]", "xlsx");
        FileChooser.ExtensionFilter filter2 = new FileChooser.ExtensionFilter("Microsoft Excel Files 97 - 2003 [.xls]", "xls");
        fc.getExtensionFilters().addAll(filter, filter2);
        fc.setSelectedExtensionFilter(filter);
        File f = fc.showSaveDialog(list.getScene().getWindow());
        if (f != null) {
            String desc = fc.getSelectedExtensionFilter().getDescription();
            if (desc.contains("[.xlsx]")) {
                path = f.getAbsolutePath() + ".xlsx";
            }
            if (desc.contains("[.xls]")) {
                path = f.getAbsolutePath() + ".xls";
            }
        }
        return this;
    }

    public ExportUTReport convertToExcel(String sheetName) {
        if (path.contains(".xls")) {
            wb = Excel.createWorkBook("xls");
        }
        if (path.contains(".xlsx")) {
            wb = Excel.createWorkBook("xlsx");
        }
        sheet = wb.createSheet(sheetName);
        top = Excel.getHeaderFont(wb);
        bottom = Excel.getBodyFont(wb);
        head = Excel.getHeaderCellStyle(wb, top);
        body = Excel.getBodyCellStyle(wb, bottom);
        r = Excel.createRow(sheet, RowCount++, 45);

        for (int i = 0; i < columnNames.length; i++) {
            c = Excel.createCell(r, i, columnNames[i], head);
        }

        list.getItems().stream().forEach(at -> {
            r = Excel.createRow(sheet, RowCount++, 25);
            c = Excel.createCell(r, 0, ""+at.getRollno(), body);
            c = Excel.createCell(r, 1, ""+at.getMarks(), body);
            c = Excel.createCell(r, 2, ""+at.getPassing(), body);
            c = Excel.createCell(r, 3, ""+at.getTotal(), body);

        });
        for (int i = 0; i < columnNames.length; i++) {
            sheet.autoSizeColumn(i);
        }
        return this;
    }

    public void exportToFile() throws FileNotFoundException, IOException {
        fout = new FileOutputStream(path);
        wb.write(fout);
        wb.close();
        fout.close();
        MessageUtil.showInformation(Message.INFORMATION, "Export Unit Test Report ", "Report Exported Successfully", list.getScene().getWindow());
    }
}
