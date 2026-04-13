package com.twitter.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeResponse(

        Long id,
        @JsonProperty("tweet_id")
        Long tweetId,
        UserResponse user

) {
}
