package com.twitter.twitter.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TweetRequest(

        @NotBlank(message = "Tweet content is required")
        @Size(min = 1, max = 280, message = "Tweet must be between 1 and 280 characters")
        String content
) {}