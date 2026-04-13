package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.RetweetRequest;
import com.twitter.twitter.dto.response.ApiResponse;
import com.twitter.twitter.dto.response.RetweetResponse;
import com.twitter.twitter.service.RetweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retweet")
@RequiredArgsConstructor
public class RetweetController {

    private final RetweetService retweetService;

    @PostMapping
    public ResponseEntity<ApiResponse<RetweetResponse>> createRetweet(
            @Valid @RequestBody RetweetRequest request) {
        RetweetResponse response = retweetService.createRetweet(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Retweet created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRetweet(@PathVariable Long id) {
        retweetService.deleteRetweet(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Retweet deleted successfully"));
    }
}
