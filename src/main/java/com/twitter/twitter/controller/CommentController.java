package com.twitter.twitter.controller;

import com.twitter.twitter.dto.request.CommentRequest;
import com.twitter.twitter.dto.response.CommentResponse;
import com.twitter.twitter.dto.response.MessageResponse;
import com.twitter.twitter.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(
            @Valid @RequestBody CommentRequest request) {
        return commentService.createComment(request);
    }

    @PutMapping("/{id}")
    public CommentResponse updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request) {
        return commentService.updateComment(id, request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new MessageResponse("Comment deleted successfully");
    }
}
