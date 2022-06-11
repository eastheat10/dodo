package com.nhnacademy.gateway.config;

import com.nhnacademy.gateway.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/", "/login", "/signup").permitAll();

        http
            .formLogin()
            .successHandler(loginSuccessHandler(null))
            .usernameParameter("username")
            .passwordParameter("password")
            .loginPage("/users/login")
            .loginProcessingUrl("/login");

        http
            .logout()
            .logoutUrl("/users/logout");

        http
            .headers()
            .defaultsDisabled()
            .frameOptions()
            .sameOrigin();

        http
            .csrf()
            .disable();

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(
        RedisTemplate<String, String> redisTemplate) {

        return new LoginSuccessHandler(redisTemplate);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
