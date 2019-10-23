/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attendance.util;

import javafx.fxml.FXMLLoader;

/**
 *
 * @author Programmer Hrishav
 */
public class Fxml {

    public static FXMLLoader getSplashFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/splash.fxml"));
    }

    public static FXMLLoader getUserType2FXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/usertype2.fxml"));
    }

    public static FXMLLoader getHODLoginFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/hodlogin.fxml"));
    }

    public static FXMLLoader getForgotPasswordFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/Forgetpassword.fxml"));
    }

    public static FXMLLoader getHODDashboardFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/hoddashboard.fxml"));
    }

    public static FXMLLoader getHODLoginActivityFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/loginactivitynode.fxml"));
    }

    public static FXMLLoader getRegisterStudentFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/addnewstudent.fxml"));
    }

    public static FXMLLoader getHODSignUpFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/hodsignup.fxml"));
    }

    public static FXMLLoader getFacultySignupFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/facultysignup.fxml"));
    }

    public static FXMLLoader getViewStudentDetailsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewstudentdetails.fxml"));
    }

    public static FXMLLoader getStudentUpdateFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/updatestudentdetails.fxml"));
    }

    public static FXMLLoader getFacultyLoginFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/facultylogin.fxml"));
    }

    public static FXMLLoader getViewFacultyDetailsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewfacultydetails.fxml"));
    }

    public static FXMLLoader getStudentAttendanceFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/Studentattendance.fxml"));
    }

    public static FXMLLoader getStudentAttendanceNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/studentattendancestatus.fxml"));
    }

    public static FXMLLoader getSelectFacultyFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/selectfaculty.fxml"));
    }

    public static FXMLLoader getSelectDepartmentFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/selectdepartment.fxml"));
    }

    public static FXMLLoader getFacultyDashboardFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/facultydashboard.fxml"));
    }

    public static FXMLLoader getAttendanceReportFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/report.fxml"));
    }

    public static FXMLLoader getSettingsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/setting.fxml"));
    }

    public static FXMLLoader getSettingsClassAndDetailsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/settingsClassAndDetails.fxml"));
    }

    public static FXMLLoader getSettingsExportFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/settingsExport.fxml"));
    }

    public static FXMLLoader getSettingsFacultyFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/settingsFaculty.fxml"));
    }

    public static FXMLLoader getSettingsLoginUserFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/settingsLoginUser.fxml"));
    }

    public static FXMLLoader getSettingsPaperFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/settingsPaper.fxml"));
    }

    public static FXMLLoader getSettingsStudentFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/settingsStudent.fxml"));
    }

    public static FXMLLoader getDeleteStudentFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/deletestudentdetails.fxml"));
    }

    public static FXMLLoader getNewPaperFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/addnewpaper.fxml"));
    }

    public static FXMLLoader getUpdatePaperFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/updatepaper.fxml"));
    }

    public static FXMLLoader getDeletePaperFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/deletepaper.fxml"));
    }

    public static FXMLLoader getClassDetailsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/classdetails.fxml"));
    }

    public static FXMLLoader getUpdateClassDetailsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/Updateclassdetail.fxml"));
    }

    public static FXMLLoader getAttendanceFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/attendance.fxml"));
    }

    public static FXMLLoader getDailyStatsFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/dailystats.fxml"));
    }

    public static FXMLLoader getUpdateAdminFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/updateadminuser.fxml"));
    }

    public static FXMLLoader getMessageFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/message.fxml"));
    }

    public static FXMLLoader getDeleteAdminUserFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/DeleteAdministratorUser.fxml"));
    }

    public static FXMLLoader getDatabaseServerFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/Database.fxml"));
    }

    public static FXMLLoader getUpdateFacultyUserFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/updatefaultyuser.fxml"));
    }

    public static FXMLLoader getDeleteFacultyUserFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/deletefacultyuser.fxml"));
    }

    public static FXMLLoader getUserLoginActivityTrackingFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/userloginactivitysettings.fxml"));
    }

    public static FXMLLoader getExportStudentListFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/ExportStudentList.fxml"));
    }

    public static FXMLLoader getExportFacultyListFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/ExportFacultyexcel.fxml"));
    }

    public static FXMLLoader getExportClassDetailsListFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/ExportClassDetails.fxml"));
    }

    public static FXMLLoader getExportDailtStatsListFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/ExportDailyStats.fxml"));
    }

    public static FXMLLoader getUploadFilesFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/uploadnotes.fxml"));
    }

    public static FXMLLoader getDownloadFilesFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/downloadnotes.fxml"));
    }

    public static FXMLLoader getUploadMarksFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/uploadmarks.fxml"));
    }

    public static FXMLLoader getUploadMarksNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/Unittestnode.fxml"));
    }

    public static FXMLLoader getUserType1FXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/usertype1.fxml"));
    }

    public static FXMLLoader getPrincipalSignUpFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principalsignup.fxml"));
    }

    public static FXMLLoader getPrincipalLoginFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principal login.fxml"));
    }

    public static FXMLLoader getDepartmentFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/DepartmentSelection.fxml"));
    }

    public static FXMLLoader getPrincipalDashboardFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principaldashboard.fxml"));
    }

    public static FXMLLoader getPrincipalDashboardStudentNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principaldashboardstudentnode.fxml"));
    }

    public static FXMLLoader getPrincipalDashboardFacultyNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principaldashboardfacultynode.fxml"));
    }

    public static FXMLLoader getPrincipalDashboardDepartmentNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principaldashboarddepartmentnode.fxml"));
    }

    public static FXMLLoader getPrincipalDashboardPrincipalAccountNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principaldashboardprincipalaccount.fxml"));
    }

    public static FXMLLoader getPrincipalDashboardHODAccountNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principaldashboardhodaccount.fxml"));
    }

    public static FXMLLoader getProfileFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/profile.fxml"));
    }

    public static FXMLLoader getChangePasswordFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/changepassword.fxml"));
    }

    public static FXMLLoader getPrincipalLoginActivityFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/principalloginactivity.fxml"));
    }

    public static FXMLLoader getFacultyLoginActivityFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/facultyloginactivitynode.fxml"));
    }

    public static FXMLLoader getViewFacultyNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/vfnode.fxml"));
    }

    public static FXMLLoader getViewFacultyDetailsNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewfacultynode.fxml"));
    }
    
    public static FXMLLoader getPendingRequestFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/pendingrequests.fxml"));
    }
    
    public static FXMLLoader getPendingRequestNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/requestnode.fxml"));
    }
    
    public static FXMLLoader getUnitTestReportFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/unittestreport.fxml"));
    }
    
    public static FXMLLoader getNotesDashboardFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/notes.fxml"));
    }
    
    public static FXMLLoader getNotesNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/notesnode.fxml"));
    }
    
    public static FXMLLoader getNotesSearchFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/notesearch.fxml"));
    }
    
    public static FXMLLoader getRoutineDashboardFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/routine.fxml"));
    }
    
    public static FXMLLoader getAddNewRoutineFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/addnewroutine.fxml"));
    }
    
    public static FXMLLoader getUpdateActiveRoutineFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/updateactiveroutine.fxml"));
    }
    
    public static FXMLLoader getUpdateActiveRoutineNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/updateroutinenode.fxml"));
    }
    
    public static FXMLLoader getViewAllRoutineFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewallroutine.fxml"));
    }
    
    
    public static FXMLLoader getViewAllRoutineNodeFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewallroutinenode.fxml"));
    }
    
    public static FXMLLoader getViewRoutineFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewroutine.fxml"));
    }
    
    public static FXMLLoader getViewActiveRoutineFXML() {
        return new FXMLLoader(Fxml.class.getResource("/com/attendance/fxml/resources/viewactiveroutine.fxml"));
    }
}
