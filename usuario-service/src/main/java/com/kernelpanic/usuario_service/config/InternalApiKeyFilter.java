package com.kernelpanic.usuario_service.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InternalApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Internal-Api-Key";

    private final String internalApiKey;

    public InternalApiKeyFilter(String internalApiKey) {
        this.internalApiKey = internalApiKey;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/usuario/financeiro");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String receivedKey = request.getHeader(HEADER_NAME);

        if (internalApiKey == null || internalApiKey.isBlank() || !internalApiKey.equals(receivedKey)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso interno nao autorizado.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
