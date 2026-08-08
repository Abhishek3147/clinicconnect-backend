package com.clinicconnect.service;

import com.clinicconnect.entity.User;
import com.clinicconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Integer id, User user) {
        User existing = userRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setFullName(user.getFullName());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());
            existing.setRole(user.getRole());

            return userRepository.save(existing);
        }

        return null;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}