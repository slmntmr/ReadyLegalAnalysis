package com.karataspartners.legal_analysis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiAnalysisService {

    @Value("${openai.api.key}") // API anahtarı artık çevre değişkenlerinden alınacak
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // GPT modelini kullanarak metin analizi yapan metod
    public String analyzeContentWithAI(String content) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // OpenAI'ya gönderilecek mesajı hazırlıyoruz
            String prompt = buildComplianceCheckPrompt(content);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4-turbo"); // GPT-4 kullanıyoruz
            requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
            requestBody.put("max_tokens", 500);
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();  // OpenAI'dan gelen cevabı döndür
            } else if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                // OpenAI isteği başarısız oldu: Aşırı istek durumu
                return "Çok fazla istek gönderildi. Lütfen birkaç dakika bekleyip tekrar deneyin.";
            } else {
                return "OpenAI isteği başarısız oldu: " + response.getStatusCode();
            }

        } catch (Exception e) {
            return "OpenAI isteği sırasında hata oluştu: " + e.getMessage();
        }
    }

    // Yasal uyumluluk kontrolü için OpenAI'ya gönderilecek prompt'u oluşturuyoruz
    private String buildComplianceCheckPrompt(String content) {
        return "Aşağıdaki web sitesi içeriğini inceleyip, belirtilen yasal başlıklar ve anahtar kelimelere göre uygun olup olmadığını kontrol et. " +
                "Yasal uyumluluk başlıkları: Ticari Elektronik İleti Onay Metni, Mesafeli Hizmet Satış Sözleşmesi, Ön Bilgilendirme Formu, " +
                "İptal, İade ve Cayma Politikası, İşlem Rehberi. " +
                "Her başlık altında şu anahtar kelimelere bak: " +
                "Ticari Elektronik İleti Onay Metni: Elektronik, Ticari, İleti, Onay, 6563, SMS, Kampanya, Bilgilendirme, KVKK. " +
                "Mesafeli Hizmet Satış Sözleşmesi: Mesafeli, Hizmet, Satış, Sözleşmesi, Teslimat, Cayma, Ödeme, Tüketici, Ürün, Şartlar. " +
                "Ön Bilgilendirme Formu: Ön, Bilgilendirme, Formu, Teslimat, Ödeme, Cayma, Tüketici, Hizmet, Ürün, İade. " +
                "İptal, İade ve Cayma Politikası: İptal, İade, Cayma, Değişim, Sipariş, Teslim, Kargo, İletişim, Tüketici, Hasar. " +
                "İşlem Rehberi: İşlem, Rehber, Teknik, Adım, Üye, Ödeme, Sipariş, Değişiklik, Teslimat, Arşiv. " +
                "İçeriği analiz et ve eksik olan yasal anahtar kelimeleri belirt. Web sitesinin yasal uyumluluğu hakkında öneriler sun. " +
                "İncelenecek içerik: " + content;
    }
}
