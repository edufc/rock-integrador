package com.rockencantech.integration;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockencantech.integration.dto.BnexRequest;
import com.rockencantech.integration.dto.BnexResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BnexMockRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    ObjectMapper mapper = new ObjectMapper();

    private String toJson(BnexResponse response) {
        try {
            return mapper.writeValueAsString(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String url = request.getURI().toString();

        String bodyString = new String(body, StandardCharsets.UTF_8);

        BnexRequest bnexRequest = mapper.readValue(bodyString, BnexRequest.class);

        // Handle API: identify
        if (url.contains("/identificador")) {
            if (url.contains("unauthorized")) {
                return createMockResponse("{\"error\":\"Unauthorized\"}", HttpStatus.UNAUTHORIZED);
            } else {
                return createMockResponse(
                        toJson(new BnexResponse(
                                bnexRequest, "id", "novo")),
                        HttpStatus.OK);
            }
        }

        // Handle API: authorize
        if (url.contains("/autorizar")) {
            if (url.contains("bad-request")) {
                return createMockResponse("{\"error\":\"Bad request\"}", HttpStatus.BAD_REQUEST);
            } else {
                return createMockResponse(toJson(new BnexResponse(bnexRequest, "id", "em_progresso")), HttpStatus.OK);
            }
        }

        // Handle API: register
        if (url.contains("/registrar")) {
            return createMockResponse(toJson(new BnexResponse(bnexRequest, "id", "finalizado")), HttpStatus.CREATED);
        }

        // Default behavior (pass-through)
        return execution.execute(request, body);
    }

    private ClientHttpResponse createMockResponse(String body, HttpStatus status) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return status;
            }

            @Override
            public int getRawStatusCode() {
                return status.value();
            }

            @Override
            public String getStatusText() {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public java.io.InputStream getBody() {
                return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
                return headers;
            }
        };
    }
}