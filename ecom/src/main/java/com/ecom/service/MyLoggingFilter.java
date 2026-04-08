package com.ecom.service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class MyLoggingFilter extends OncePerRequestFilter {

    private final UserSession userSession;

    public MyLoggingFilter(UserSession userSession) {
        this.userSession = userSession;
    }

    // List of URLs that do not require a session
    private final List<String> excludedUrls = Arrays.asList("/signup", "/login","/form","/register");

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
                                    throws ServletException, IOException {

        String path = request.getRequestURI();
        // 1. Check if the current URL is in the exclusion list
        boolean isExcluded = excludedUrls.stream().anyMatch(path::startsWith);
        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Expires", "0");

        // 2. Check if user is logged in via UserSession bean
        if (!userSession.isLoggedIn()) {
            // 3. Redirect to login if not authenticated
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // 4. User is logged in, proceed with the request
            filterChain.doFilter(request, response);
        }
    }
}