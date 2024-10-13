/*
package com.karataspartners.legal_analysis.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Component
public class OpenAiRequest {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "OPENAI_API_KEY"; // OpenAI API anahtarınızı buraya ekleyin

    public String analyzeContent(String content) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4-turbo");
            requestBody.put("messages", new Object[]{Map.of("role", "user", "content", content)});
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();  // OpenAI'dan gelen cevabı döndür
            } else {
                return "OpenAI isteği başarısız oldu: " + response.getStatusCode();
            }

        } catch (Exception e) {
            return "OpenAI isteği sırasında hata oluştu: " + e.getMessage();
        }
    }
}
*/
