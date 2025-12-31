package com.binewsian.service;

import com.binewsian.exception.BiNewsianException;

public interface EmailService {
    void sendCredentials(String email, String username, String rawPassword) throws BiNewsianException;
    void sendResetPassword(String email, String token, String appUrl) throws BiNewsianException;
}
