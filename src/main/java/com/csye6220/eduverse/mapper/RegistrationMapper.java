package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.InstructorDAO;
import com.csye6220.eduverse.dao.StudentDAO;
import com.csye6220.eduverse.entity.*;
import com.csye6220.eduverse.dao.DepartmentDAO;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import com.csye6220.eduverse.pojo.TestRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    PasswordEncoder passwordEncoder;

    DepartmentDAO departmentDAO;

    StudentDAO studentDAO;

    InstructorDAO instructorDAO;

    @Autowired
    public RegistrationMapper(PasswordEncoder passwordEncoder, DepartmentDAO departmentDAO, StudentDAO studentDAO, InstructorDAO instructorDAO) {
        this.passwordEncoder = passwordEncoder;
        this.departmentDAO = departmentDAO;
        this.studentDAO = studentDAO;
        this.instructorDAO = instructorDAO;
    }

    public void mapRegistrationToStudent(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRole(Role.STUDENT);
        Department department = departmentDAO.getDepartmentById(registrationDTO.getDepartmentId());
        Student student = new Student();
        student.setDepartment(department);
        student.setUser(user);
        studentDAO.saveStudent(student);
    }

    public void mapRegistrationToInstructor(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        Department department = departmentDAO.getDepartmentById(registrationDTO.getDepartmentId());
        user.setRole(Role.INSTRUCTOR);
        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setDepartment(department);
        System.out.println(instructor);
        instructorDAO.saveInstructor(instructor);
    }

    public void mapTestRegistrationToStudent(TestRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRole(Role.STUDENT);
        Department department = departmentDAO.getDepartmentById(registrationDTO.getDepartmentId());
        Student student = new Student();
        student.setDepartment(department);
        student.setUser(user);
        studentDAO.saveStudent(student);
    }

    public void mapTestRegistrationToInstructor(TestRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        Department department = departmentDAO.getDepartmentById(registrationDTO.getDepartmentId());
        user.setRole(Role.INSTRUCTOR);
        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setDepartment(department);
        System.out.println(instructor);
        instructorDAO.saveInstructor(instructor);
    }
}
