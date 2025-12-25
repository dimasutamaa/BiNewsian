package com.binewsian.service;

import java.util.Map;

public interface HomeService {
    Object getAdminSummary();
    Object getContributorSummary(Long userId);
}
