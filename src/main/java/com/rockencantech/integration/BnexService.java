package com.rockencantech.integration;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.rockencantech.exception.IntegrationException;
import com.rockencantech.integration.dto.BnexRequest;
import com.rockencantech.integration.dto.BnexResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BnexService {

    private final RestTemplate restTemplate;

    public BnexResponse identify(BnexRequest request) {
        var path = request.customerId().equals("unauthorized") ? "/identificador/unauthorized" : "/identificador";

        try {
            BnexResponse response = restTemplate.postForObject(path, request, BnexResponse.class);
            log.info("Response from Bnex: {}", response);
            return response;
        } catch (HttpClientErrorException e) {
            var message = "Erro na integracao com a Bnex - identify";
            log.error("{} {}", message, e.getResponseBodyAsString(), e);
            throw new IntegrationException(message, e);
        }
    }

    public BnexResponse authorize(BnexRequest request) {
        var path = request.customerId().equals("bad-request") ? "/autorizar/bad-request" : "/autorizar";

        try {
            BnexResponse response = restTemplate.postForObject(path, request, BnexResponse.class);
            log.info("Response from Bnex: {}", response);
            return response;
        } catch (HttpClientErrorException e) {
            var message = "Erro na integracao com a Bnex - authorize";
            log.error("{} {}", message, e.getResponseBodyAsString(), e);
            throw new IntegrationException(message, e);
        }
    }

    public BnexResponse register(BnexRequest request) {

        try {
            BnexResponse response = restTemplate.postForObject("registrar", request, BnexResponse.class);
            log.info("Response from Bnex: {}", response);
            return response;
        } catch (HttpClientErrorException e) {
            var message = "Erro na integracao com a Bnex - register";
            log.error("{} {}", message, e.getResponseBodyAsString(), e);
            throw new IntegrationException(message, e);
        }
    }
}