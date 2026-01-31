package com.example.MEDICINE.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.MEDICINE.Model.Role;
import com.example.MEDICINE.Model.User;
import com.example.MEDICINE.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private Role role;

    @Autowired
    // private roleRepository roleRepository; // Ensure this is properly injected
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Map<String, Object> registerUser(User request) {
        // System.out.println("roles is --------------"+request.getRoles());
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }
    
        // // Check password match
        // if (!request.getPassword().equals(request.getConfirm_password())) {
        //     throw new IllegalArgumentException("Passwords do not match");
        // }
    
        // Generate unique ID
        String uniqueId = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    
        // Create user
        User user = new User();
        user.setRoles(request.getRoles()); // default role
        user.setContactNo(request.getContactNo());
        user.setUniqueId(uniqueId);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
    
        // Save user
        userRepository.save(user);
    
        // Generate JWT token
        String token = jwtService.GenerateToken(user.getEmail());
    
        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("user", user.getUsername());
        response.put("unique_id", user.getUniqueId());
        response.put("roles", user.getRoles());
        response.put("jwt", token); // <-- token included
    
        return response;
    }
    
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public Map<String, Object> verify(User user) {
    Map<String, Object> response = new HashMap<>();

    try {
        // Authenticate user
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Load full user details from DB
            User fullUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
                    System.out.println("full user is:----"+fullUser);

            // Generate JWT token
            String jwt = jwtService.GenerateToken(fullUser.getEmail());

            // Prepare response
            response.put("email", fullUser.getEmail());
            response.put("jwt", jwt);
            response.put("user", fullUser.getUsername());
            response.put("unique_id", fullUser.getUniqueId());
            response.put("roles", fullUser.getRoles());

            return response;
        } else {
            response.put("error", "Authentication failed");
            return response;
        }
    } catch (Exception e) {
        response.put("error", e.getMessage());
        return response;
    }
}


    public String updateProfile(String email, EditProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (!request.getPassword().equals(request.getConfirm_password())) {
                throw new RuntimeException("Password and confirm password do not match");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        System.out.println(request.getContactNo() + "===============");
        if (request.getContactNo() != null && !request.getContactNo().isBlank()) {
            user.setContactNo(request.getContactNo());
        }
        userRepository.save(user);
        return "Profile updated successfully!";
    }
}
