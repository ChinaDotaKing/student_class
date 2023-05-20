package org.example.controller;

import lombok.Setter;
import org.example.domain.*;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Controller
@RequestMapping("/")
public class StudentHomePageController {
    @Autowired
    UserService userService;


    @GetMapping("/home/{page}/{limit}")
    public String home(@PathVariable int page,@PathVariable int limit, HttpServletRequest request) {
        Student student= (Student) request.getSession(false).getAttribute("student");

        HttpSession newSession= request.getSession(true);
        List<Enrollment> enrollments=userService.getEnrollmentByStudentIdPageLimit(student.getId(),page,limit);
        List<WebRegClass> classes=null;


        List<Course> courses=null;
        List<Professor> professors=null;
        List<Semester> semesters=null;
        List<Department> departments=null;
        if(!(enrollments ==null))
        {  classes= enrollments.stream().map(c->userService.getClassesbyId(c.getClass_id())).collect(Collectors.toList());
            courses=classes.stream().map(c->userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
        professors=classes.stream().map(c->userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
        semesters=classes.stream().map(c->userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
        departments=professors.stream().map(c->userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
        }


        //page=1;
        int totalPages=userService.getTotalPages(student.getId(),limit);
        System.out.print(totalPages);

        newSession.setAttribute("totalPages",totalPages);
        //newSession.setAttribute("userService",userService);
        newSession.setAttribute("limit",limit);
        newSession.setAttribute("page",page);
        newSession.setAttribute("classes",classes);
        newSession.setAttribute("enrollments",enrollments);
        newSession.setAttribute("courses",courses);
        newSession.setAttribute("professors",professors);
        newSession.setAttribute("semesters",semesters);
        newSession.setAttribute("departments",departments);


        return "home";
    }


    @GetMapping("/class/all")
    public String class_management_Page(HttpServletRequest request) {

        //get current session
        HttpSession curSession = request.getSession(false);
        Student student = (Student) curSession.getAttribute("student");

        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("student",student);

//        System.out.print(userService.checkIfStudentPassCourseById(student,2));
//        System.out.print(student.getId());
        List<WebRegClass> classes = userService.getAllActiveClasses();
        try {
            List<Course> courses = classes.stream().map(c -> userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
            List<Professor> professors = classes.stream().map(c -> userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
            List<Semester> semesters = classes.stream().map(c -> userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
            List<Department> departments = professors.stream().map(c -> userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
            List<Integer> en_num = classes.stream().map(c -> userService.getActiveEnrollmentsByClassId(c.getId()) == null ? 0 : userService.getActiveEnrollmentsByClassId(c.getId()).size()).collect(Collectors.toList());

            List<String> en_status = classes.stream().map(c -> userService.getEnrollmentStatus(student.getId(), c.getId()) ? "YES" : "NO").collect(Collectors.toList());


            newSession.setAttribute("classes", classes);
            newSession.setAttribute("courses", courses);
            newSession.setAttribute("professors", professors);
            newSession.setAttribute("semesters", semesters);

            newSession.setAttribute("departments", departments);
            newSession.setAttribute("en_num", en_num);
            newSession.setAttribute("en_status", en_status==null? null:en_status);
            newSession.setAttribute("userService",userService);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return "class_management";
    }


    @GetMapping("/class/{classId}")
    public String classPage(@PathVariable int classId,
                            HttpServletRequest request) {

        Optional<WebRegClass> theClass = Optional.ofNullable(userService.getWebRegClassById(classId));
        if (theClass.isPresent()) {
            HttpSession oldSession = request.getSession(false);
            // invalidate old session if it exists
//            if (oldSession != null) oldSession.invalidate();

            // generate new session
//            HttpSession newSession = request.getSession(true);
        try{
            WebRegClass theWebRegClass = theClass.get();


            Course course = userService.getCourseById(theWebRegClass.getCourse_id());
            List<Enrollment> enrollments = userService.getActiveEnrollmentsByClassId(theWebRegClass.getId());
            Department department = userService.getDepartmentById(course.getDept_id());

            Lecture lecture = userService.getLectureById(theWebRegClass.getLecture_id());

            Professor professor = userService.getProfessorById(theWebRegClass.getProfessor_id());
            Department pro_dept = userService.getDepartmentById(professor.getDept_id());
            // store user details in session
            Classroom classroom = userService.getClassroomById(theWebRegClass.getClassroom_id());
            List<Prerequisite> prerequisites = userService.getPrerequisitesByCourseId(course.getId());
            List<Enrollment> enrollments1=userService.getActiveEnrollmentsByClassId(classId);

            List<Student> students=null;
            List<Department> departments=null;
            Semester semester=userService.getSemesterById(theClass.get().getSemester_id());
            if(enrollments1!=null){
                students=enrollments1.stream().map(c->userService.getStudentById(c.getStudent_id())).collect(Collectors.toList());
                departments=students.stream().map(c->userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());

            }
            oldSession.setAttribute("semester", semester);
            oldSession.setAttribute("theClass", theClass.get());
            oldSession.setAttribute("course", course);
            oldSession.setAttribute("department", department);
            oldSession.setAttribute("enrollments_num", enrollments == null ? 0 : enrollments.size());
            oldSession.setAttribute("pro_dept", pro_dept);
            oldSession.setAttribute("professor", professor);
            oldSession.setAttribute("lecture", lecture);
            oldSession.setAttribute("classroom", classroom);
            oldSession.setAttribute("prerequisites", prerequisites);
            oldSession.setAttribute("userService", userService);
            oldSession.setAttribute("students", students);
            oldSession.setAttribute("departments", departments);
            oldSession.setAttribute("enrollments1", enrollments1==null?null:enrollments1);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        }
        return "student_class";


    }

    @PostMapping("/class/{classId}")
    public String classPagePost(@PathVariable int classId,
                            HttpServletRequest request) {
        String button ;
        Student student;
        WebRegClass theClass;


        button=request.getParameter("button");
        student= (Student) request.getSession(false).getAttribute("student");
        theClass=userService.getWebRegClassById(classId);

        if (button.equals("add")) {
            if(!userService.checkIfStudentEligible(student,theClass)) return "Not_eligible_forenrollment";

            if(!userService.checkIfClassIsFull(theClass)) {
            userService.addEnrollment(student.getId(),theClass.getId());

                HttpSession curSession = request.getSession(false);


                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("student", student);

//        System.out.print(userService.checkIfStudentPassCourseById(student,2));
//        System.out.print(student.getId());
                List<WebRegClass> classes = userService.getAllActiveClasses();
                try {
                    List<Course> courses = classes.stream().map(c -> userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
                    List<Professor> professors = classes.stream().map(c -> userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
                    List<Semester> semesters = classes.stream().map(c -> userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
                    List<Department> departments = professors.stream().map(c -> userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
                    List<Integer> en_num = classes.stream().map(c -> userService.getActiveEnrollmentsByClassId(c.getId()) == null ? 0 : userService.getActiveEnrollmentsByClassId(c.getId()).size()).collect(Collectors.toList());

                    List<String> en_status = classes.stream().map(c -> userService.getEnrollmentStatus(student.getId(), c.getId()) ? "YES" : "NO").collect(Collectors.toList());


                    newSession.setAttribute("classes", classes);
                    newSession.setAttribute("courses", courses);
                    newSession.setAttribute("professors", professors);
                    newSession.setAttribute("semesters", semesters);

                    newSession.setAttribute("departments", departments);
                    newSession.setAttribute("en_num", en_num);
                    newSession.setAttribute("en_status", en_status == null ? null : en_status);
                    newSession.setAttribute("userService", userService);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return "class_management";
            }
            else
                button = "apply";

        }
        else if (button.equals("drop")) {
            if(!userService.checkIfDroppable(student.getId(),theClass)) return "undroppable";
            else {
                userService.dropEnrollments(student.getId(),classId);
                HttpSession curSession = request.getSession(false);


                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("student",student);

//        System.out.print(userService.checkIfStudentPassCourseById(student,2));
//        System.out.print(student.getId());
                List<WebRegClass> classes = userService.getAllActiveClasses();
                try {
                    List<Course> courses = classes.stream().map(c -> userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
                    List<Professor> professors = classes.stream().map(c -> userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
                    List<Semester> semesters = classes.stream().map(c -> userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
                    List<Department> departments = professors.stream().map(c -> userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
                    List<Integer> en_num = classes.stream().map(c -> userService.getActiveEnrollmentsByClassId(c.getId()) == null ? 0 : userService.getActiveEnrollmentsByClassId(c.getId()).size()).collect(Collectors.toList());

                    List<String> en_status = classes.stream().map(c -> userService.getEnrollmentStatus(student.getId(), c.getId()) ? "YES" : "NO").collect(Collectors.toList());


                    newSession.setAttribute("classes", classes);
                    newSession.setAttribute("courses", courses);
                    newSession.setAttribute("professors", professors);
                    newSession.setAttribute("semesters", semesters);

                    newSession.setAttribute("departments", departments);
                    newSession.setAttribute("en_num", en_num);
                    newSession.setAttribute("en_status", en_status==null? null:en_status);
                    newSession.setAttribute("userService",userService);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
                return "class_management";
            }
        }
        else if (button.equals("withdraw")) {
            if(!userService.checkIfWithdrawable(student.getId(),theClass)) return "unwithdrawable";
            else {
                userService.createEnrollmentApplication(student.getId(),theClass.getId());

                HttpSession curSession = request.getSession(false);


                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("student",student);

//        System.out.print(userService.checkIfStudentPassCourseById(student,2));
//        System.out.print(student.getId());
                List<WebRegClass> classes = userService.getAllActiveClasses();
                try {
                    List<Course> courses = classes.stream().map(c -> userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
                    List<Professor> professors = classes.stream().map(c -> userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
                    List<Semester> semesters = classes.stream().map(c -> userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
                    List<Department> departments = professors.stream().map(c -> userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
                    List<Integer> en_num = classes.stream().map(c -> userService.getActiveEnrollmentsByClassId(c.getId()) == null ? 0 : userService.getActiveEnrollmentsByClassId(c.getId()).size()).collect(Collectors.toList());

                    List<String> en_status = classes.stream().map(c -> userService.getEnrollmentStatus(student.getId(), c.getId()) ? "YES" : "NO").collect(Collectors.toList());


                    newSession.setAttribute("classes", classes);
                    newSession.setAttribute("courses", courses);
                    newSession.setAttribute("professors", professors);
                    newSession.setAttribute("semesters", semesters);

                    newSession.setAttribute("departments", departments);
                    newSession.setAttribute("en_num", en_num);
                    newSession.setAttribute("en_status", en_status==null? null:en_status);
                    newSession.setAttribute("userService",userService);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
                return "class_management";
            }
        }
        if (button.equals("apply")) {

                userService.createApplicationByStudentClassId(student.getId(),classId);
                return "success";

        }

        return button;
    }


    @GetMapping("/application/all")
    public String applicationManagement(
                                        HttpServletRequest request) {
        HttpSession curSession = request.getSession(false);
        Student student = (Student) curSession.getAttribute("student");

        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("student",student);

        List<Application> applications= userService.getApplicationsByStudentId(student.getId());
        List<Course> courses=null;
        List<Professor> professors=null;
        List<Semester> semesters=null;
        List<Department> departments=null;
        List<WebRegClass> classes=null;
                        List<Student> students=null;
        if(!(applications ==null))
        {
            classes=applications.stream().map(c->userService.getWebRegClassById(c.getClass_id())).collect(Collectors.toList());

            students=applications.stream().map(c->userService.getStudentById(c.getStudent_id())).collect(Collectors.toList());

            courses=classes.stream().map(c->userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
            semesters=classes.stream().map(c->userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
            professors=classes.stream().map(c->userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
            departments=professors.stream().map(c->userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
        }
          String s1="Pending";
        String s2="Reject";
        String s3="Withdraw";
        newSession.setAttribute("s1",s1);
        newSession.setAttribute("s2",s2);
        newSession.setAttribute("s3",s3);
        newSession.setAttribute("applications",applications);
        newSession.setAttribute("students",students);
        newSession.setAttribute("classes",classes);
        newSession.setAttribute("courses",courses);
        newSession.setAttribute("semesters",semesters);
        newSession.setAttribute("departments",departments);



        return "application_management";

    }


    @PostMapping("/application/{applicationId}")
    public String applicationdelete( @PathVariable int applicationId, HttpServletRequest request){

        HttpSession curSession = request.getSession(false);
        Student student = (Student) curSession.getAttribute("student");
        String  button=request.getParameter("button");
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("student",student);


        if(button.equals("cancel")) {
            if (!(student.getId() == userService.getApplicationById(applicationId).getStudent_id())) return "login";
            else userService.withdrawApplication(applicationId);
        }

        else if(button.equals("resubmit")){
            if (!(student.getId() == userService.getApplicationById(applicationId).getStudent_id())) return "login";
            else userService.resubmitApplication(applicationId);
        }
        List<Application> applications= userService.getApplicationsByStudentId(student.getId());
        List<Course> courses=null;
        List<Professor> professors=null;
        List<Semester> semesters=null;
        List<Department> departments=null;
        List<WebRegClass> classes=null;
        List<Student> students=null;
        if(!(applications ==null))
        {
            classes=applications.stream().map(c->userService.getWebRegClassById(c.getClass_id())).collect(Collectors.toList());

            students=applications.stream().map(c->userService.getStudentById(c.getStudent_id())).collect(Collectors.toList());

            courses=classes.stream().map(c->userService.getCourseById(c.getCourse_id())).collect(Collectors.toList());
            semesters=classes.stream().map(c->userService.getSemesterById(c.getSemester_id())).collect(Collectors.toList());
            professors=classes.stream().map(c->userService.getProfessorById(c.getProfessor_id())).collect(Collectors.toList());
            departments=professors.stream().map(c->userService.getDepartmentById(c.getDept_id())).collect(Collectors.toList());
        }
        String s1="Pending";
        String s2="Reject";
        String s3="Withdraw";
        newSession.setAttribute("s1",s1);
        newSession.setAttribute("s2",s2);
        newSession.setAttribute("s3",s3);
        newSession.setAttribute("applications",applications);
        newSession.setAttribute("students",students);
        newSession.setAttribute("classes",classes);
        newSession.setAttribute("courses",courses);
        newSession.setAttribute("semesters",semesters);
        newSession.setAttribute("departments",departments);



        return "application_management";
    }

}
