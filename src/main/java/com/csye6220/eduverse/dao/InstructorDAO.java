package com.csye6220.eduverse.dao;

import com.csye6220.eduverse.entity.Instructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class InstructorDAO extends DAO {
    public void saveInstructor(Instructor instructor) {
        begin();
        getSession().persist(instructor);
        commit();
    }

    public Instructor getInstructorByUsername(String username) {
        begin();
        String hql = "FROM Instructor i WHERE i.user.username = :username";
        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);
        Instructor instructorResult = (Instructor) query.uniqueResult();
        System.out.println(Objects.nonNull(instructorResult)?instructorResult.getUser().getUsername(): "Instructor is not found");
        close();
        return instructorResult;
    }
}
