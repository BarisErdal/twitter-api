package com.twitter.twitter.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RetweetRequest(

        @NotNull(message = "Tweet ID is required")
        Long tweetId,

        @Size(max = 280, message = "Quote comment cannot exceed 280 characters")
        String comment
) {}