package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Enrollment;
import com.csye6220.eduverse.entity.Student;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentDAO extends DAO {

    public List<Enrollment> getEnrollmentsByStudent(String username) {
        begin();
        String hql = "FROM Enrollment e WHERE e.student.user.username = :username";
        Query<Enrollment> query = getSession().createQuery(hql, Enrollment.class);
        query.setParameter("username", username);

        List<Enrollment> enrollments = query.list();
        System.out.println(enrollments);
        close();
        return enrollments;
    }

    public boolean checkCurrentCourseEnrollment(Long courseOfferingId, String username) {
        begin();
        String hql = "SELECT COUNT(e) FROM Enrollment e WHERE e.courseOffering.id = :courseOfferingId AND e.student.user.username = :username";
        Query<Long> query = getSession().createQuery(hql, Long.class);
        query.setParameter("courseOfferingId", courseOfferingId);
        query.setParameter("username", username);

        Long count = query.getSingleResult();
        close();
        return count != 0;
    }

    public boolean checkCurrentCourseEnrollmentByEmail(Long courseOfferingId, String email) {
        begin();
        String hql = "SELECT COUNT(e) FROM Enrollment e WHERE e.courseOffering.id = :courseOfferingId AND e.student.user.email = :email";
        Query<Long> query = getSession().createQuery(hql, Long.class);
        query.setParameter("courseOfferingId", courseOfferingId);
        query.setParameter("email", email);

        Long count = query.getSingleResult();
        close();
        return count != 0;
    }

    public List<Enrollment> getEnrollmentsByCourseOfferingId(Long courseOfferingId, int offset) {
        begin();
        String hql = "FROM Enrollment e WHERE e.courseOffering.id = :courseOfferingId";
        Query<Enrollment> query = getSession().createQuery(hql, Enrollment.class);
        query.setParameter("courseOfferingId", courseOfferingId);
        query.setFirstResult(offset); // Offset for additional batch (starting from a specified index)
        query.setMaxResults(20);
        List<Enrollment> enrollments = query.list();
        System.out.println(enrollments);
        close();
        return enrollments;
    }

    public void createEnrollment(Enrollment enrollment) {
        begin();
        getSession().persist(enrollment);
        commit();
        close();
    }

    public List<Student> getStudentsByAssignmentId(Long assignmentId) {
        begin();
        String hql = "SELECT e.student FROM Enrollment e WHERE e.courseOffering.id = (SELECT a.courseOffering.id FROM Assignment a where a.id = :assignmentId)";
        Query<Student> query = getSession().createQuery(hql, Student.class);
        query.setParameter("assignmentId", assignmentId);
        List<Student> students = query.list();
        System.out.println(students);
        close();
        return students;

    }
}

