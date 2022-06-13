package com.nhnacademy.gateway.service;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

import com.nhnacademy.gateway.dto.CustomUser;
import com.nhnacademy.gateway.dto.request.EmailRequest;
import com.nhnacademy.gateway.dto.request.oauth.AccessToken;
import com.nhnacademy.gateway.dto.response.oauth.GithubProfile;
import com.nhnacademy.gateway.dto.response.oauth.OAuthToken;
import com.nhnacademy.gateway.dto.response.user.UserResponse;
import com.nhnacademy.gateway.exception.UserNotFoundByEmailException;
import com.nhnacademy.gateway.security.handler.LoginSuccessHandler;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service {

    @Value("${github.client.id}")
    private String CLIENT_ID;
    @Value("${github.client.secret}")
    private String CLIENT_SECRET;

    private static final String BASE_URL = "http://localhost:8081";
    private static final String TOKEN_REQUEST_URL = "https://github.com/login/oauth/access_token";
    private static final String PROFILE_REQUEST_URL = "https://api.github.com/user";
    private static final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/github";

    private final RedisTemplate<String, String> redisTemplate;
    private final RestTemplate restTemplate;

    public String getRedirectUrl() {
        return "https://github.com/login/oauth/authorize?" +
            "client_id=" + CLIENT_ID + "&" +
            "redirect_uri=" + REDIRECT_URL;
    }

    public boolean verifyResidentByEmail(String email, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException, IOException {

        log.info("email request = {}", email);

        final String PATH = "/users/email";

        EmailRequest emailRequest = new EmailRequest(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(List.of(APPLICATION_JSON));

        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);

        ResponseEntity<UserResponse> responseEntity =
            restTemplate.exchange(BASE_URL + PATH, POST, httpEntity, UserResponse.class);

        UserResponse userResponse =
            Optional.ofNullable(responseEntity.getBody())
                    .orElseThrow(() -> new UserNotFoundByEmailException(email));

        CustomUser user = new CustomUser(userResponse,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        new LoginSuccessHandler(redisTemplate).onAuthenticationSuccess(request, response,
            getAuthentication(user));

        log.info("response status = {}", responseEntity.getStatusCode());
        log.info("user response = {}", userResponse.getUsername());

        return true;
    }

    public GithubProfile getProfile(String code) {

        OAuthToken oAuthToken = getOAuthToken(code);

        RestTemplate template = new RestTemplate();
        ResponseEntity<GithubProfile> profileResponse =
            template.exchange(PROFILE_REQUEST_URL, HttpMethod.GET, getProfileRequest(oAuthToken),
                GithubProfile.class);

        return profileResponse.getBody();
    }

    private Authentication getAuthentication(UserDetails user) {

        Authentication authentication =
            new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return authentication;
    }

    private HttpEntity<AccessToken> getCodeRequest(String code) {

        AccessToken token = new AccessToken(CLIENT_ID, CLIENT_SECRET, code);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(token, headers);
    }

    private OAuthToken getOAuthToken(String code) {

        HttpEntity<AccessToken> codeRequest = getCodeRequest(code);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OAuthToken> tokenResponse =
            restTemplate.exchange(TOKEN_REQUEST_URL, HttpMethod.POST, codeRequest,
                OAuthToken.class);

        return tokenResponse.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> getProfileRequest(OAuthToken token) {

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", "token " + token.getAccessToken());
        return new HttpEntity<>(requestHeader);
    }
}