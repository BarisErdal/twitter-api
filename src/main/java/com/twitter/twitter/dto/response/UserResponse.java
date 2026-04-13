package com.twitter.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(

        Long id,
        String username,
        String email,
        String bio,
        @JsonProperty("tweet_count")
        int tweetCount
) {
}
