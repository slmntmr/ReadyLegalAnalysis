/*
package com.karataspartners.legal_analysis.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComplianceScoringService {

    // Anahtar kelimeler ve puanlar (Açıklamalı)
    private static final Map<String, Map<String, int[]>> complianceKeywords = new HashMap<>();

    static {
        // Ticari Elektronik İleti Onay Metni için anahtar kelimeler ve puanlar
        Map<String, int[]> ticariElektronikIletiKeywords = new HashMap<>();
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti, Onay, 6563, SMS, Mail, Kampanya, Bilgilendirme, KVKK", new int[]{100, 0});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti, Onay, 6563, SMS, Mail, Kampanya", new int[]{90, 0});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti, Onay, 6563, SMS, Mail", new int[]{80, 0});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti, Onay, 6563, SMS", new int[]{70, 1});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti, Onay, 6563", new int[]{60, 1});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti, Onay", new int[]{50, 1});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari, İleti", new int[]{40, 2});
        ticariElektronikIletiKeywords.put("Elektronik, Ticari", new int[]{30, 2});
        ticariElektronikIletiKeywords.put("Elektronik", new int[]{20, 2});
        ticariElektronikIletiKeywords.put("-", new int[]{0, 2});
        complianceKeywords.put("Ticari Elektronik İleti Onay Metni", ticariElektronikIletiKeywords);

        // Mesafeli Hizmet Satış Sözleşmesi için anahtar kelimeler ve puanlar
        Map<String, int[]> mesafeliHizmetKeywords = new HashMap<>();
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet, Satış, Sözleşmesi, Teslimat, Ödeme, Cayma, Tüketici, Ürün, Şartlar", new int[]{100, 0});
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet, Satış, Sözleşmesi, Teslimat, Ödeme, Cayma", new int[]{90, 0});
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet, Satış, Sözleşmesi, Teslimat, Ödeme", new int[]{80, 0});
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet, Satış, Sözleşmesi, Teslimat", new int[]{70, 1});
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet, Satış, Sözleşmesi", new int[]{60, 1});
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet, Satış", new int[]{50, 1});
        mesafeliHizmetKeywords.put("Mesafeli, Hizmet", new int[]{40, 2});
        mesafeliHizmetKeywords.put("Mesafeli", new int[]{30, 2});
        mesafeliHizmetKeywords.put("-", new int[]{0, 2});
        complianceKeywords.put("Mesafeli Hizmet Satış Sözleşmesi", mesafeliHizmetKeywords);

        // Ön Bilgilendirme Formu için anahtar kelimeler ve puanlar
        Map<String, int[]> onBilgilendirmeKeywords = new HashMap<>();
        onBilgilendirmeKeywords.put("Ön, Bilgilendirme, Formu, Teslimat, Ödeme, Cayma, Tüketici, Hizmet, Ürün, İade", new int[]{100, 0});
        onBilgilendirmeKeywords.put("Ön, Bilgilendirme, Formu, Teslimat, Ödeme, Cayma", new int[]{90, 0});
        onBilgilendirmeKeywords.put("Ön, Bilgilendirme, Formu, Teslimat, Ödeme", new int[]{80, 0});
        onBilgilendirmeKeywords.put("Ön, Bilgilendirme, Formu, Teslimat", new int[]{70, 1});
        onBilgilendirmeKeywords.put("Ön, Bilgilendirme, Formu", new int[]{60, 1});
        onBilgilendirmeKeywords.put("Ön, Bilgilendirme", new int[]{50, 1});
        onBilgilendirmeKeywords.put("Ön", new int[]{40, 2});
        onBilgilendirmeKeywords.put("-", new int[]{0, 2});
        complianceKeywords.put("Ön Bilgilendirme Formu", onBilgilendirmeKeywords);

        // İptal, İade ve Cayma Politikası için anahtar kelimeler ve puanlar
        Map<String, int[]> iptalIadeKeywords = new HashMap<>();
        iptalIadeKeywords.put("İptal, İade, Cayma, Değişim, Sipariş, Teslim, Kargo, İletişim, Tüketici, Hasar", new int[]{100, 0});
        iptalIadeKeywords.put("İptal, İade, Cayma, Değişim, Sipariş", new int[]{90, 0});
        iptalIadeKeywords.put("İptal, İade, Cayma, Değişim", new int[]{80, 0});
        iptalIadeKeywords.put("İptal, İade, Cayma", new int[]{70, 1});
        iptalIadeKeywords.put("İptal, İade", new int[]{60, 1});
        iptalIadeKeywords.put("İptal", new int[]{50, 1});
        iptalIadeKeywords.put("-", new int[]{0, 2});
        complianceKeywords.put("İptal, İade ve Cayma Politikası", iptalIadeKeywords);

        // İşlem Rehberi için anahtar kelimeler ve puanlar
        Map<String, int[]> islemRehberiKeywords = new HashMap<>();
        islemRehberiKeywords.put("İşlem, Rehber, Teknik, Adım, Üye, Ödeme, Sipariş, Değişiklik, Teslimat, Arşiv", new int[]{100, 0});
        islemRehberiKeywords.put("İşlem, Rehber, Teknik, Adım", new int[]{90, 0});
        islemRehberiKeywords.put("İşlem, Rehber, Teknik", new int[]{80, 0});
        islemRehberiKeywords.put("İşlem, Rehber", new int[]{70, 1});
        islemRehberiKeywords.put("İşlem", new int[]{60, 1});
        islemRehberiKeywords.put("-", new int[]{0, 2});
        complianceKeywords.put("İşlem Rehberi", islemRehberiKeywords);
    }

    //***************************************************************************************************************
    // Uyumluluk değerlendirmesi yapan metod
    public String evaluateComplianceScore(int score, int detailsNeeded) {
        if (score >= 90) {
            return "Hukuka Uygun, Geçer Not";
        } else if (score >= 70) {
            return "Metin Düzenlenmeli, Geçer Not";
        } else if (score >= 50 || detailsNeeded > 0) {
            return "Metin Detaylı Düzenlenmeli, Geçmez Not";
        } else {
            return "Hukuka Uygun Değil, Geçmez Not";
        }
    }

    //***************************************************************************************************************

    // Uyumluluk önerilerini döndüren metod
    public Map<String, String> generateComplianceSuggestions(String siteContent) {
        Map<String, String> suggestions = new HashMap<>();

        // Burada site içeriğine göre uyumluluk önerileri oluşturulacak
        for (String complianceType : complianceKeywords.keySet()) {
            Map<String, int[]> keywords = complianceKeywords.get(complianceType);
            for (String key : keywords.keySet()) {
                if (!siteContent.contains(key)) {
                    suggestions.put(complianceType, complianceType + " için '" + key + "' kelimesi eksik.");
                }
            }
        }

        return suggestions;
    }

    //***************************************************************************************************************

    // Puanlama işlemini gerçekleştiren metod
    public Map<String, int[]> calculateScore(String siteContent) {
        Map<String, int[]> scoreResults = new HashMap<>();

        // Her uyumluluk türü için anahtar kelimeleri kontrol et
        for (String complianceType : complianceKeywords.keySet()) {
            Map<String, int[]> keywords = complianceKeywords.get(complianceType);
            int bestScore = 0;
            int detailsNeeded = 0;

            for (Map.Entry<String, int[]> entry : keywords.entrySet()) {
                String keyword = entry.getKey();
                int[] scoreDetails = entry.getValue();

                if (siteContent.contains(keyword)) {
                    bestScore = Math.max(bestScore, scoreDetails[0]);
                    detailsNeeded = Math.min(detailsNeeded, scoreDetails[1]);
                } else {
                    detailsNeeded += scoreDetails[1]; // Eğer anahtar kelime yoksa, detay gereksinimini artır
                }
            }

            scoreResults.put(complianceType, new int[]{bestScore, detailsNeeded});
        }

        return scoreResults;
}
}*/
