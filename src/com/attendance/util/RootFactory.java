/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.dashboard.HODDashboardController;
import com.attendance.dashboard.FacultyDashboardController;
import com.attendance.dashboard.LoginActivityController;
import com.attendance.faculty.controller.RegisterFacultyController;
import com.attendance.faculty.controller.SelectFacultyController;
import com.attendance.faculty.controller.UpdateFacultyController;
import com.attendance.faculty.controller.ViewFacultyController;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.controller.HODLoginController;
import com.attendance.login.controller.FacultyLoginController;
import com.attendance.login.forgot.ForgotPasswordController;
import com.attendance.login.signup.HODSignupController;
import com.attendance.login.signup.FacultySignUpController;
import com.attendance.login.user.model.User;
import com.attendance.marks.controller.UploadUnitTestMarksController;
import com.attendance.notes.controller.DownloadNotesController;
import com.attendance.notes.controller.UploadNotesController;
import com.attendance.report.controller.AttendanceReportController;
import com.attendance.settings.ClassAndDetailsSettingsController;
import com.attendance.settings.DatabaseServerSettingsController;
import com.attendance.settings.ExportSettingsController;
import com.attendance.settings.FacultySettingsController;
import com.attendance.settings.LoginUserSettingsController;
import com.attendance.settings.PaperSettingsController;
import com.attendance.settings.SettingsController;
import com.attendance.settings.StudentSettingsController;
import com.attendance.settings.UserLoginActivitySettingsController;
import com.attendance.settings.sub.AttendanceController;
import com.attendance.settings.sub.ClassDetailsController;
import com.attendance.settings.sub.DailyStatsController;
import com.attendance.settings.sub.DeleteAdminUserController;
import com.attendance.settings.sub.DeleteFacultyController;
import com.attendance.settings.sub.DeleteFacultyUserController;
import com.attendance.settings.sub.DeletePaperController;
import com.attendance.settings.sub.DeleteStudentController;
import com.attendance.settings.sub.ExportClassDetailsListController;
import com.attendance.settings.sub.ExportDailyStatsListController;
import com.attendance.settings.sub.ExportFacultyListController;
import com.attendance.settings.sub.ExportStudentListController;
import com.attendance.settings.sub.NewPaperController;
import com.attendance.settings.sub.UpdateAdminUserController;
import com.attendance.settings.sub.UpdateClassDetailsController;
import com.attendance.settings.sub.UpdateFacultyUserController;
import com.attendance.settings.sub.UpdatePaperController;
import com.attendance.splash.SplashController;
import com.attendance.student.controller.RegisterStudentController;
import com.attendance.student.controller.StudentUpdateController;
import com.attendance.student.controller.ViewStudentDetailsController;
import com.attendance.studentattendance.controller.StudentAttendanceController;
import com.attendance.usertype.controller.UserTypeController;
import javafx.scene.Parent;

/**
 *
 * @author Programmer Hrishav
 */
public class RootFactory {

    public static Parent getSplashRoot() {
        return new SplashController();
    }

    public static Parent getUserTypeRoot() {
        return new UserTypeController();
    }

    public static Parent getForgotPasswordRoot() {
        return new ForgotPasswordController();
    }

    public static Parent getHODLoginRoot() {
        return new HODLoginController();
    }

    public static Parent getAdminDashboardRoot(User admin, LoginActivity activity) {
        return new HODDashboardController(admin, activity);
    }

    public static Parent getLoginActivityRoot(LoginActivity activity) {
        return new LoginActivityController(activity);
    }

    public static Parent getRegisterStudentRoot() {
        return new RegisterStudentController();
    }

    public static Parent getHODSignupRoot(Parent parent) {
        return new HODSignupController(parent);
    }

    public static Parent getFacultySignupRoot() {
        return new FacultySignUpController();
    }

    public static Parent getViewStudentDetailsRoot() {
        return new ViewStudentDetailsController();
    }

    public static Parent getStudentUpdateRoot() {
        return new StudentUpdateController();
    }

    public static Parent getFacultyLoginRoot() {
        return new FacultyLoginController();
    }

