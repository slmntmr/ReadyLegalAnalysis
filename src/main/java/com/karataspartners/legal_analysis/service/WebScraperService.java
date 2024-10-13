package com.karataspartners.legal_analysis.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@AllArgsConstructor
@Service // Bu sınıfı bir servis olarak işaretliyoruz
public class WebScraperService {
    private final OpenAiAnalysisService openAiAnalysisService;

    //*******************************************************************************************************

    // Anahtar kelimeleri ve başlıkları sabit olarak tutuyoruz
    private static final Map<String, Set<String>> KEYWORDS = new HashMap<>();

    static {
        Set<String> ticariElektronikKeywords = new HashSet<>();
        ticariElektronikKeywords.add("Elektronik");
        ticariElektronikKeywords.add("Ticari");
        ticariElektronikKeywords.add("İleti");
        ticariElektronikKeywords.add("Onay");
        ticariElektronikKeywords.add("6563");
        ticariElektronikKeywords.add("SMS");
        ticariElektronikKeywords.add("Kampanya");
        ticariElektronikKeywords.add("Bilgilendirme");
        ticariElektronikKeywords.add("KVKK");
        KEYWORDS.put("Ticari Elektronik İleti Onay Metni", ticariElektronikKeywords);

        Set<String> mesafeliSatisKeywords = new HashSet<>();
        mesafeliSatisKeywords.add("Mesafeli");
        mesafeliSatisKeywords.add("Hizmet");
        mesafeliSatisKeywords.add("Satış");
        mesafeliSatisKeywords.add("Sözleşmesi");
        mesafeliSatisKeywords.add("Teslimat");
        mesafeliSatisKeywords.add("Cayma");
        mesafeliSatisKeywords.add("Ödeme");
        mesafeliSatisKeywords.add("Tüketici");
        mesafeliSatisKeywords.add("Ürün");
        mesafeliSatisKeywords.add("Şartlar");
        KEYWORDS.put("Mesafeli Hizmet Satış Sözleşmesi", mesafeliSatisKeywords);

        Set<String> onBilgilendirmeKeywords = new HashSet<>();
        onBilgilendirmeKeywords.add("Ön");
        onBilgilendirmeKeywords.add("Bilgilendirme");
        onBilgilendirmeKeywords.add("Formu");
        onBilgilendirmeKeywords.add("Teslimat");
        onBilgilendirmeKeywords.add("Ödeme");
        onBilgilendirmeKeywords.add("Cayma");
        onBilgilendirmeKeywords.add("Tüketici");
        onBilgilendirmeKeywords.add("Hizmet");
        onBilgilendirmeKeywords.add("Ürün");
        onBilgilendirmeKeywords.add("İade");
        KEYWORDS.put("Ön Bilgilendirme Formu", onBilgilendirmeKeywords);

        Set<String> iptalIadeKeywords = new HashSet<>();
        iptalIadeKeywords.add("İptal");
        iptalIadeKeywords.add("İade");
        iptalIadeKeywords.add("Cayma");
        iptalIadeKeywords.add("Değişim");
        iptalIadeKeywords.add("Sipariş");
        iptalIadeKeywords.add("Teslim");
        iptalIadeKeywords.add("Kargo");
        iptalIadeKeywords.add("İletişim");
        iptalIadeKeywords.add("Tüketici");
        iptalIadeKeywords.add("Hasar");
        KEYWORDS.put("İptal, İade ve Cayma Politikası", iptalIadeKeywords);

        Set<String> islemRehberiKeywords = new HashSet<>();
        islemRehberiKeywords.add("İşlem");
        islemRehberiKeywords.add("Rehber");
        islemRehberiKeywords.add("Teknik");
        islemRehberiKeywords.add("Adım");
        islemRehberiKeywords.add("Üye");
        islemRehberiKeywords.add("Ödeme");
        islemRehberiKeywords.add("Sipariş");
        islemRehberiKeywords.add("Değişiklik");
        islemRehberiKeywords.add("Teslimat");
        islemRehberiKeywords.add("Arşiv");
        KEYWORDS.put("İşlem Rehberi", islemRehberiKeywords);
    }

    // URL'den içerik çekme metodu
    public String fetchWebsiteContent(String url) {
        try {
            // Jsoup ile web sitesinin HTML içeriğini al
            Document document = Jsoup.connect(url).get();
            // HTML içeriğini sadeleştirip sadece metni döndür
            return document.text();
        } catch (IOException e) {
            // Hata durumunda null döndür
            e.printStackTrace();
            return null;
        }
    }




