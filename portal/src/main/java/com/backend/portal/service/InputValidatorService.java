package com.backend.portal.service;


import com.backend.portal.config.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class InputValidatorService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public void validateSearchQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query must not be empty");
        }
        if (query.length() < 2) {
            throw new IllegalArgumentException("Search query must be at least 2 characters");
        }
    }

    public void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }
    }

    public void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email format is invalid");
        }
    }


}
