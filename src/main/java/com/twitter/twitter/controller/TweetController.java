package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.TweetRequest;
import com.twitter.twitter.dto.response.ApiResponse;
import com.twitter.twitter.dto.response.TweetResponse;
import com.twitter.twitter.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<ApiResponse<TweetResponse>> createTweet(
            @Valid @RequestBody TweetRequest request) {
        TweetResponse response = tweetService.createTweet(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tweet created successfully"));
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<ApiResponse<List<TweetResponse>>> findByUserId(
            @RequestParam Long userId) {
        List<TweetResponse> response = tweetService.findByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(response, "Tweets fetched successfully"));
    }

    @GetMapping("/findById")
    public ResponseEntity<ApiResponse<TweetResponse>> findById(@RequestParam Long id) {
        TweetResponse response = tweetService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Tweet fetched successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TweetResponse>> updateTweet(
            @PathVariable Long id,
            @Valid @RequestBody TweetRequest request) {
        TweetResponse response = tweetService.updateTweet(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Tweet updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Tweet deleted successfully"));
    }
}
