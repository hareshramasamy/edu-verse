package com.csye6220.eduverse.security;

import com.csye6220.eduverse.dao.UserDAO;
import com.csye6220.eduverse.entity.Role;
import com.csye6220.eduverse.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.searchByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(getRoles(user))
                    .build();
    }

    private String[] getRoles(User user) {
        if (Role.INSTRUCTOR.equals(user.getRole())) {
            return new String[]{"INSTRUCTOR"};
        } else if (Role.STUDENT.equals(user.getRole())) {
            return new String[]{"STUDENT"};
        }
        return new String[]{"STUDENT"};
    }
}