    public static Parent getRegisterFacultyRoot() {
        return new RegisterFacultyController();
    }

    public static Parent getUpdateFacultyRoot() {
        return new UpdateFacultyController();
    }

    public static Parent getViewFacultyRoot() {
        return new ViewFacultyController();
    }

    public static Parent getStudentAttendanceRoot(Parent sc, String faculty) {
        return new StudentAttendanceController(sc, faculty);
    }

    public static Parent getSelectFacultyRoot(String type) {
        return new SelectFacultyController(type);
    }

    public static Parent getFacultyDashboardRoot(User faculty, LoginActivity activity) {
        return new FacultyDashboardController(faculty, activity);
    }

    public static Parent getAttendanceReportRoot(Parent parent) {
        return new AttendanceReportController(parent);
    }

    public static Parent getSettingsRoot(Parent parent) {
        return new SettingsController(parent);
    }

    public static Parent getSettingsClassAndDetailsRoot(Parent parent) {
        return new ClassAndDetailsSettingsController(parent);
    }

    public static Parent getSettingsExportRoot(Parent parent) {
        return new ExportSettingsController(parent);
    }

    public static Parent getSettingsFacultyRoot(Parent parent) {
        return new FacultySettingsController(parent);
    }

    public static Parent getSettingsLoginUserRoot(Parent parent) {
        return new LoginUserSettingsController(parent);
    }

    public static Parent getSettingsPaperRoot(Parent parent) {
        return new PaperSettingsController(parent);
    }

    public static Parent getSettingsStudentRoot(Parent parent) {
        return new StudentSettingsController(parent);
    }

    public static Parent getDeleteStudentRoot(String sem) {
        return new DeleteStudentController(sem);
    }

    public static Parent getDeleteFacultyRoot() {
        return new DeleteFacultyController();
    }

    public static Parent getNewPaperRoot() {
        return new NewPaperController();
    }

    public static Parent getUpdatePaperRoot() {
        return new UpdatePaperController();
    }

    public static Parent getDeletePaperRoot() {
        return new DeletePaperController();
    }

    public static Parent getClassDetailsRoot() {
        return new ClassDetailsController();
    }

    public static Parent getUpdateClassDetailsRoot() {
        return new UpdateClassDetailsController();
    }

    public static Parent getAttendanceRoot() {
        return new AttendanceController();
    }

    public static Parent getDailyStatsRoot() {
        return new DailyStatsController();
    }

    public static Parent getUpdateAdminUserRoot() {
        return new UpdateAdminUserController();
    }

    public static Parent getMessageRoot(int type, String header, String content) {
        return new Message(type, header, content);
    }

    public static Parent getMessageRoot(int type, String header, Exception content) {
        return new Message(type, header, content);
    }

    public static Parent getDeleteAdminUserRoot() {
        return new DeleteAdminUserController();
    }

    public static Parent getDatabaseServerRoot(Parent parent) {
        return new DatabaseServerSettingsController(parent);
    }

    public static Parent getUpdateFacultyUserRoot() {
        return new UpdateFacultyUserController();
    }

    public static Parent getDeleteFacultyUserRoot() {
        return new DeleteFacultyUserController();
    }

    public static Parent getUserLoginActivitySettingsRoot(Parent parent) {
        return new UserLoginActivitySettingsController(parent);
    }

    public static Parent getExportStudentListRoot() {
        return new ExportStudentListController();
    }

    public static Parent getExportFacultyListRoot() {
        return new ExportFacultyListController();
    }

    public static Parent getExportClassDetailsListRoot() {
        return new ExportClassDetailsListController();
    }

    public static Parent getExportDailyStatsListRoot() {
        return new ExportDailyStatsListController();
    }

    public static Parent getUploadNotesRoot(Parent p,String name) {
        return new UploadNotesController(p,name);
    }
    
    public static Parent getDownloadNotesRoot(Parent p) {
        return new DownloadNotesController(p);
    }
    
    public static Parent getUploadMarksRoot() {
        return new UploadUnitTestMarksController();
    }
}
