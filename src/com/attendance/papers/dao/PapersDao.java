/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.papers.dao;

import com.attendance.papers.model.Paper;
import java.util.List;

/**
 *
 * @author Programmer Hrishav
 */
public interface PapersDao {

    public abstract long save(Paper p);

    public abstract boolean update(Paper p);

    public abstract boolean delete(Paper p);

    public abstract Paper findByCode(String code);

    public abstract List<Paper> findAll();

    public abstract List<Paper> findBySemester(String sem);

    public boolean updatePaperId(String newId, String oldId);

    public abstract List<Paper> findByDepartment(String department);

    public abstract List<Paper> findByCourseType(String courseType);

    public abstract List<Paper> findByDepartmentAndCourseType(String department,String courseType);
    
    public abstract boolean exists(String code);

}
