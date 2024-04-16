package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Announcement;
import com.csye6220.eduverse.entity.Grade;
import com.csye6220.eduverse.entity.StudentAssignment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GradeDAO extends DAO {
    public void createSubmission(Grade grade) {
        begin();
        getSession().persist(grade);
        commit();
        close();
    }

    public Grade getGradeByStudentAndAssignment(Long studentId, Long assignmentId) {
        begin();
        String hql = "FROM Grade g WHERE g.studentAssignment.student.id = :studentId AND g.studentAssignment.assignment.id = :assignmentId";
        Query<Grade> query = getSession().createQuery(hql, Grade.class);
        query.setParameter("studentId", studentId);
        query.setParameter("assignmentId", assignmentId);
        Grade grade = query.uniqueResult();
        System.out.println(grade);
        close();
        return grade;
    }

    public Grade getGradeByStudentNameAndAssignment(String name, Long assignmentId) {
        begin();
        String hql = "FROM Grade g WHERE g.studentAssignment.student.user.username = :name AND g.studentAssignment.assignment.id = :assignmentId";
        Query<Grade> query = getSession().createQuery(hql, Grade.class);
        query.setParameter("name", name);
        query.setParameter("assignmentId", assignmentId);
        Grade grade = query.uniqueResult();
        System.out.println(grade);
        close();
        return grade;
    }
}
