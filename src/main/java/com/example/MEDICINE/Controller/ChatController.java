package com.example.MEDICINE.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MEDICINE.Service.geminiService;


//chat controller

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private geminiService geminiService;

     @PostMapping
    public Map<String, String> askGemini(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String reply = geminiService.askGemini(message);
        return Map.of("reply", reply);
    }
    
}