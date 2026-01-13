package com.binewsian.service.impl;

import com.binewsian.constant.AppConstant;
import com.binewsian.dto.CommentRequest;
import com.binewsian.exception.BiNewsianException;
import com.binewsian.model.Comment;
import com.binewsian.model.User;
import com.binewsian.repository.ActivityRepository;
import com.binewsian.repository.CommentRepository;
import com.binewsian.repository.NewsRepository;
import com.binewsian.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final ActivityRepository activityRepository;

    public void create(CommentRequest request, User user) throws BiNewsianException {
        if (request.contentType() == null) {
            throw new BiNewsianException("Commentable ID is required.");
        }

        if (request.contentId() == null) {
            throw new BiNewsianException("Commentable type is required.");
        }

        switch (request.contentType()) {
            case "News" -> {
                if (!newsRepository.existsById(request.contentId())) {
                    throw new BiNewsianException(AppConstant.NEWS_NOT_FOUND);
                }
            }
            case "Activity" -> {
                if (!activityRepository.existsById(request.contentId())) {
                    throw new BiNewsianException(AppConstant.ACTIVITY_NOT_FOUND);
                }
            }
            case "Forum" -> {
                
            }
        }

        if (request.content() == null || request.content().isBlank()) {
            throw new BiNewsianException("Content cannot be empty.");
        }

        Comment comment = new Comment();

        comment.setContentId(request.contentId());
        comment.setContentType(request.contentType());
        comment.setContent(request.content());
        comment.setUser(user);

        Long parentCommentId = request.parentId();

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new BiNewsianException("Parent comment not found."));

            comment.setParent(parentComment);
        }

        commentRepository.save(comment);
    }

    @Override
    public void update(Long id, CommentRequest request, User user) throws BiNewsianException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BiNewsianException("Comment not found."));

        comment.setContent(request.content());

        commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) throws BiNewsianException {

    }

    @Override
    public Page<Comment> findPaginated(int page, int size, Long contentId, String contentType) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return commentRepository.findByContentIdAndContentTypeAndParentNull(contentId, contentType, pageable);
    }

    @Override
    public Page<Comment> findReplies(int page, int size, Long parentId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return commentRepository.findByParentId(parentId, pageable);
    }
}
