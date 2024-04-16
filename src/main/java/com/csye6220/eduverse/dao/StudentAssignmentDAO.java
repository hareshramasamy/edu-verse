package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.StudentAssignment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentAssignmentDAO extends DAO{
    public void saveStudentAssignment(StudentAssignment studentAssignment) {
        begin();
        getSession().persist(studentAssignment);
        commit();
        close();
    }

    public StudentAssignment getStudentAssignmentByid(Long studentAssignmentId) {
        begin();

        String hql = "FROM StudentAssignment s WHERE s.id = :studentAssignmentId";
        Query<StudentAssignment> query = getSession().createQuery(hql, StudentAssignment.class);
        query.setParameter("studentAssignmentId", studentAssignmentId);

        StudentAssignment studentAssignment = query.uniqueResult();
        System.out.println(studentAssignment);

        close();
        return studentAssignment;
    }

    public StudentAssignment getStudentAssignmentByStudentAndAssignment(Long assignmentId, String name) {
        begin();

        String hql = "FROM StudentAssignment s WHERE s.assignment.id = :assignmentId AND s.student.user.username = :name";
        Query<StudentAssignment> query = getSession().createQuery(hql, StudentAssignment.class);
        query.setParameter("assignmentId", assignmentId);
        query.setParameter("name", name);

        StudentAssignment studentAssignment = query.uniqueResult();
        System.out.println(studentAssignment);

        close();
        return studentAssignment;
    }

    public List<StudentAssignment> getStudentAssignmentsByAssignmentId(Long assignmentId) {
        begin();

        String hql = "FROM StudentAssignment s WHERE s.assignment.id = :assignmentId";
        Query<StudentAssignment> query = getSession().createQuery(hql, StudentAssignment.class);
        query.setParameter("assignmentId", assignmentId);
        List<StudentAssignment> studentAssignment = query.list();
        System.out.println(studentAssignment);

        close();
        return studentAssignment;

    }
}
