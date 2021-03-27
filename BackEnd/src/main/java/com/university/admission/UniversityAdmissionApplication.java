package com.university.admission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UniversityAdmissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityAdmissionApplication.class, args);
    }

    @Configuration
    public class WebConfiguration implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*").allowedHeaders("*");
        }
    }
}
