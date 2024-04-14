package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Assignment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssignmentDAO extends DAO {
    public List<Assignment> getAssignmentsByCourseOfferingId(Long courseOfferingId) {
        begin();

        String hql = "FROM Assignment a WHERE a.courseOffering.id = :courseOfferingId";
        Query<Assignment> query = getSession().createQuery(hql, Assignment.class);
        query.setParameter("courseOfferingId", courseOfferingId);

        List<Assignment> assignments = query.list();
        System.out.println(assignments);
        close();
        return assignments;
    }

    public void createAssignment(Assignment assignment) {
        begin();
        getSession().persist(assignment);
        commit();
        close();
    }

    public Assignment getAssignmentById(Long assignmentId) {
        begin();

        String hql = "FROM Assignment a WHERE a.id = :assignmentId";
        Query<Assignment> query = getSession().createQuery(hql, Assignment.class);
        query.setParameter("assignmentId", assignmentId);

        Assignment assignment = query.uniqueResult();
        System.out.println(assignment);
        close();
        return assignment;
    }

    public Assignment editAssignment(Assignment assignment) {
        begin();
        Assignment updatedAssignment = getSession().merge(assignment);
        System.out.println(assignment);
        commit();
        close();
        return updatedAssignment;
    }

    public void deleteAssignment(Assignment assignment) {
        begin();
        getSession().remove(assignment);
        commit();
        close();
    }
}
