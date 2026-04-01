package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    // ✅ SIGNUP
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {

        // 🔥 Default role
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    // ✅ LOGIN (JWT + ROLE)
    @PostMapping("/login")
    public Object login(@RequestBody User user) {

        // 🔐 Validate user
        User existing = authService.login(user.getUsername(), user.getPassword());

        // 🔐 Generate JWT token
        String token = JwtUtil.generateToken(existing.getUsername());

        // 🔥 Return token + role
        return Map.of(
                "token", token,
                "role", existing.getRole()
        );
    }

    // ✅ FORGOT PASSWORD
    @PostMapping("/forgot-password")
    public String resetPassword(@RequestBody User user) {

        User existing = userRepository.findByUsername(user.getUsername());

        if (existing == null) {
            return "User not found";
        }

        existing.setPassword(user.getPassword());
        userRepository.save(existing);

        return "Password updated successfully";
    }

    // ✅ GET ALL USERS (ADMIN USE)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}