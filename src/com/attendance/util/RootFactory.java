/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import com.attendance.dashboard.HODDashboardController;
import com.attendance.dashboard.FacultyDashboardController;
import com.attendance.dashboard.HODLoginActivityController;
import com.attendance.dashboard.PrincipalDashboardController;
import com.attendance.dashboard.node.PrincipalDashboardDepartmentNodeController;
import com.attendance.dashboard.node.PrincipalDashboardFacultyNodeController;
import com.attendance.dashboard.node.PrincipalDashboardHODAccountNodeController;
import com.attendance.dashboard.node.PrincipalDashboardPrincipalAccountNodeController;
import com.attendance.dashboard.node.PrincipalDashboardStudentNodeController;
import com.attendance.dashboard.principal.PrincipalRoutineDashboardController;
import com.attendance.faculty.controller.SelectFacultyController;
import com.attendance.faculty.controller.ViewFacultyController;
import com.attendance.login.activity.model.LoginActivity;
import com.attendance.login.controller.HODLoginController;
import com.attendance.login.controller.FacultyLoginController;
import com.attendance.login.controller.PrincipalLoginController;
import com.attendance.login.forgot.ChangePasswordController;
import com.attendance.login.forgot.ResetPassword1Controller;
import com.attendance.login.forgot.ResetPassword3Controller;
import com.attendance.login.forgot.ResetPasswordResultController;
import com.attendance.login.forgot.SecurityQuestionController;
import com.attendance.login.request.PendingRequestController;
import com.attendance.login.signup.HODSignupController;
import com.attendance.login.signup.FacultySignUpController;
import com.attendance.login.signup.PrincipalSignUpController;
import com.attendance.marks.controller.UnitTestReportController;
import com.attendance.marks.controller.UploadUnitTestMarksController;
import com.attendance.notes.controller.NotesDashboardController;
import com.attendance.papers.controller.PapersController;
import com.attendance.report.controller.AttendanceReportController;
import com.attendance.routines.controller.RoutineDashboardController;
import com.attendance.settings.ClassAndDetailsSettingsController;
import com.attendance.settings.DatabaseServerSettingsController;
import com.attendance.settings.ExportSettingsController;
import com.attendance.settings.HODLoginActivitySettingsController;
import com.attendance.settings.NotesSettingController;
import com.attendance.settings.HODSettingsController;
import com.attendance.settings.PrincipalLoginActivitySettingsController;
import com.attendance.settings.PrincipalSettingsController;
import com.attendance.settings.UserLoginActivitySettingsController;
import com.attendance.settings.sub.AttendanceController;
import com.attendance.settings.sub.BlockLoginUserController;
import com.attendance.settings.sub.ClassDetailsController;
import com.attendance.settings.sub.DailyStatsController;
import com.attendance.settings.sub.DeleteLoginUserController;
import com.attendance.settings.sub.ExportClassDetailsListController;
import com.attendance.settings.sub.ExportDailyStatsListController;
import com.attendance.settings.sub.ExportFacultyListController;
import com.attendance.settings.sub.ExportStudentListController;
import com.attendance.settings.sub.LoadingController;
import com.attendance.settings.sub.ModifyUsertypeController;
import com.attendance.settings.sub.UpdateClassDetailsController;
import com.attendance.splash.SplashController;
import com.attendance.student.controller.RegisterStudentController;
import com.attendance.student.controller.StudentUpdateController;
import com.attendance.student.controller.ViewStudentDetailsController;
import com.attendance.studentattendance.controller.StudentAttendanceController;
import com.attendance.user.UserProfileController;
import com.attendance.usertype.controller.DepartmentPageController;
import com.attendance.usertype.controller.SelectDepartmentController;
import com.attendance.usertype.controller.UserType1Controller;
import com.attendance.usertype.controller.UserType2Controller;
import javafx.scene.Parent;

/**
 *
 * @author Programmer Hrishav
 */
public class RootFactory {

    public static Parent getSplashRoot() {
        return new SplashController();
    }

    public static Parent getUserType2Root() {
        return new UserType2Controller();
    }

    public static Parent getHODLoginRoot() {
        return new HODLoginController();
    }

    public static Parent getHODDashboardRoot() {
        return new HODDashboardController();
    }

    public static Parent getPrincipalDashboardRoot() {
        return new PrincipalDashboardController();
    }

    public static Parent getLoginActivityRoot(LoginActivity activity) {
        return new HODLoginActivityController(activity);
    }

    public static Parent getRegisterStudentRoot(Parent parent) {
        return new RegisterStudentController(parent);
    }

    public static Parent getHODSignupRoot(Parent parent) {
        return new HODSignupController(parent);
    }

    public static Parent getFacultySignupRoot(Parent parent) {
        return new FacultySignUpController(parent);
    }

    public static Parent getViewStudentDetailsRoot(String department, Parent parent) {
        return new ViewStudentDetailsController(department, parent);
    }

    public static Parent getStudentUpdateRoot(Parent parent) {
        return new StudentUpdateController(parent);
    }

    public static Parent getFacultyLoginRoot() {
        return new FacultyLoginController();
    }

    public static Parent getViewFacultyRoot(String department, Parent parent) {
        return new ViewFacultyController(department, parent);
    }

    public static Parent getStudentAttendanceRoot(Parent sc, String faculty) {
        return new StudentAttendanceController(sc, faculty);
    }

