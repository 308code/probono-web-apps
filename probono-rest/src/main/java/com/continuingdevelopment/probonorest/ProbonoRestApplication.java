package com.continuingdevelopment.probonorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProbonoRestApplication {
    private final static String pathPattern = "/**";
    private final static String allowedOrigins = "http://localhost:4200";
    public static void main(String[] args) {
        SpringApplication.run(ProbonoRestApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping(pathPattern)
                        .allowedMethods("POST", "GET", "PUT", "DELETE")
                        .allowedOrigins(allowedOrigins)
                        .maxAge(0);
            }
        };
    }

}
