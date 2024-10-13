package com.karataspartners.legal_analysis.controller;

import com.karataspartners.legal_analysis.service.WebScraperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/crawler")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class WebScraperController {

    private final WebScraperService webScraperService; // Web Scraper Servisi

    //*******************************************************************************************************
    // URL geçerliliğini kontrol eden metod
    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //*******************************************************************************************************
    @GetMapping("/crawl")
    public ResponseEntity<?> crawlWebsite(@RequestParam String url) {
        // URL'nin geçerliliğini kontrol ediyoruz
        if (!isValidURL(url)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Geçersiz URL. Lütfen URL'nin 'http://' veya 'https://' ile başladığından emin olun. Örnek: https://www.example.com"));
        }

        String content = webScraperService.fetchWebsiteContent(url);

        if (content != null && !content.isEmpty()) {
            // İçeriği tarayıp analiz yapıyoruz
            Map<String, Object> analysis = webScraperService.analyzeContent(content);
            return ResponseEntity.ok(analysis);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "İçerik alınamadı."));
        }
    }

    //*******************************************************************************************************
}
