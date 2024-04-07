package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.UserDAO;
import com.csye6220.eduverse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public User searchByUserName(String username) {
        return userDAO.searchByUserName(username);
    }

    @Override
    public User searchByEmail(String email) {
        return userDAO.searchByEmail(email);
    }
}
