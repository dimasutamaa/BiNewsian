package com.binewsian.repository;

import com.binewsian.model.ForumReply;
import com.binewsian.model.ForumThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReply, Long> {

    @Query("""
        select r from ForumReply r
        join fetch r.createdBy
        where r.thread = :thread
        order by r.createdAt asc
    """)
    List<ForumReply> findByThreadWithUserOrderByCreatedAtAsc(@Param("thread") ForumThread thread);

    long countByThread(ForumThread thread);

    @Query("""
        select r.thread.id, count(r)
        from ForumReply r
        where r.thread.id in :threadIds
        group by r.thread.id
    """)
    List<Object[]> countByThreadIds(@Param("threadIds") List<Long> threadIds);
}

