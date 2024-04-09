package com.csye6220.eduverse.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollmentList;

    @OneToMany(mappedBy = "student",  cascade = CascadeType.ALL)
    private List<TAAssignment> taAssignmentList;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Student() {
    }

    public Student(Long id, User user, List<Enrollment> enrollmentList, List<TAAssignment> taAssignmentList, Department department) {
        this.id = id;
        this.user = user;
        this.enrollmentList = enrollmentList;
        this.taAssignmentList = taAssignmentList;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }

    public void setEnrollmentList(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    public List<TAAssignment> getTaAssignmentList() {
        return taAssignmentList;
    }

    public void setTaAssignmentList(List<TAAssignment> taAssignmentList) {
        this.taAssignmentList = taAssignmentList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
