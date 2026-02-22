package com.edu.Institiute.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getLoggedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getName().equals("anonymousUser")) {
            return null;
        }

        return authentication.getName(); // username from JWT
    }
}
