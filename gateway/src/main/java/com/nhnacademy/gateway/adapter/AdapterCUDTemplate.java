package com.nhnacademy.gateway.adapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdapterCUDTemplate {

    public static final String BASE_URL = "http://localhost:8082";

    public static <T> void create(RestTemplate restTemplate, String domain, T createRequest) {

        HttpHeaders headers = getHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<T> httpEntity = new HttpEntity<>(createRequest, headers);

        ResponseEntity<Void> exchange =
            restTemplate.exchange(BASE_URL + domain, POST, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        verifyCode(exchange.getStatusCode());
    }

    public static <T> void modify(RestTemplate restTemplate, String domain, T modifyRequest) {

        HttpHeaders headers = getHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<T> httpEntity = new HttpEntity<>(modifyRequest, headers);

        ResponseEntity<Void> exchange =
            restTemplate.exchange(BASE_URL + domain, PUT, httpEntity, new ParameterizedTypeReference<>() {
            });

        verifyCode(exchange.getStatusCode());
    }

    public static void delete(RestTemplate restTemplate, String domain, Long id) {

        final String PATH = domain + "/" + id;

        HttpEntity<Void> httpEntity = new HttpEntity<>(getHeaders());

        ResponseEntity<Void> exchange = restTemplate.exchange(BASE_URL + PATH, DELETE, httpEntity,
            new ParameterizedTypeReference<>() {
            });

        verifyCode(exchange.getStatusCode());
    }

    public static void verifyCode(HttpStatus httpStatus) {
        if (httpStatus.is4xxClientError()) {
            throw new IllegalArgumentException();
        }
    }

    private static HttpHeaders getHeaders() {
        return new HttpHeaders();
    }
}
