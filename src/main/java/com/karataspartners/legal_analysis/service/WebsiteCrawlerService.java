/*
package com.karataspartners.legal_analysis.service;

import com.karataspartners.legal_analysis.dto.OpenAiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
@RequiredArgsConstructor
@Service
public class WebsiteCrawlerService {

    private final OpenAiRequest openAiRequest;
    private final ComplianceScoringService complianceScoringService;
//***********************************************************************************************************



    // Web sitesinden dinamik olarak içeriği çeken metod
    public String crawlWebsite(String url) {
        try {
            // Jsoup ile web sitesinin HTML içeriğini çekiyoruz
            Document document = Jsoup.connect(url).get();
            // HTML içeriğini string olarak döndürüyoruz
            return document.html();
        } catch (IOException e) {
            // Bir hata oluşursa null döndür
            return null;
        }
    }



    //***********************************************************************************************************


    // Uyumluluk raporunu ve önerileri birleştiren fonksiyon
    public Map<String, Object> analyzeAndSuggest(String siteContent) {
        Map<String, Object> results = new HashMap<>();

        // Uyumluluk raporu ve önerileri
        Map<String, String> suggestions = complianceScoringService.generateComplianceSuggestions(siteContent);
        results.put("suggestions", suggestions);

        // Puanlama işlemi
        Map<String, int[]> scoreResults = complianceScoringService.calculateScore(siteContent);

        // Toplam puanı ve detay gereksinimini al
        int totalScore = 0;
        int detailsNeeded = 0;

        for (int[] score : scoreResults.values()) {
            totalScore += score[0]; // Toplam puanı hesapla
            detailsNeeded += score[1]; // Detay gereksinimlerini hesapla
        }


//***********************************************************************************************************


        // Değerlendirmeyi yap
        String evaluation = complianceScoringService.evaluateComplianceScore(totalScore, detailsNeeded);

        results.put("score", totalScore);
        results.put("evaluation", evaluation);

        return results;
}
}*/
