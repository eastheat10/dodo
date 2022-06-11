package com.nhnacademy.gateway.dto.request.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.PropertySource;

public class AccessToken {

    private final String clientId;
    private final String clientSecret;
    private final String code;

    public AccessToken(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return this.clientId;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return this.clientSecret;
    }

    @JsonProperty("code")
    public String getCode() {
        return this.code;
    }
}
