package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.LikeRequest;
import com.twitter.twitter.dto.response.LikeResponse;
import com.twitter.twitter.dto.response.MessageResponse;
import com.twitter.twitter.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponse createLike(
            @Valid @RequestBody LikeRequest request) {
        return likeService.createLike(request);
    }

    @PostMapping("/dislike")
    public MessageResponse dislike(@Valid @RequestBody LikeRequest request) {
        likeService.dislike(request);
        return new MessageResponse("Like removed successfully");
    }
}
