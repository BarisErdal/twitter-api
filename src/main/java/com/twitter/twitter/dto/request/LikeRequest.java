package com.twitter.twitter.dto.request;



import jakarta.validation.constraints.NotNull;

public record LikeRequest(

        @NotNull(message = "Tweet ID is required")
        Long tweetId
) {}