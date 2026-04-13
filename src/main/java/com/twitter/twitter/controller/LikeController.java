package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.LikeRequest;
import com.twitter.twitter.dto.response.ApiResponse;
import com.twitter.twitter.dto.response.LikeResponse;
import com.twitter.twitter.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<LikeResponse>> createLike(
            @Valid @RequestBody LikeRequest request) {
        LikeResponse response = likeService.createLike(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Like created successfully"));
    }

    @PostMapping("/dislike")
    public ResponseEntity<ApiResponse<Void>> dislike(@Valid @RequestBody LikeRequest request) {
        likeService.dislike(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Like removed successfully"));
    }
}
