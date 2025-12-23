package com.binewsian.service;

import com.binewsian.dto.CreateActivityRequest;
import com.binewsian.exception.BiNewsianException;
import com.binewsian.model.Activity;
import com.binewsian.model.User;

import org.springframework.data.domain.Page;

public interface ActivityService {
    void create(CreateActivityRequest request, User user) throws BiNewsianException;
    void update(Long id, CreateActivityRequest request, User user) throws BiNewsianException;
    void delete(Long id) throws BiNewsianException;
    Activity findById(Long id) throws BiNewsianException;
    Page<Activity> findPaginated(int page, int size);
    Page<Activity> findPaginatedByUserId(int page, int size, long user);
}
