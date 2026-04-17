package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.CommentRequest;
import com.twitter.twitter.dto.response.CommentResponse;
import com.twitter.twitter.entity.Comment;
import com.twitter.twitter.entity.Tweet;
import com.twitter.twitter.entity.User;
import com.twitter.twitter.exception.ResourceNotFoundException;
import com.twitter.twitter.exception.UnauthorizedException;
import com.twitter.twitter.repository.CommentRepository;
import com.twitter.twitter.repository.TweetRepository;
import com.twitter.twitter.repository.UserRepository;
import com.twitter.twitter.security.SecurityUtils;
import com.twitter.twitter.util.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private final CommentMapper commentMapper = new CommentMapper();

    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest request) {
        User currentUser = findCurrentUser();
        Tweet tweet = tweetRepository.findById(request.tweetId())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", request.tweetId()));

        Comment comment = new Comment();
        comment.setTweet(tweet);
        comment.setUser(currentUser);
        comment.setContent(request.content());

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponseDto(savedComment);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long id, CommentRequest request) {
        User currentUser = findCurrentUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));

        validateCommentOwner(comment, currentUser.getId(), "update");
        validateTweetAssociation(comment, request.tweetId());

        comment.setContent(request.content());
        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toResponseDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        User currentUser = findCurrentUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));

        validateDeletePermission(comment, currentUser.getId());
        commentRepository.delete(comment);

    }

    private User findCurrentUser() {
        Long currentUserId = securityUtils.getCurrentUserId();
        return userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId));
    }

    private void validateCommentOwner(Comment comment, Long currentUserId, String action) {
        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException(
                    "You are not authorized to " + action + " this comment");
        }
    }

    private void validateDeletePermission(Comment comment, Long currentUserId) {
        Long commentOwnerId = comment.getUser().getId();
        Long tweetOwnerId = comment.getTweet().getUser().getId();

        //tweet sahibi veya yorum sahibi silebilir
        if (!commentOwnerId.equals(currentUserId) && !tweetOwnerId.equals(currentUserId)) {
            throw new UnauthorizedException(
                    "You are not authorized to delete this comment");
        }
    }

    private void validateTweetAssociation(Comment comment, Long tweetId) {
        if (!comment.getTweet().getId().equals(tweetId)) {
            throw new IllegalStateException("A comment cannot be moved to a different tweet");
        }
    }
}
