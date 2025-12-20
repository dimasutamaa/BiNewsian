package com.binewsian.repository;

import com.binewsian.enums.NewsStatus;
import com.binewsian.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    int countByStatus(NewsStatus status);
}
