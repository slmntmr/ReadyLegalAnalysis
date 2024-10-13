package com.karataspartners.legal_analysis.WebConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Tüm path'ler için CORS'u etkinleştirir
                .allowedOrigins("http://localhost:3000")  // Frontend URL'sini buraya ekleyin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // İzin verilen HTTP metodları
                .allowedHeaders("*")  // Tüm header'lara izin verir
                .allowCredentials(true);  // Credential (Çerez, Authorization gibi) desteğini etkinleştirir
    }
}
