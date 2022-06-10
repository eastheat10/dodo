package com.nhnacademy.gateway.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableSpringDataWebSupport
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setReadTimeout(Duration.ofSeconds(5L))
            .setConnectTimeout(Duration.ofSeconds(3L))
            .build();
    }
}
