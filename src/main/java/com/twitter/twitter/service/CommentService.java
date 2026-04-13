package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.CommentRequest;
import com.twitter.twitter.dto.response.CommentResponse;

public interface CommentService {
    CommentResponse createComment(CommentRequest request);
    CommentResponse updateComment(Long id, CommentRequest request);
    void deleteComment(Long id);
}
