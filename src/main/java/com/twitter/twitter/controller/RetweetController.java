package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.RetweetRequest;
import com.twitter.twitter.dto.response.MessageResponse;
import com.twitter.twitter.dto.response.RetweetResponse;
import com.twitter.twitter.service.RetweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retweet")
@RequiredArgsConstructor
public class RetweetController {

    private final RetweetService retweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetweetResponse createRetweet(
            @Valid @RequestBody RetweetRequest request) {
        return retweetService.createRetweet(request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteRetweet(@PathVariable Long id) {
        retweetService.deleteRetweet(id);
        return new MessageResponse("Retweet deleted successfully");
    }
}
