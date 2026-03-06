package com.example.MEDICINE.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class geminiService {

    @Autowired
    private RestTemplate restTemplate;

    // ✅ Gemini Pro endpoint
    private static final String GEMINI_URL = 
    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-pro:generateContent";

    // ✅ API Key from application.properties
    @Value("${spring.ai.gemini.api-key}")
    private String API_KEY;

    public String askGemini(String prompt) {
        // Build request body in Gemini format
        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(textPart));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // API key goes in query param
        String url = GEMINI_URL + "?key=" + API_KEY;

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Send request
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map respBody = response.getBody();
            var candidates = (List<Map>) respBody.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map firstCandidate = candidates.get(0);
                Map contentResp = (Map) firstCandidate.get("content");
                if (contentResp != null) {
                    var parts = (List<Map>) contentResp.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        return parts.get(0).get("text").toString();
                    }
                }
            }
        }
        return "No response from Gemini";
    }
}
