package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.pojo.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
    public UserDTO mapUserToDTO(User user) {
        if(Objects.nonNull(user)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            return userDTO;
        }
        return null;
    }
}
