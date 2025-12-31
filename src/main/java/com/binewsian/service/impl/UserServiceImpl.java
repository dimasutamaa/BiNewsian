package com.binewsian.service.impl;

import com.binewsian.exception.BiNewsianException;
import com.binewsian.model.User;
import com.binewsian.repository.UserRepository;
import com.binewsian.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) throws BiNewsianException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BiNewsianException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BiNewsianException("Old password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BiNewsianException("New password must be different from old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
