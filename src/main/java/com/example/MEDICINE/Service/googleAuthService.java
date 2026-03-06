package com.example.MEDICINE.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.MEDICINE.Model.Role;
import com.example.MEDICINE.Model.User;
import com.example.MEDICINE.Repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class googleAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Role role;

    @Autowired
    private JWTService jwtService;

    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";
    public Map<String, Object> verifyAndAuthenticateUser(String idToken) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(GOOGLE_TOKEN_URL + idToken, Map.class);
        Map<String, Object> userDetails = response.getBody();
    
        if (userDetails == null || userDetails.get("email") == null) {
            throw new RuntimeException("Invalid Google Token");
        }
    
        String email = (String) userDetails.get("email");
    String name = (String) userDetails.get("name");
        String pictureUrl = (String) userDetails.get("picture");
    
        // Save user if not exist
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setPictureUrl(pictureUrl);
            newUser.setRoles(role.User);
            String UNIQUE_ID =UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            newUser.setUniqueId(UNIQUE_ID);
            String randomPassword = UUID.randomUUID().toString();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            newUser.setPassword(encoder.encode(randomPassword));
            // newUser.setConfirm_password(encoder.encode(randomPassword));
            userRepository.save(newUser);
        }
    
        String jwt = jwtService.generateToken(email, userDetails);
        Optional<User> uniqueid=userRepository.findByEmail(email);
        User user=uniqueid.get();
        
    
        Map<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("jwt", jwt);
        result.put("name", name);
        result.put("unique_id",user.getUniqueId());
        return result;
    }
    
}