    public static Parent getSelectFacultyRoot(String type) {
        return new SelectFacultyController(type);
    }

    public static Parent getSelectDepartmentRoot(String type, Parent parent) {
        return new SelectDepartmentController(type, parent);
    }

    public static Parent getFacultyDashboardRoot() {
        return new FacultyDashboardController();
    }

    public static Parent getAttendanceReportRoot(Parent parent) {
        return new AttendanceReportController(parent);
    }

    public static Parent getSettingsRoot(Parent parent) {
        return new HODSettingsController(parent);
    }

    public static Parent getSettingsClassAndDetailsRoot(Parent parent) {
        return new ClassAndDetailsSettingsController(parent);
    }

    public static Parent getSettingsExportRoot(Parent parent) {
        return new ExportSettingsController(parent);
    }

    public static Parent getPaperRoot(Parent parent) {
        return new PapersController(parent);
    }

    public static Parent getClassDetailsRoot(Parent parent, String name) {
        return new ClassDetailsController(parent, name);
    }

    public static Parent getUpdateClassDetailsRoot() {
        return new UpdateClassDetailsController();
    }

    public static Parent getAttendanceRoot(Parent parent,String faculty) {
        return new AttendanceController(parent,faculty);
    }

    public static Parent getDailyStatsRoot(Parent parent,String faculty) {
        return new DailyStatsController(parent,faculty);
    }

    public static Parent getMessageRoot(int type, String header, String content) {
        return new Message(type, header, content);
    }

    public static Parent getMessageRoot(int type, String header, Exception content) {
        return new Message(type, header, content);
    }

    public static Parent getDatabaseServerRoot(Parent parent) {
        return new DatabaseServerSettingsController(parent);
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

    public static Parent getUploadMarksRoot(Parent parent) {
        return new UploadUnitTestMarksController(parent);
    }

    public static Parent getUserType1Root() {
        return new UserType1Controller();
    }

    public static Parent getDepartmentRoot() {
        return new DepartmentPageController();
    }

    public static Parent getPrincipalLoginRoot() {
        return new PrincipalLoginController();
    }

    public static Parent getPrincipalSignUpRoot(Parent parent) {
        return new PrincipalSignUpController(parent);
    }

    public static Parent getPrincipalDashboardStudentNodeRoot() {
        return new PrincipalDashboardStudentNodeController();
    }

    public static Parent getPrincipalDashboardFacultyNodeRoot() {
        return new PrincipalDashboardFacultyNodeController();
    }

    public static Parent getPrincipalDashboardDepartmentNodeRoot() {
        return new PrincipalDashboardDepartmentNodeController();
    }

    public static Parent getPrincipalDashboardPrincipalAccountNodeRoot() {
        return new PrincipalDashboardPrincipalAccountNodeController();
    }

    public static Parent getPrincipalDashboardHODAccountNodeRoot() {
        return new PrincipalDashboardHODAccountNodeController();
    }

    public static Parent getUserProfileRoot(String parent) {
        return new UserProfileController(parent);
    }

    public static Parent getChangePasswordRoot(Parent parent) {
        return new ChangePasswordController(parent);
    }

    public static Parent getPendingRequestRoot(Parent parent, String department) {
        return new PendingRequestController(parent, department);
    }

    public static Parent getUnitTestReportRoot(Parent parent) {
        return new UnitTestReportController(parent);
    }

    public static Parent getNotesDashboardRoot(Parent parent, String facultyname) {
        return new NotesDashboardController(parent, facultyname);
    }

    public static Parent getRoutineDashboardRoot(Parent parent) {
        return new RoutineDashboardController(parent);
    }

    public static Parent getPrincipalRoutineDashboardRoot(Parent parent, String department, String year) {
        return new PrincipalRoutineDashboardController(parent, department, year);
    }

    public static Parent getNotesSettingsRoot(Parent parent) {
        return new NotesSettingController(parent);
    }

    public static Parent getPrincipalSettingsRoot(Parent parent) {
        return new PrincipalSettingsController(parent);
    }

    public static Parent getDeleteLoginUserRoot(Parent parent, String department) {
        return new DeleteLoginUserController(parent, department);
    }

    public static Parent getBlockLoginUserRoot(Parent parent, String department) {
        return new BlockLoginUserController(parent, department);
    }

    public static Parent getModifyUsertypeRoot(Parent parent) {
        return new ModifyUsertypeController(parent);
    }

    public static Parent getPrincipalLoginUserActivityRoot(Parent parent) {
        return new PrincipalLoginActivitySettingsController(parent);
    }

    public static Parent getHODLoginUserActivityRoot(Parent parent) {
        return new HODLoginActivitySettingsController(parent);
    }

    public static Parent getResetPassword1Root(Parent parent) {
        return new ResetPassword1Controller(parent);
    }

    public static Parent getRestPassword3Root(Parent parent) {
        return new ResetPassword3Controller(parent);
    }

    public static Parent getSecurityQuestionRoot(Parent parent, String mode) {
        return new SecurityQuestionController(parent, mode);
    }

    public static Parent getResetPasswordResultRoot(String mode) {
        return new ResetPasswordResultController(mode);
    }
    
    public static Parent getLoadingRoot() {
        return new LoadingController();
    }


}
