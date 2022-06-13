package com.nhnacademy.gateway.service.user;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.gateway.dto.request.user.UserSignupRequest;
import com.nhnacademy.gateway.dto.response.user.UserResponse;
import com.nhnacademy.gateway.exception.SignupFailException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String BASE_URL = "http://localhost:8081";

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    public void requestSignup(final UserSignupRequest signupRequest) {

        signupRequest.passwordEncoding(passwordEncoder.encode(signupRequest.getPassword()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<UserSignupRequest> httpEntity = new HttpEntity<>(signupRequest, headers);

        ResponseEntity<Void> exchange =
            restTemplate.exchange(BASE_URL + "/users/signup", POST, httpEntity, Void.class);

        if (Objects.equals(exchange.getStatusCode(), BAD_REQUEST)) {
            throw new SignupFailException();
        }

        log.info("response code = {}", exchange.getStatusCode());
    }

    public List<UserResponse> findUsers() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<UserResponse>> exchange =
            restTemplate.exchange(BASE_URL + "/users", GET, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        return exchange.getBody();
    }
}
