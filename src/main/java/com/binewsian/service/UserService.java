package com.binewsian.service;

import com.binewsian.exception.BiNewsianException;

public interface UserService {
    void changePassword(Long userId, String oldPassword, String newPassword) throws BiNewsianException;
}
