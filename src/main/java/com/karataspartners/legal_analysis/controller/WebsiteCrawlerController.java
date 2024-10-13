/*
package com.karataspartners.legal_analysis.controller;

import com.karataspartners.legal_analysis.service.WebsiteCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Collections;

import java.util.Map;

@RestController
@RequestMapping("/api/crawler")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WebsiteCrawlerController {

    private final WebsiteCrawlerService websiteCrawlerService;

    // URL geçerliliğini kontrol eden metod
    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/crawl")
    public ResponseEntity<Map<String, Object>> crawlWebsite(@RequestParam String url) {
        // URL'nin geçerliliğini kontrol et
        if (!isValidURL(url)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("hata", "Lütfen geçerli bir URL girin. URL'niz 'http://' veya 'https://' ile başlamalıdır."));
        }

        try {
            // Web sitesinin HTML içeriğini alıyoruz
            String content = websiteCrawlerService.crawlWebsite(url);

            if (content != null && !content.isEmpty()) {
                // Uyumluluk analizini yapıyoruz
                Map<String, Object> complianceResults = websiteCrawlerService.analyzeAndSuggest(content);

                // Sonuçları kullanıcıya döndürüyoruz
                return ResponseEntity.ok(complianceResults);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("hata", "İçerik alınamadı."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("hata", "Bir hata oluştu: " + e.getMessage()));
        }
    }
}
*/
