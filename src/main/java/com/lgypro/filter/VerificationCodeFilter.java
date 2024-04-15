package com.lgypro.filter;

import com.lgypro.exception.VerificationCodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class VerificationCodeFilter extends OncePerRequestFilter {
    AuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler("/login");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login") && request.getMethod().equals("POST")) {
            try {
                verificationCode(request, response);
                filterChain.doFilter(request, response);
            } catch (VerificationCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws VerificationCodeException {
        String requestCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("captcha");
        session.removeAttribute("captcha");
        if (requestCode == null || !requestCode.equals(sessionCode)) {
            throw new VerificationCodeException();
        }
    }
}
