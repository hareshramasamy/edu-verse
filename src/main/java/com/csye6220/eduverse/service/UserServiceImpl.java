package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.UserDAO;
import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.mapper.UserMapper;
import com.csye6220.eduverse.pojo.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public UserDTO searchByUserName(String username) {
        return userMapper.mapUserToDTO(userDAO.searchByUserName(username));
    }

    @Override
    public UserDTO searchByEmail(String email) {
        return userMapper.mapUserToDTO(userDAO.searchByEmail(email));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String name) {
        User user = userDAO.searchByUserName(name);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        User updateUser = userDAO.updateUser(user);
        return userMapper.mapUserToDTO(updateUser);
    }

    @Override
    public List<String> getEmailIdsByDepartment(Long courseOfferingId) {
        return userDAO.getUsersByDepartment(courseOfferingId);
    }
}
