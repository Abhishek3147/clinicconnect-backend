package com.clinicconnect.controller;

import java.util.Map;
import java.util.HashMap;

import com.clinicconnect.dto.LoginRequest;
import com.clinicconnect.dto.LoginResponse;
import com.clinicconnect.dto.RegisterRequest;
import com.clinicconnect.entity.User;
import com.clinicconnect.repository.UserRepository;
import com.clinicconnect.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER =================

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            return "Email already exists";
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        try{
             System.out.println("Before Authenticate");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

                        System.out.println("After Authenticate");

                         User user = userRepository.findByEmail(request.getEmail());

                          if (user == null){
                            throw new RuntimeException("User not found");

                          }
                           
        String token = jwtService.generateToken(user);

        return new LoginResponse(token);

        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        
    }

    // ================= FORGOT PASSWORD =================

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        otpStorage.put(email, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);

        return ResponseEntity.ok("OTP sent successfully");
    }

    // ================= RESET PASSWORD =================

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (!otpStorage.containsKey(email)) {
            return ResponseEntity.badRequest().body("OTP expired");
        }

        if (!otp.equals(otpStorage.get(email))) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        otpStorage.remove(email);

        return ResponseEntity.ok("Password reset successfully");
    }
}