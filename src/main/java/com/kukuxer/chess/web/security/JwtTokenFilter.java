package com.kukuxer.chess.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String bearerToken = httpRequest.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            // Если заголовок отсутствует или не содержит токен, пропускаем запрос дальше
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        bearerToken = bearerToken.substring(7); // Удалить "Bearer " из заголовка
        try {
            if (jwtTokenProvider.validateToken(bearerToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null && authentication.getPrincipal() != null) { // Добавлена проверка на null
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ignored) {
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
