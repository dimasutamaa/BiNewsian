package com.binewsian.service;

import com.binewsian.exception.BiNewsianException;

public interface ContributorService {
    void create(String username, String email) throws BiNewsianException;
}
