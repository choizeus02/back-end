package com.example.modemate.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String CORS_URL_PATTERN = "/**";

    private static final String CORS_URL = "http://localhost:5173";
    private static final String CORS_URL2 = "http://frontend:5173";
    private static final String CORS_URL3 = "http://3.35.123.191:5173";

    private static final String CORS_URL4 = "http://moodmate.choizeus.com:5173";

    private static final String CORS_URL5 = "http://moodmate.choizeus.com";

    private static final String CORS_URL6 = "http://52.78.55.94:5173";

    private static final String CORS_URL7 = "http://116.121.184.204:5173";



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(CORS_URL_PATTERN)
                .allowedHeaders("*")
                .allowedOrigins(CORS_URL,CORS_URL2,CORS_URL3,CORS_URL4,CORS_URL5,CORS_URL6,CORS_URL7)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);

    }
}