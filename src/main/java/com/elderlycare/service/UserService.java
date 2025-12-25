package com.elderlycare.service;

import com.elderlycare.entity.User;
import com.elderlycare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    public boolean validateUser(String username, String password) {
        User user = findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
    
    public User createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("111111");
            admin.setRole("ADMIN");
            return userRepository.save(admin);
        }
        return findByUsername("admin");
    }
}