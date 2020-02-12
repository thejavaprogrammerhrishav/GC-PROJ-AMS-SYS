/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.services.impl;

import com.attendance.login.dao.Login;
import com.attendance.login.service.LoginService;
import com.attendance.login.user.model.User;
import com.attendance.util.ExceptionConverter;
import com.attendance.util.ExceptionDialog;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author pc
 */
public class LoginServiceImpl implements LoginService {

    private Login dao;

    private final String header = "Login Information Error";

    private ExceptionDialog ex;
    private Parent parent;

    public void setEx(ExceptionDialog ex) {
        this.ex = ex;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Login getDao() {
        return dao;
    }

    public ExceptionDialog getEx() {
        return ex;
    }

    public void setDao(Login dao) {
        this.dao = dao;
    }

    @Override
    public int saveUser(User user) {
        try {
            return dao.save(user);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return -1;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return dao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            return dao.delete(user);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public User findById(int userId) {
        try {
            return dao.findById(userId);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new User();
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return dao.findByUsername(username);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new User();
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<User> findByDepartment(String department) {
        try {
            return dao.findByDepartment(department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<User> findByType(String type) {
        try {
            return dao.findByType(type);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public User findByUsernameDepartmentType(String username, String department, String type) {
        try {
            return dao.findByUsernameDepartmentType(username, department, type);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new User();
        }
    }

    @Override
    public List<User> findByStatus(String status) {
        try {
            return dao.findByStatus(status);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public List<User> findByStatusAndDepartment(String status, String department) {
        try {
            return dao.findByStatusAndDepartment(status, department);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return new ArrayList<>();
        }
    }

    @Override
    public int count(String sql) {
        try {
            return dao.count(sql);
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

            return -1;
        }
    }

    @Override
    public boolean login(String username, String password, String type) {
        try {
            User user = dao.findByUsernameDepartmentType(username, password, type);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }

    @Override
    public boolean isUsernameExists(String username) {
        try {
            return dao.isUsernameExists(username)==0;
        } catch (Exception e) {
            ex.showError(parent, header, ExceptionConverter.getException(e));

        }
        return false;
    }
    
    

}
