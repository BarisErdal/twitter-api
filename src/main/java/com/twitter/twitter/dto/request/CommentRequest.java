package com.twitter.twitter.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequest(

        @NotNull(message = "Tweet ID is required")
        Long tweetId,

        @NotBlank(message = "Comment content is required")
        @Size(min = 1, max = 280, message = "Comment must be between 1 and 280 characters")
        String content
) {}