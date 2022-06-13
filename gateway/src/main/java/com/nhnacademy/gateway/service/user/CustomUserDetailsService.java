package com.nhnacademy.gateway.service.user;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.gateway.dto.CustomUser;
import com.nhnacademy.gateway.dto.request.user.UserLoginRequest;
import com.nhnacademy.gateway.dto.response.user.UserResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final String BASE_URL = "http://localhost:8081";

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("login start");

        final String PATH = "/users/login";

        UserLoginRequest userLoginRequest = new UserLoginRequest(username);

        log.info("login user request = {}", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<UserLoginRequest> httpEntity = new HttpEntity<>(userLoginRequest, headers);

        ResponseEntity<UserResponse> exchange =
            restTemplate.exchange(BASE_URL + PATH, POST, httpEntity, UserResponse.class);

        if (Objects.equals(exchange.getStatusCode(), HttpStatus.BAD_REQUEST)) {
            throw new UsernameNotFoundException(username);
        }

        UserResponse response = Optional.ofNullable(exchange.getBody())
                                        .orElseThrow(() -> new UsernameNotFoundException(username));

        log.info("login user response = {}", response.getUsername());
        log.info("login user response = {}", response);

        log.info("encoding = {}", passwordEncoder.encode("1234"));
//        $2a$10$qgqXPyAnnLXX/qDyhzd01u5XAE6BjJErgiOohjd8QdmSrMIRXzdfe

        log.info("login status code = {}", exchange.getStatusCode());

        return new CustomUser(response, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
