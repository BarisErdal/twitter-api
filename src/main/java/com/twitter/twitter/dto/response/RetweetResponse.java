package com.twitter.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RetweetResponse(

        Long id,

        @JsonProperty("original_tweet_id")
        Long originalTweetId,

        @JsonProperty("original_tweet_content")
        String originalTweetContent,

        @JsonProperty("original_tweet_owner")
        UserResponse originalTweetOwner,

        @JsonProperty("retweeted_by")
        UserResponse retweetedBy,

        String comment
) {
}
