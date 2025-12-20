package com.binewsian.repository;

import com.binewsian.enums.ActivityStatus;
import com.binewsian.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    int countByStatus(ActivityStatus status);
}
