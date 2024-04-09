package com.csye6220.eduverse.dao;

import java.util.Objects;

import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.pojo.UserDTO;
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
}

