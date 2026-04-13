package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.TweetRequest;
import com.twitter.twitter.dto.response.MessageResponse;
import com.twitter.twitter.dto.response.TweetResponse;
import com.twitter.twitter.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponse createTweet(
            @Valid @RequestBody TweetRequest request) {
        return tweetService.createTweet(request);
    }

    @GetMapping("/findByUserId")
    public List<TweetResponse> findByUserId(
            @RequestParam Long userId) {
        return tweetService.findByUserId(userId);
    }

    @GetMapping("/findById")
    public TweetResponse findById(@RequestParam Long id) {
        return tweetService.findById(id);
    }

    @PutMapping("/{id}")
    public TweetResponse updateTweet(
            @PathVariable Long id,
            @Valid @RequestBody TweetRequest request) {
        return tweetService.updateTweet(id, request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return new MessageResponse("Tweet deleted successfully");
    }
}
