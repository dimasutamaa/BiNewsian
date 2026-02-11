package com.binewsian.repository;

import com.binewsian.model.ForumThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumThreadRepository extends JpaRepository<ForumThread, Long> {

    @Query("""
        select t from ForumThread t
        join fetch t.createdBy
        order by t.createdAt desc
    """)
    List<ForumThread> findAllNewestFirstWithUser();

    @Query("""
        select t from ForumThread t
        join fetch t.createdBy
        where t.id = :id
    """)
    Optional<ForumThread> findByIdWithUser(@Param("id") Long id);

    @NativeQuery("SELECT c.content_id, count(*) " +
            "FROM comments c " +
            "WHERE c.content_id IN :contentIds " +
            "AND c.content_type = 'THREAD' " +
            "AND c.is_deleted = false " +
            "GROUP BY c.content_id")
    List<Object[]> countByThreadIds(@Param("contentIds") List<Long> contentIds);

    @EntityGraph(attributePaths = {"createdBy"})
    Page<ForumThread> findAll(Pageable pageable);
}

