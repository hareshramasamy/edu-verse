package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.User;

public interface UserService {
    void saveUser(User user);
    User searchByUserName(String username);
    User searchByEmail(String email);
}
