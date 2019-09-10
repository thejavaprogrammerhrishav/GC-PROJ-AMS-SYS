/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.student.model.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
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
public class ExportDailyStats {
    
    private final String[] columnNames = {"Class Date", "Class Time", "Semester", "Year", "Total Student",
        "Total Present", "Total Absent","Present Percentage","Absent Percentage","Acadamic Year","Course Type"};

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

    private TableView<DailyStatsUtilModel> list;

    public ExportDailyStats(TableView<DailyStatsUtilModel> list) {
        this.list = list;
        RowCount = 0;
    }

    public ExportDailyStats createFile() {
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

    public ExportDailyStats convertToExcel(String sheetName) {
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
            c = Excel.createCell(r, 0, at.getDate(), body);
            c = Excel.createCell(r, 1, ""+at.getTime(), body);
            c = Excel.createCell(r, 2, ""+at.getSemester(), body);
            c = Excel.createCell(r, 3, ""+at.getYear(), body);
            c = Excel.createCell(r, 4, ""+at.getTotalStudent(), body);
            c = Excel.createCell(r, 5, ""+at.getTotalPresent(), body);
            c = Excel.createCell(r, 6, ""+at.getTotalAbsent(), body);
            c = Excel.createCell(r, 7, ""+at.getPresentPercentage(), body);
            c = Excel.createCell(r, 8, ""+at.getAbsentPercentage(), body);
            c = Excel.createCell(r, 9, ""+at.getAcadamicyear(), body);
            c = Excel.createCell(r, 10, ""+at.getCoursetype(), body);
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
        MessageUtil.showInformation(Message.INFORMATION, "Export Daily Class Statistics List", "List Exported Successfully", list.getScene().getWindow());
    }
}
