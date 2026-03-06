package com.example.MEDICINE.Controller;

import com.example.MEDICINE.Model.User;
import com.example.MEDICINE.Service.AuthService;
import com.example.MEDICINE.Service.UserService;
import com.example.MEDICINE.Service.gitHubAuthService;
import com.example.MEDICINE.Service.googleAuthService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://127.0.0.1:5503", "http://localhost:5503"})
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private googleAuthService googleAuthService;

    @Autowired
    private gitHubAuthService gitHubAuthService;

    public AuthController(AuthService authService) {
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    // Verify user credentials via service
    Map<String, Object> authResponse = userService.verify(user);
    System.out.println("authresponse"+authResponse);

    // authResponse can contain: email, jwt, fullName, unique_id, roles, etc.
    return ResponseEntity.ok(authResponse);
}


    @GetMapping("/oauth2/success")
    public ResponseEntity<String> oauth2Success() {
        return ResponseEntity.ok("OAuth2 login successful");
    }

    // oauth 2

    // Controller for oauth2
    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");
        System.out.println("Received ID Token: " + idToken);

        // Get JWT and user details from service
        Map<String, Object> authResponse = googleAuthService.verifyAndAuthenticateUser(idToken);

        System.out.println("Keys in authResponse: " + authResponse.keySet());
        String email = (String) authResponse.get("email");

        String jwt = (String) authResponse.get("jwt");
        String fullUser = (String) authResponse.get("name");
        String unique_id = (String) authResponse.get("unique_id");

        // Return only necessary data
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("jwt", jwt);
        response.put("user", fullUser);
        response.put("unique_id", unique_id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/github")
    public ResponseEntity<?> loginWithGitHub(@RequestBody Map<String, String> request) {
        try {
            String code = request.get("code");
            if (code == null || code.isEmpty()) {
                return ResponseEntity.badRequest().body("Authorization code is required");
            }

            Map<String, Object> authResponse = gitHubAuthService.verifyAndAuthenticateGitHubUser(code);
            return buildAuthResponse(authResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Authentication failed: " + e.getMessage());
        }
    }

    private ResponseEntity<?> buildAuthResponse(Map<String, Object> authResponse) {
        Map<String, Object> response = new HashMap<>();
        response.put("email", authResponse.get("email"));
        response.put("jwt", authResponse.get("jwt"));
        response.put("user", authResponse.get("user"));
        response.put("unique_id", authResponse.get("unique_id"));
        return ResponseEntity.ok(response);
    }

  }




