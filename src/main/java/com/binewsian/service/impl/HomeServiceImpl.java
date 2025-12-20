package com.binewsian.service.impl;

import com.binewsian.enums.ActivityStatus;
import com.binewsian.enums.NewsStatus;
import com.binewsian.enums.Role;
import com.binewsian.repository.ActivityRepository;
import com.binewsian.repository.CategoryRepository;
import com.binewsian.repository.NewsRepository;
import com.binewsian.repository.UserRepository;
import com.binewsian.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final ActivityRepository activityRepository;
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public Object getAdminSummary() {
        Map<String, Object> summary = new HashMap<>();

        summary.put("totalActivity", activityRepository.countByStatus(ActivityStatus.PUBLISHED));
        summary.put("totalNews", newsRepository.countByStatus(NewsStatus.PUBLISHED));
        summary.put("totalCategory", categoryRepository.count());
        summary.put("totalContributor", userRepository.countByRole(Role.CONTRIBUTOR));

        return summary;
    }
}
