package com.twitter.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TweetResponse(

        Long id,
        String content,
        UserResponse user,
        @JsonProperty("like_count")
        int likeCount,
        @JsonProperty("comment_count")
        int commentCount,
        @JsonProperty("retweet_count")
        int retweetCount,
        List<CommentResponse> comments
) {
}
