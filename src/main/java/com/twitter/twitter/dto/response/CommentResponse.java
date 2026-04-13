package com.twitter.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentResponse(

        Long id,
        String content,
        UserResponse user,
        @JsonProperty("tweet_id")
        Long tweetId

) {
}
