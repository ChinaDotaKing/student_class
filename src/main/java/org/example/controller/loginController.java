package org.example.controller;


import lombok.Setter;
import org.example.domain.*;
import org.example.service.LoginService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Controller
@RequestMapping("/")
public class loginController {


    private final LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public loginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor =new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        HttpSession newSession= request.getSession(true);

        return "login";
    }
    // validate that we are always getting a new session after login
    @PostMapping("/login")
    public String postLogin(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request) {
        Optional<Student> possibleStudent = loginService.validateLogin(username, password);
        if(possibleStudent.isPresent()) {
//            Student admin=new Student("admin","admin","admin@gmail.com","admin","",1,true);
//            userService.createNewStudent(admin);
            HttpSession oldSession = request.getSession(false);
            // invalidate old session if it exists
            if (oldSession != null) oldSession.invalidate();

            // generate new session
            HttpSession newSession = request.getSession(true);

            // store user details in session
            newSession.setAttribute("student", possibleStudent.get());
            int limit=5;
            int page=1;
//            System.out.print(possibleStudent.get().is_admin());
           // newSession.setAttribute("professors",classes);
            if(!possibleStudent.get().is_admin()) {

                Student student= (Student) request.getSession(false).getAttribute("student");

                newSession= request.getSession(true);
                List<Enrollment> enrollments=userService.getEnrollmentByStudentId(student.getId());
                List<WebRegClass> classes=null;


                List<Course> courses=null;
                List<Professor> professors=null;
                List<Semester> semesters=null;
                List<Department> departments=null;
                if(!(enrollments ==null))
                {  classes= enrollments.stream().map(c->userService.getClassesbyStudentIdEnrollmentIdPageLimit(student.getId(),c.getId(),page,limit)).collect(Collectors.toList());
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
            else {

                if(!((Student)request.getSession(false).getAttribute("student")).is_admin() ) return "login";
                Student student= (Student) request.getSession(false).getAttribute("student");
                HttpSession curSession=request.getSession(true);


                int totalPages=userService.getTotalPagesAdmin(limit);

                List<Student> students=userService.getStudentsByPageLimit(page,limit);
                List<Department> departments=students.stream().map(c->userService.getDepartmentById(c.getId())).collect(Collectors.toList());

                curSession.setAttribute("totalPages", totalPages);
                curSession.setAttribute("limit",limit);
                curSession.setAttribute("page", page);
                curSession.setAttribute("student", student);
                curSession.setAttribute("students", students);
                curSession.setAttribute("departments", departments);

                return "admin_home";

            }
        } else {
            return "login";
        }
    }


    @GetMapping("/signup")
    public String signup(Model theModel) {

        theModel.addAttribute("student", new Student());
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("student") Student theStudent, BindingResult theBindingResult) {
//        if(!theBindingResult.getRawFieldValue("password").equals(theBindingResult.getRawFieldValue("repeat password")))
//            return "signup";

        if(theBindingResult.getRawFieldValue("username").toString().equalsIgnoreCase("admin") ) return "signup";

        String pass_word=bCryptPasswordEncoder.encode( theBindingResult.getRawFieldValue("password").toString());

        userService.createNewStudent(new Student((String) theBindingResult.getRawFieldValue("username"),
                pass_word,theBindingResult.getRawFieldValue("email").toString()
                ,theBindingResult.getRawFieldValue("firstName").toString()
                ,theBindingResult.getRawFieldValue("lastName").toString()
                ,Integer.parseInt(theBindingResult.getRawFieldValue("dept_id").toString())
        ));


        System.out.println("Binding result: " + theBindingResult.getRawFieldValue("password") );


        if(theBindingResult.hasErrors()) return
                "signup";
        else return "login";
    }


    @GetMapping("/signout")
    public String signout(Model theModel,HttpServletRequest request) {

        HttpSession newSession=request.getSession(true);
        System.out.print(request.getRequestURI());
        return "login";
    }

}
