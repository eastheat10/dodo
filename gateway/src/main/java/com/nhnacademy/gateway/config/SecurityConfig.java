package com.nhnacademy.gateway.config;

import com.nhnacademy.gateway.security.handler.LoginSuccessHandler;
import com.nhnacademy.gateway.service.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/", "/login", "/signup", "/users/login").permitAll()
            .antMatchers("/projects/**").authenticated()
            .and();

        http
            .formLogin()
            .defaultSuccessUrl("/", true)
            .loginPage("/users/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(loginSuccessHandler(null))
            .loginProcessingUrl("/login")
            .and();

        http
            .logout()
            .logoutSuccessUrl("/")
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and();
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
            .sameOrigin()
            .and();

        http
            .csrf();
            .sameOrigin();

        http
            .csrf();

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(
        RedisTemplate<String, String> redisTemplate) {

        return new LoginSuccessHandler(redisTemplate);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailsService userDetailsService,
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
