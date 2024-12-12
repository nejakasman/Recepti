package com.example.recepti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Omogoča CORS za vse poti
                        .allowedOrigins("http://localhost:3000") // Dovoli zahteve s frontend naslova
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Dovoljeno za specifične metode
                        .allowedHeaders("*") // Dovoli vse HTTP glave
                        .allowCredentials(true); // Dovoli pošiljanje piškotkov ali poverilnic
            }
        };
    }
}

