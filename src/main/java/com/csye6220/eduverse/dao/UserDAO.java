package com.csye6220.eduverse.dao;

import java.util.List;
import java.util.Objects;

import com.csye6220.eduverse.entity.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kai
 */

@Repository
public class UserDAO extends DAO {

    public void saveUser(User user) {
        begin();
        getSession().persist(user);
        commit();
        close();
    }

    public User searchByUserName(String username) {
        begin();

        String hql = "FROM User u WHERE u.username = :username";
        Query<User> query = getSession().createQuery(hql, User.class);
        query.setParameter("username", username);

        User userResult = query.uniqueResult();
        System.out.println(Objects.nonNull(userResult)?userResult.getUsername(): "User is not found");
        close();
        return userResult;
    }

    public User searchByEmail(String email) {
        begin();

        String hql = "FROM User u WHERE u.email = :email";
        Query<User> query = getSession().createQuery(hql, User.class);
        query.setParameter("email", email);

        User userResult = query.uniqueResult();
        System.out.println(Objects.nonNull(userResult)?userResult.getUsername(): "User is not found");
        close();
        return userResult;
    }

    public User updateUser(User user) {
        begin();
        User updatedUser = getSession().merge(user);
        System.out.println(user);
        commit();
        close();
        return updatedUser;
    }

    public List<String> getUsersByDepartment(Long courseOfferingId) {
        begin();
        String hql = "SELECT s.user.email FROM Student s WHERE s.department.id = (SELECT co.course.department.id from CourseOffering co where co.id = :courseOfferingId)";
        Query<String> query = getSession().createQuery(hql, String.class);
        query.setParameter("courseOfferingId", courseOfferingId);
        List<String> userResultList = query.list();
        System.out.println(Objects.nonNull(userResultList)? userResultList : "User is not found");
        close();
        return userResultList;
    }
}

