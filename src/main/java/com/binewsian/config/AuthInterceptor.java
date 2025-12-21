package com.binewsian.config;

import com.binewsian.annotation.RequireRole;
import com.binewsian.constant.AppConstant;
import com.binewsian.enums.Role;
import com.binewsian.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);

        if (requireRole == null) {
            requireRole = handlerMethod.getBeanType().getAnnotation(RequireRole.class);
        }

        if (requireRole != null) {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                if (isApiRequest(request)) {
                    sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, AppConstant.UNAUTHORIZED);
                } else {
                    response.sendRedirect("/login?error=unauthorized");
                }
                return false;
            }

            User user = (User) session.getAttribute("user");
            Role[] requiredRoles = requireRole.value();

            boolean hasRole = false;
            for (Role role : requiredRoles) {
                if (user.getRole() == role) {
                    hasRole = true;
                    break;
                }
            }

            if (!hasRole) {
                if (isApiRequest(request)) {
                    sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, AppConstant.FORBIDDEN);
                } else {
                    response.sendRedirect("/access-denied");
                }
                return false;
            }
        }

        return true;
    }

    private boolean isApiRequest(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(requestedWithHeader)) {
            return true;
        }

        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null && acceptHeader.contains("application/json")) {
            return true;
        }

        String method = request.getMethod();
        if (!"GET".equalsIgnoreCase(method)) {
            return true;
        }

        return acceptHeader != null && !acceptHeader.contains("txt/html");
    }

    private void sendJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"error\": %d, \"message\": \"%s\"}", status, message));
    }
}
