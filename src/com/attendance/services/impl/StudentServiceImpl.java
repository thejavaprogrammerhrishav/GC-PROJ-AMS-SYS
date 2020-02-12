/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.student.dao.StudentDao;
import com.attendance.student.model.Student;
import com.attendance.student.service.StudentService;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pc
 */
public class StudentServiceImpl implements StudentService {

    private StudentDao dao;

    private final String header = "Student Information Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public StudentDao getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(StudentDao dao) {
        this.dao = dao;
    }

    @Transactional
    private boolean update(Student s, String ns) {
        boolean u = dao.updateStudent(s);
        boolean p = dao.updateStudentId(ns, s.getId());
        return u && p;
    }

    @Override
    public String saveStudent(Student s) {
        try {
            return dao.saveStudent(s);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return "";
        }
    }

    @Override
    public boolean updateStudent(Student s, String ns) {
        try {
            return update(s, ns);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return false;
    }

    @Override
    public boolean deleteStudent(Student s) {
        try {
            return dao.deleteStudent(s);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return false;
    }

    @Override
    public Student findById(String id) {
        try {
            return dao.findById(id);
        } catch (Exception e) {
            if (e.getMessage().contains("JBDCConnectionExecption")) {

                ex.showError(parent, header, ExceptionConverter.getException(e));
            }
            return new Student();
        }

    }

    @Override
    public List<Student> findByYear(int year) {
        try {
            return dao.findByYear(year);
        } catch (Exception e) {
            if (e.getMessage().contains("JBDCConnectionExecption")) {

                ex.showError(parent, header, ExceptionConverter.getException(e));
            }
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> findByAcadamicyear(String acadamicyear) {
        try {
            return dao.findByAcadamicYear(acadamicyear);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return new ArrayList<>();
    }

    @Override
    public List<Student> findByGender(String gender) {
        try {
            return dao.findByGender(gender);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> findByRollNumber(int rollno
    ) {
        try {
            return dao.findByRollNumber(rollno);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> findByName(String name
    ) {
        try {
            return dao.findByName(name);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> findByAcadamicYearAndyear(String acadamicyear, int year
    ) {
        try {
            return dao.findByAcadamicYearAndYear(acadamicyear, year);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateStudentId(String newId, String oldId
    ) {
        try {
            return dao.updateStudentId(newId, oldId);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return false;
    }

    @Override
    public <T> List<T> get(String query, Class<T> t) {
        try {
            return dao.get(query, t);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<T>();
        }
    }

    @Override
    public int countStudents(String acadamicyear, String department) {
        try {
            return dao.countStudents(acadamicyear, department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return 0;
    }

    @Override
    public int countStudents(String acadamicyear, int year, String department) {
        try {
            return dao.countStudents(acadamicyear, "" + year, department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
        }
        return 0;
    }

    @Override
    public List<Student> findByCourseType(String courseType) {
        try {
            return dao.findByCourseType(courseType);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> findByDepartment(String department) {
        try {
            return dao.findByDepartment(department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> findAllYear() {
        try {
            return dao.findAllYears();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));
            return new ArrayList<>();
        }
    }

}