    // Anahtar kelime tarama ve puanlama metodu
    public Map<String, Object> analyzeContent(String content) {
        Map<String, Object> result = new HashMap<>();
        int totalScore = 0;
        int totalMaxScore = 0;
        Map<String, Set<String>> missingKeywordsByTitle = new HashMap<>();

        // Her başlık için tarama yapıyoruz
        for (Map.Entry<String, Set<String>> entry : KEYWORDS.entrySet()) {
            String title = entry.getKey();
            Set<String> keywords = entry.getValue();
            Set<String> missingKeywords = new HashSet<>();

            int foundKeywords = 0;
            for (String keyword : keywords) {
                if (!content.contains(keyword)) {
                    missingKeywords.add(keyword);  // Eksik anahtar kelimeyi kaydet
                } else {
                    foundKeywords++;
                }
            }

            int maxScoreForTitle = keywords.size() * 10;
            int scoreForTitle = foundKeywords * 10;
            totalScore += scoreForTitle;
            totalMaxScore += maxScoreForTitle;

            if (!missingKeywords.isEmpty()) {
                missingKeywordsByTitle.put(title, missingKeywords);  // Eksik kelimeleri ekle
            }
        }

        // Genel puanı yüzlük sistemde hesaplıyoruz
        double percentageScore = ((double) totalScore / totalMaxScore) * 100;

        // Puanı virgülden sonra iki basamağa sınırla
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedScore = df.format(percentageScore);

        // Puan aralığına göre durum ve not belirleme
        String durum;
        String not;

        if (percentageScore >= 80) {
            durum = "Hukuka Uygun";
            not = "Geçer Not";
        } else if (percentageScore >= 40) {
            durum = "Metin Detaylı Düzenlenmelidir";
            not = "Geçmez Not";
        } else {
            durum = "Metniniz Hukuka Uygun Değil";
            not = "Geçmez Not";
        }

        // Sonuçları hazırlıyoruz
        result.put("score", formattedScore);
        result.put("missingKeywords", missingKeywordsByTitle);
        result.put("durum", durum);
        result.put("not", not);

        // Yapay zeka ile önerileri ekliyoruz
        Map<String, Object> suggestions = analyzeContentWithAI(content);  // AI analizini çağırıyoruz
        result.put("suggestions", suggestions);  // Önerileri sonuçlara ekliyoruz

        return result;
    }




    //*******************************************************************************************************
/*
    // Öneri üreten metod
    private Map<String, String> generateSuggestions(Map<String, Set<String>> missingKeywordsByTitle) {
        Map<String, String> suggestions = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : missingKeywordsByTitle.entrySet()) {
            String title = entry.getKey();
            Set<String> missingKeywords = entry.getValue();

            StringBuilder suggestion = new StringBuilder();
            suggestion.append(title).append(" başlığı için şunları ekleyin: ");

            for (String keyword : missingKeywords) {
                suggestion.append(keyword).append(", ");
            }

            // Öneriyi temizleyip ekliyoruz
            suggestions.put(title, suggestion.toString().replaceAll(", $", ""));
        }

        return suggestions;
    }*/

    //*******************************************************************************************************


    // Yapay zeka ile içerik analizi yapma metodu
    public Map<String, Object> analyzeContentWithAI(String content) {
        Map<String, Object> result = new HashMap<>();

        // Yapay zeka ile analiz yapıyoruz
        String aiAnalysisResult = openAiAnalysisService.analyzeContentWithAI(content);

        // OpenAI yanıtından sadece mesajı alıyoruz ve temizliyoruz
        String aiMessage = cleanText(extractMessageFromOpenAiResponse(aiAnalysisResult));

        result.put("aiAnalysis", aiMessage); // Yapay zeka sonucu ekleniyor

        return result;
    }

    // OpenAI API yanıtından sadece mesajı çeken metot
    private String extractMessageFromOpenAiResponse(String aiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(aiResponse);
            JsonNode messageNode = root.path("choices").get(0).path("message").path("content");
            return messageNode.asText();
        } catch (IOException e) {
            e.printStackTrace();
            return "Yapay zeka yanıtı işlenirken hata oluştu.";
        }
    }
    // Metindeki gereksiz \n gibi karakterleri temizleme metodu
    public String cleanText(String text) {
        return text.replaceAll("\n", " ").trim(); // Tekli backslash yerine çift backslash kullanarak \n karakterini doğru şekilde hedefliyoruz
    }




}
