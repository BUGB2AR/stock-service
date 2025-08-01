package com.dev.jarmison.stock_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClientProduto() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api/produtos")
                .build();
    }
}
