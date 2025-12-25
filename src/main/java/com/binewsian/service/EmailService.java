package com.binewsian.service;

import com.binewsian.exception.BiNewsianException;

public interface EmailService {
    void sendCredentials(String email, String username, String rawPassword) throws BiNewsianException;
}
