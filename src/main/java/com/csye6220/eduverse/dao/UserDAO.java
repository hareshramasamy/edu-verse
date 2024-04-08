package com.csye6220.eduverse.dao;

import java.util.Objects;

import com.csye6220.eduverse.entity.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
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
    }

    public User searchByUserName(String username) {
        begin();

        String hql = "FROM User u WHERE u.username = :username";
        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);

        User userResult = (User) query.uniqueResult();
        System.out.println(Objects.nonNull(userResult)?userResult.getUsername(): "User is not found");
        close();
        return userResult;
    }

    public User searchByEmail(String email) {
        begin();

        String hql = "FROM User u WHERE u.email = :email";
        Query query = getSession().createQuery(hql);
        query.setParameter("email", email);

        User userResult = (User) query.uniqueResult();
        System.out.println(Objects.nonNull(userResult)?userResult.getUsername(): "User is not found");
        close();
        return userResult;
    }
}

