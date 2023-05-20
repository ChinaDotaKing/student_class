package org.example.service;

import org.example.dao.*;
import org.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    StudentDao studentDao;

    @Autowired
    WebRegClassDao webRegClassDao;

    @Autowired
    SemesterDao semesterDao;

    @Autowired
    ProfessorDao professorDao;

    @Autowired
    ClassroomDao classroomDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    EnrollmentDao enrollmentDao;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    LectureDao lectureDao;

    @Autowired
    PrerequisiteDao prerequisiteDao;

    @Autowired
    ApplicationDao applicationDAO;



    public UserServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void createNewStudent(Student student) {
            studentDao.createNewStudent(student);

    }

    @Override
    public List<WebRegClass> getClassesbyCourseId(int id) {
        return webRegClassDao.getWebRegClassesByCourseId(id);
    }

    @Override
    public List<WebRegClass> getClassesbyStudentId(int id) {

        return webRegClassDao.getWebRegClassesByStudentId(id);
    }



    public Course getCourseById(int id) {

        return courseDao.getCourseById(id);
    }

    public Professor getProfessorById(int id) {

        return professorDao.getProfessorById(id);
    }

    public Semester getSemesterById(int id) {

        return semesterDao.getSemesterById(id);
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentDao.getDepartmentById(id);
    }

    @Override
    public WebRegClass getWebRegClassById(int id) {
        return webRegClassDao.getWebRegClassById(id);
    }

    @Override
    public List<Enrollment> getActiveEnrollmentsByClassId(int id) {
        return enrollmentDao.getActiveEnrollmentsByClassId(id);
    }

    @Override
    public Lecture getLectureByClassId(int id) {
        return lectureDao.getLectureByClassId(id);
    }

    @Override
    public Classroom getClassroomById(int id) {
        return classroomDao.getClassroomById(id);
    }

    @Override
    public List<Prerequisite> getPrerequisitesByCourseId(int id) {
        return prerequisiteDao.getPrerequisiteByCourseId(id);
    }

    @Override
    public List<WebRegClass> getAllActiveClasses() {
        return webRegClassDao.getAllActiveClasses();
    }

    @Override
    public boolean getEnrollmentStatus(int studentId, int classId) {
        return enrollmentDao.getEnrollmentStatus(studentId,classId);
    }

    @Override
    public String getClassStatus(int studentId, int classId) {
        return enrollmentDao.getStatus(studentId,classId);
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentDao.getStudentById(studentId);
    }

    @Override
    public boolean checkIfStudentEligible(Student student,WebRegClass theClass) {
        if(!student.is_active()) {
            System.out.print("not active student");
            return false;
        }
        if(!theClass.is_active()){
            System.out.print("not active class");
            return false;

        }
        List<Prerequisite> prerequisites=
                getPrerequisitesByCourseId(theClass.getCourse_id());
        if (!(prerequisites==null))
        for(Prerequisite prerequisite:prerequisites){
            if(!checkIfStudentPassCourseById(student,prerequisite.getPre_req_id())) {
                System.out.print(prerequisite.getPre_req_id());
                System.out.print("not pass pre-req");
                return false;
            }
        }

        if(checkIfWithdrawn(student.getId(),theClass.getId())) {
            System.out.print("withdrawn");
            return false;
        }

        if(checkIfStudentPassCourseById(student,theClass.getCourse_id())) {
            System.out.print("pass the course");
            return false;
        }

        if(checkTimeConflict(student,theClass)) {
            System.out.print("time conflict");
            return false;
        }


        return true;
    }



    @Override
    public boolean checkIfStudentPassCourseById(Student student, int courseId) {
        List<Enrollment> Enrollments= enrollmentDao.getClassesByStudentId(student.getId());
       // System.out.print(Enrollments.size());
        if(Enrollments==null) return false;

        List<WebRegClass> classes=Enrollments.stream().map(c->webRegClassDao.getWebRegClassById(c.getClass_id())).collect(Collectors.toList());

        if(classes==null) return false;

        for(WebRegClass theClass:classes) if(theClass.getCourse_id()==courseId)return true;


        return false;
    }

    @Override
    public boolean checkTimeConflict(Student student, WebRegClass theClass) {

        List<Enrollment> Enrollments= enrollmentDao.getClassesByStudentId(student.getId());
        if(Enrollments==null) return false;

        List<WebRegClass> classes=Enrollments.stream().map(c->webRegClassDao.getWebRegClassById(c.getClass_id())).collect(Collectors.toList());
        if(classes==null) return false;

        List<Lecture> lectures=classes.stream().map(c->lectureDao.getLectureByClassId(c.getId())).collect(Collectors.toList());
        if(lectures==null) return false;

        Lecture theLecture=lectureDao.getLectureByClassId(theClass.getId());
        if(theLecture==null) return false;

        for(int i=0;i<classes.size();i++){
            if(classes.get(i).getSemester_id()==theClass.getSemester_id()) return false;

            if(lectures.get(i).getEnd_time().before(theLecture.getStart_time())  ||
                    lectures.get(i).getStart_time().after(theLecture.getEnd_time())||
                    lectures.get(i).getDay_of_week()!=theLecture.getDay_of_week()
            ) continue;
            else return false;
        }
        return true;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    @Override
    public void createNewCourse(Course course) {
        courseDao.createNewCourse(course);
    }

    @Override
    public boolean checkIfClassIsFull(WebRegClass theClass) {
        return enrollmentDao.getEnrollmentNumByClassId(theClass.getId())==theClass.getCapacity()?true:false;

    }

    public boolean checkIfDroppable(int studentId,WebRegClass theClass){
        Semester semester=semesterDao.getSemesterById(theClass.getSemester_id());
        if(enrollmentDao.getStatus(studentId,theClass.getId())==null||!enrollmentDao.getStatus(studentId,theClass.getId()).equals("ongoing")) return false;

        Calendar c = Calendar.getInstance();
        c.setTime(semester.getStart_date());
        c.add(Calendar.DATE,14);

        Date date=c.getTime();

        Date d=new java.sql.Date(System.currentTimeMillis());

        if(!d.before(date)) return false;

        return true;



    }
    public boolean checkIfWithdrawable(int studentId,WebRegClass theClass){

        Semester semester=semesterDao.getSemesterById(theClass.getSemester_id());
        if(enrollmentDao.getStatus(studentId,theClass.getId())==null||!enrollmentDao.getStatus(studentId,theClass.getId()).equals("ongoing")) return false;

        Calendar c = Calendar.getInstance();
        c.setTime(semester.getStart_date());
        c.add(Calendar.DATE,14);

        Date date=c.getTime();

        Date d=new java.sql.Date(System.currentTimeMillis());

        if(!d.after(date)) return false;

        return true;
    }

    @Override
    public boolean checkIfWithdrawn(int studentId, int classId) {

        List<Enrollment> enrollments=enrollmentDao.getEnrollmentsByStudentId(studentId);

        if(enrollments==null) return false;

        for(Enrollment enrollment:enrollments){
            if(enrollment.getStatus().equals("withdraw")) return true;
        }

        return false;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public List<WebRegClass> getAllStatusClassesbyStudentId(int studentId) {
        return webRegClassDao.getAllStatusClassesByStudentId(studentId);
    }

    @Override
    public void createNewClass(WebRegClass theClass) {
        webRegClassDao.createNewClass(theClass);
    }

    @Override
    public List<WebRegClass> getAllClasses() {
        return webRegClassDao.getAllClasses();
    }

    @Override
    public void deactivateClassById(int classId) {
        webRegClassDao.deactivateClassById(classId);
    }

    @Override
    public void activateClassById(int classId) {
        webRegClassDao.activateClassById(classId);
    }

    @Override
    public void createApplicationByStudentClassId(int id, int classId) {
        applicationDAO.createApplicationByStudentClassId(id,classId);
    }

    @Override
    public List<Application> getApplicationsByStudentId(int id) {
        return applicationDAO.getApplicationByStudentId(id);
    }

    @Override
    public List<Application> getAllPendingApplications() {
        return applicationDAO.getPendingApplications();
    }

    @Override
    public void rejectOrApproveByIdFeedback(String status, int applicationId, String f) {
        applicationDAO.rejectOrApproveByIdFeedback(status,applicationId,f);
    }

    @Override
    public void activateStudentById(int studentId) {
        studentDao.activateStudentById(studentId);
    }

    @Override
    public void deactivateStudentById(int studentId) {
        studentDao.deactivateStudentById(studentId);
    }

    @Override
    public void passStudentByClassId(int studentId, int classId) {
        enrollmentDao.passStudentByClassId(studentId,classId);
    }

    @Override
    public void failStudentByClassId(int studentId, int classId) {
        enrollmentDao.failStudentByClassId(studentId,classId);
    }

    @Override
    public String getRequestByApplicationId(int applicationId) {
        return applicationDAO.getRequestByApplicationId(applicationId);
    }

    @Override
    public int getStudentIdByApplicationId(int applicationId) {
        return applicationDAO.getStudentIdByApplicationId(applicationId);
    }

    @Override
    public int getClassIdByApplicationId(int applicationId) {
        return applicationDAO.getClassIdByApplicationId(applicationId);
    }

    @Override
    public int getTotalPages(int studentId,int limit) {

            List<Enrollment> enrollments=getEnrollmentByStudentId(studentId);
            if(enrollments==null) return 0;
            if(enrollments.size()%limit==0) return enrollments.size()/limit;
            else return enrollments.size()/limit+1;

    }

    @Override
    public List<WebRegClass> getClassesbyStudentIdPageLimit(int id, int page, int limit) {
        return webRegClassDao.getWebRegClassesByStudentIdPageLimit(id,  page, limit);
    }

    @Override
    public int getTotalPagesAdmin(int limit) {
        return studentDao.getTotalPagesAdmin(limit);
    }

    @Override
    public List<Student> getStudentsByPageLimit(int page, int limit) {
        return studentDao.getStudentsByPageLimit(page,limit);
    }

    @Override
    public String getCourseNameById(int courseId) {
        return courseDao.getCourseNameById(courseId);
    }

    @Override
    public Lecture getLectureById(int lecture_id) {
        return lectureDao.getLectureById(lecture_id);
    }

    @Override
    public void withdrawApplication(int applicationId) {
        applicationDAO.withdrawApplication(applicationId);
    }

    @Override
    public Application getApplicationById(int applicationId) {
        return applicationDAO.getApplicationById(applicationId);
    }

    @Override
    public void resubmitApplication(int applicationId) {
        applicationDAO.resubmitApplication(applicationId);
    }

    @Override
    public List<Application> getAllApplications() {
        return null;
    }

    @Override
    public List<Enrollment> getEnrollmentByStudentId(int studentId) {
        return enrollmentDao.getEnrollmentsByStudentId(studentId);
    }

    @Override
    public WebRegClass getClassesbyStudentIdEnrollmentIdPageLimit(int studentId, int enrollmentId, int page, int limit) {
        return webRegClassDao.getClassesbyStudentIdEnrollmentIdPageLimit(studentId,enrollmentId,page,limit);
    }

    @Override
    public List<Enrollment> getEnrollmentByStudentIdPageLimit(int id, int page, int limit) {
        List<Enrollment> enrollments=getEnrollmentByStudentId(id);
        System.out.print(enrollments.size());
        return enrollments.subList((page-1)*limit,Math.min((page)*limit,enrollments.size()));
    }

    @Override
    public WebRegClass getClassesbyId(int class_id) {

        return webRegClassDao.getWebRegClassById(class_id);
    }


    @Override
    public void dropEnrollments(int studentId, int classId) {
        enrollmentDao.dropEnrollment(studentId,classId);
    }

    @Override
    public void withdrawEnrollment(int studentId, int classId) {



        enrollmentDao.withdrawEnrollment(studentId,classId);
    }

    @Override
    public void createEnrollmentApplication(int studentId, int classId) {
       applicationDAO.createEnrollmentApplication(studentId,classId);
    }

    @Override
    public void addEnrollment(int studentId, int classId) {
            enrollmentDao.addEnrollment(studentId, classId);
    }


}
