package com.twitter.twitter.util.mapper;

import com.twitter.twitter.dto.response.LikeResponse;
import com.twitter.twitter.dto.response.UserResponse;
import com.twitter.twitter.entity.Like;
import com.twitter.twitter.entity.User;

public class LikeMapper {

    public LikeResponse toResponseDto(Like like) {
        User likeUser = like.getUser();

        UserResponse userResponse = new UserResponse(
                likeUser.getId(),
                likeUser.getUsername(),
                likeUser.getEmail(),
                likeUser.getBio(),
                likeUser.getTweets() == null ? 0 : likeUser.getTweets().size()
        );

        return new LikeResponse(
                like.getId(),
                like.getTweet().getId(),
                userResponse
        );
    }
}
