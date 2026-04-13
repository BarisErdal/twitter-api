package com.twitter.twitter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(

        @JsonProperty("user")
        UserResponse user
) {
}
