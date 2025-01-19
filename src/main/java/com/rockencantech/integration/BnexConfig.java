package com.rockencantech.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BnexConfig {

    @Value("${bnex.integration.url}")
    private String bnexUrl;

    @Value("${bnex.integration.username}")
    private String bnexUsername;

    @Value("${bnex.integration.password}")
    private String bnexPassword;

    @Value("${bnex.integration.connect.timeout}")
    private int bnextConnectTimeout;

    @Value("${bnex.integration.read.timeout}")
    private int bnextReadTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .requestFactory(this::createRequestFactory)
                .rootUri(bnexUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader(bnexUsername, bnexPassword))
                .build();

        restTemplate.setInterceptors(List.of(new BnexMockRestTemplateInterceptor()));
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory createRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(bnextConnectTimeout);
        factory.setReadTimeout(bnextReadTimeout);
        return factory;
    }

    private String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
