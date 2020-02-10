/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.service;

import com.attendance.marks.model.UnitTest;
import java.util.List;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface MarksService {

    public abstract int saveMarks(UnitTest ut);

    public abstract boolean saveAllMarks(List<UnitTest> list);

    public abstract boolean updateMarks(UnitTest ut);

    public abstract boolean deleteMarks(UnitTest list);

    public abstract List<UnitTest> findAll();

    public abstract List<UnitTest> findByUnitTest(String unitTest);

    public abstract List<UnitTest> findBySemesterAndAcademicyearAndYear(String semester, String academicyesr, int year);

    public abstract <T> List<T> get(String query, Class<T> type);
}
