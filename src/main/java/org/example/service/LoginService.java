package org.example.service;

import org.example.dao.StudentDao;
import org.example.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final StudentDao studentDao;

    @Autowired
    public LoginService(StudentDao studentDao) {this.studentDao = studentDao; }

    public Optional<Student> validateLogin(String username, String password) {
        return studentDao.validateLogin(username, password);
    }

}
