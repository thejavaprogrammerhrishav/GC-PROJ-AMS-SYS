/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.papers.service;

import com.attendance.papers.model.Paper;
import java.util.List;

/**
 *
 * @author AKSHAY KUMAR DHAR
 */
public interface PapersService {

    public abstract Long savePaper(Paper p);

    public abstract boolean updatePaper(Paper p);

    public abstract boolean deletePaper(Paper p);

    public abstract Paper findByCode(String code);

    public abstract List<Paper> findAll();

    public abstract List<Paper> findBySemester(String sem);

    public abstract List<Paper> findByPaperId(String newID, String oldID);

    public abstract List<Paper> findByDepartment(String Department);

    public abstract List<Paper> findByCourseType(String courseType);

    public abstract List<Paper> findByDepartmentAndCourseType(String department, String courseType);

    public abstract boolean exists(String code);

}
