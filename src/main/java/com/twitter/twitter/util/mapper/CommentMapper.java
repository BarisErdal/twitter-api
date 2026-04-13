package com.twitter.twitter.util.mapper;

import com.twitter.twitter.dto.response.CommentResponse;
import com.twitter.twitter.dto.response.UserResponse;
import com.twitter.twitter.entity.Comment;
import com.twitter.twitter.entity.User;

public class CommentMapper {

    public CommentResponse toResponseDto(Comment comment) {
        User commentUser = comment.getUser();

        UserResponse userResponse = new UserResponse(
                commentUser.getId(),
                commentUser.getUsername(),
                commentUser.getEmail(),
                commentUser.getBio(),
                commentUser.getTweets() == null ? 0 : commentUser.getTweets().size()
        );

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                userResponse,
                comment.getTweet().getId()
        );
    }
}
