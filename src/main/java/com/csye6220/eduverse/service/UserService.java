package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.pojo.UserDTO;

public interface UserService {
    void saveUser(User user);
    UserDTO searchByUserName(String username);
    UserDTO searchByEmail(String email);
    UserDTO updateUser(UserDTO userDTO, String name);
}