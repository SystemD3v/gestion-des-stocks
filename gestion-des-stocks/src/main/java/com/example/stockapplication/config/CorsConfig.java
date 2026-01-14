package com.example.stockapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials
        config.setAllowCredentials(true);

        // Allow requests from the Vue.js frontend
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://10.129.129.127:5173");

        // Allow requests from any local network device
        config.addAllowedOriginPattern("http://192.168.*.*:5173");
        config.addAllowedOriginPattern("http://10.*.*.*:5173");
        config.addAllowedOriginPattern("http://172.*.*.*:5173");

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
