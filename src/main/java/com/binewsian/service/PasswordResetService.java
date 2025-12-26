package com.binewsian.service;

import com.binewsian.exception.BiNewsianException;

public interface PasswordResetService {
    void createPasswordResetTokenForUser(String email, String appUrl) throws BiNewsianException;
    boolean validatePasswordResetToken(String token);
    void updatePassword(String token, String newPassword) throws BiNewsianException;
}
