package com.example.MEDICINE.Controller;


import com.example.MEDICINE.Model.User;
import com.example.MEDICINE.Service.EditProfileRequest;
import com.example.MEDICINE.Service.UserService;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
 public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String greeting(){
        return "Hello World!";
    }


    @PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody User user, BindingResult result) {
    // System.out.println("result is ++++++++++++"+result);
    // System.out.println(user.getRoles());+
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }

    try {
        Map<String, Object> registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
    }
}

    
    @GetMapping("/getUser/{email}")
    public Optional<User> getuser(@PathVariable String email){
        return userService.getUser(email);
}


 @PutMapping("/edit-profile")
    public ResponseEntity<?> editProfile(@RequestBody EditProfileRequest request, Principal principal) {
        try {
            String email = principal.getName(); // From authenticated user
            String message = userService.updateProfile(email, request);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
 }
