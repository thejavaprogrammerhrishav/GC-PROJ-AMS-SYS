/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.studentattendance.dao.ClassDetailsDao;
import com.attendance.studentattendance.model.ClassDetails;
import com.attendance.studentattendance.service.AttendanceService;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class AttendanceServiceImpl implements AttendanceService {

    private ClassDetailsDao dao;

    private final String header = "Attendance Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public ClassDetailsDao getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(ClassDetailsDao dao) {
        this.dao = dao;
    }

    @Override
    public String saveAttendance(ClassDetails details) {
        try {
            return dao.save(details);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return "";
    }

    @Override
    public boolean updateAttendance(ClassDetails details) {
        try {
            return dao.update(details);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean deleteAttendance(ClassDetails details) {
        try {
            return dao.delete(details);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return false;
        }
    }

    @Override
    public List<ClassDetails> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public ClassDetails findById(String id) {
        try {
            return findById(id);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ClassDetails();
        }
    }

    @Override
    public List<ClassDetails> findByFacultyName(String facultyname) {
        try {
            return dao.findByFacultyName(facultyname);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDate(String date) {
        try {
            return dao.findByDate(date);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByTime(String time) {
        try {
            return dao.findByTime(time);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findBySemester(String semester) {
        try {
            return dao.findBySemester(semester);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByAcadamicYear(String acadamicYear) {
        try {
            return dao.findByAcadamicYear(acadamicYear);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByYear(int year) {
        try {
            return dao.findByYear(year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findBySemesterAndYear(String semester, int year) {
        try {
            return dao.findBySemesterAndYear(semester, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByAcadamicYearAndYear(String acadamicYear, int year) {
        try {
            return findByAcadamicYearAndYear(acadamicYear, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndSemesterAndPaperCodeAndYear(String department, String semester, String papercode, int year) {
        try {
            return dao.findByDepartmentAndSemesterAndPaperCodeAndYear(department, semester, papercode, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartment(String department) {
        try {
            return dao.findByDepartment(department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndSemesterAndYear(String department, String semester, int year) {
        try {
            return dao.findByDepartmentAndSemesterAndYear(department, semester, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndAcadamicYearAndYear(String department, String acadamicYear, int year) {
        try {
            return dao.findByDepartmentAndAcadamicYearAndYear(department, acadamicYear, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndCourseType(String department, String courseType) {
        try {
            return dao.findByDepartmentAndCourseType(department, courseType);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndCourseTypeAndYear(String department, String courseType, int year) {
        try {
            return dao.findByDepartmentAndCourseTypeAndYear(department, courseType, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndCourseTypeAndAcadamicYearAndYear(String department, String courseType, String acadamicYear, int year) {
        try {
            return dao.findByDepartmentAndCourseTypeAndAcadamicYearAndYear(department, courseType, acadamicYear, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByDepartmentAndCourseTypeAndSemesterAndYear(String department, String courseType, String semester, int year) {
        try {
            return dao.findByDepartmentAndCourseTypeAndSemesterAndYear(department, courseType, semester, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByFacultyAndDepartmentAndCourseTypeAndSemesterAndYear(String faculty, String department, String courseType, String semester, int year) {
        try {
            return dao.findByFacultyAndDepartmentAndCourseTypeAndSemesterAndYear(faculty, department, courseType, semester, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findByFacultyAndDepartmentAndCourseTypeAndAcadamicAndYear(String faculty, String department, String courseType, String acadamicYear, int year) {
        try {
            return dao.findByFacultyAndDepartmentAndCourseTypeAndAcadamicAndYear(faculty, department, courseType, acadamicYear, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<ClassDetails> findAll(String department, String acadamicYear, String semester, int year, String papercode, String coursetype) {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateClassId(String oldId, String newId) {
        try {
            return dao.updateClassId(oldId, newId);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return false;
        }
    }
}
