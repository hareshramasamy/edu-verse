package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.TAAssignment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TAAssignmentsDAO extends DAO {
    public List<TAAssignment> getTAAssignmentsByStudent(String username) {
        begin();
        String hql = "FROM TAAssignment t WHERE t.student.user.username = :username";
        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);

        List<TAAssignment> taAssignments = query.list();
        System.out.println(taAssignments);
        commit();
        return taAssignments;
    }
}
