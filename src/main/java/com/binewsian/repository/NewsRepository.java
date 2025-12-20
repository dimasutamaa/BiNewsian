package com.binewsian.repository;

import com.binewsian.enums.NewsStatus;
import com.binewsian.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    int countByStatus(NewsStatus status);

    Page<News> findByStatus(NewsStatus status, Pageable pageable);
}
