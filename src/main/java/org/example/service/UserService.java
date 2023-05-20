package org.example.service;

import org.example.domain.*;

import java.util.List;
import java.util.stream.DoubleStream;

public interface UserService {

    void createNewStudent(Student student);

    List<WebRegClass> getClassesbyCourseId(int id);

    List<WebRegClass> getClassesbyStudentId(int id);

    Course getCourseById(int id);

    Professor getProfessorById(int id);

    Semester getSemesterById(int id);

    Department getDepartmentById(int id);

    WebRegClass getWebRegClassById(int id);

    List<Enrollment> getActiveEnrollmentsByClassId(int id);

    Lecture getLectureByClassId(int id);

    Classroom getClassroomById(int id);

    List<Prerequisite> getPrerequisitesByCourseId(int id);

    List<WebRegClass> getAllActiveClasses();

    boolean getEnrollmentStatus(int studentId,int classId);
    String getClassStatus(int studentId,int classId);
    Student getStudentById(int studentId);

    boolean checkIfStudentEligible(Student student,WebRegClass theClass);

    boolean checkIfStudentPassCourseById(Student student,int courseId);

    boolean checkTimeConflict(Student student,WebRegClass theClass);

    List<Course> getAllCourses();

    void createNewCourse(Course course);

    boolean checkIfClassIsFull(WebRegClass theClass);

    void addEnrollment(int studentId, int classId);

    boolean checkIfDroppable(int studentId,WebRegClass theClass);



    void dropEnrollments(int studentId, int classId);


    void withdrawEnrollment(int studentId,int classId);
    boolean checkIfWithdrawable(int studentId,WebRegClass theClass);

    boolean checkIfWithdrawn(int studentId,int classId);


    void createEnrollmentApplication(int studentId,int classId);
    List<Student> getAllStudents();

    List<WebRegClass> getAllStatusClassesbyStudentId(int studentId);

    void createNewClass(WebRegClass theClass);

    List<WebRegClass> getAllClasses();

    void deactivateClassById(int classId);

    void activateClassById(int classId);

    void createApplicationByStudentClassId(int id, int classId);

    List<Application> getApplicationsByStudentId(int id);

    List<Application> getAllPendingApplications();

    void rejectOrApproveByIdFeedback(String status, int applicationId, String f);

    void activateStudentById(int studentId);

    void deactivateStudentById(int studentId);

    void passStudentByClassId(int studentId, int classId);

    void failStudentByClassId(int studentId, int classId);

    
    String getRequestByApplicationId(int applicationId);

    int getStudentIdByApplicationId(int applicationId);

    int getClassIdByApplicationId(int applicationId);


    int getTotalPages(int studentId,int limit);

    List<WebRegClass> getClassesbyStudentIdPageLimit(int id, int page, int limit);

    int getTotalPagesAdmin(int limit);

    List<Student> getStudentsByPageLimit(int page, int limit);

    String getCourseNameById(int courseId);

    Lecture getLectureById(int lecture_id);

    void withdrawApplication(int applicationId);

    Application getApplicationById(int applicationId);

    void resubmitApplication(int applicationId);

    List<Application> getAllApplications();

    List<Enrollment> getEnrollmentByStudentId(int studentId);

    WebRegClass getClassesbyStudentIdEnrollmentIdPageLimit(int studentId, int enrollmentId, int page, int limit);

    List<Enrollment> getEnrollmentByStudentIdPageLimit(int id, int page, int limit);

    WebRegClass getClassesbyId(int class_id);
}
