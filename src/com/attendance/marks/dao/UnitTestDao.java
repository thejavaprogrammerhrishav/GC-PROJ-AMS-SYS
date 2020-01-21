/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.marks.dao;

import com.attendance.marks.model.UnitTest;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface UnitTestDao {

    public abstract int save(UnitTest ut);

    public abstract boolean saveAll(List<UnitTest> list);

    public abstract boolean update(UnitTest ut);

    public abstract boolean delete(UnitTest ut);

    public abstract List<UnitTest> findAll();

    public abstract List<UnitTest> findByUnitTest(String unitTest);

    public abstract List<UnitTest> findBySemesterAndAcademicyearAndYear(String semester, String academicyear, int year);

    public abstract <T> List<T> get(String query, Class<T> type);

}
