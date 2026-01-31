package com.example.MEDICINE.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OAuth2Controller {

    @GetMapping("/public/info")
    public Map<String, String> publicInfo() {
        return Map.of("message", "This is a public endpoint accessible by anyone.");
    }

    @GetMapping("/secure/user")
    public Map<String, Object> userInfo(Authentication authentication) {
        return Map.of(
            "message", "This is a secured endpoint.",
            "userDetails", authentication.getPrincipal()
        );
    }
}
