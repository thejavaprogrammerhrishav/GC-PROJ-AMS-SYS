/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.faculty.model.Faculty;
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
public class ExportFacultyList {
    private final String[] columnNames = {"Faculty Name","Gender", "Contact", "Email Id"};

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

    private TableView<Faculty> list;

    public ExportFacultyList(TableView<Faculty> list) {
        this.list = list;
        RowCount = 0;
    }

    public ExportFacultyList createFile() {
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

    public ExportFacultyList convertToExcel(String sheetName) {
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
            c = Excel.createCell(r, 0, at.getFullName(), body);
            c = Excel.createCell(r, 1, ""+at.getGender(), body);
            c = Excel.createCell(r, 2, ""+at.getContact(), body);
            c = Excel.createCell(r, 3, ""+at.getEmailId(), body);
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
        MessageUtil.showInformation(Message.INFORMATION, "Export Faculty List", "List Exported Successfully", list.getScene().getWindow());
    }
}
