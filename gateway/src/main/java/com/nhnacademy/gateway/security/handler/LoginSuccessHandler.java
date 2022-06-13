package com.nhnacademy.gateway.security.handler;

import com.nhnacademy.gateway.dto.CustomUser;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {

        log.error("go in handler");

        CustomUser user = (CustomUser) authentication.getPrincipal();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());

        HttpSession session = request.getSession(true);
        session.setAttribute("username", user.getUsername());

        Cookie cookie = new Cookie("SESSION", session.getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(daysToSec(3));
        response.addCookie(cookie);


        redisTemplate.opsForHash().put(session.getId(), "username", user.getUsername());
        redisTemplate.opsForHash().put(session.getId(), "id", String.valueOf(user.getId()));

        redisTemplate.opsForHash()
                     .put(session.getId(), "authority", authorities.get(0).getAuthority());
        redisTemplate.boundHashOps(session.getId()).expire(Duration.ofDays(3));

        log.info("login success user = {}", user.getUsername());

        response.sendRedirect("/");
    }

    private int daysToSec(int day) {
        return 60 * 60 * 24 * day;
    }
}
