package com.twitter.twitter.util.mapper;

import com.twitter.twitter.dto.request.TweetRequest;
import com.twitter.twitter.dto.response.CommentResponse;
import com.twitter.twitter.dto.response.TweetResponse;
import com.twitter.twitter.dto.response.UserResponse;
import com.twitter.twitter.entity.Comment;
import com.twitter.twitter.entity.Tweet;

import java.util.List;

public class TweetMapper {

    public TweetResponse toResponseDto(Tweet tweet) {
        return toResponseDto(tweet, true);
    }

    public TweetResponse toResponseDto(Tweet tweet, boolean includeComments) {
        List<CommentResponse> comments = includeComments && tweet.getComments() != null
                ? tweet.getComments().stream().map(this::toCommentResponseDto).toList()
                : List.of();

        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                toUserResponseDto(tweet.getUser()),
                tweet.getLikes() == null ? 0 : tweet.getLikes().size(),
                tweet.getComments() == null ? 0 : tweet.getComments().size(),
                tweet.getRetweets() == null ? 0 : tweet.getRetweets().size(),
                comments
        );
    }

    public Tweet toEntity(TweetRequest tweetRequest) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.content());
        return tweet;
    }

    private CommentResponse toCommentResponseDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                toUserResponseDto(comment.getUser()),
                comment.getTweet().getId()
        );
    }

    private UserResponse toUserResponseDto(com.twitter.twitter.entity.User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getTweets() == null ? 0 : user.getTweets().size()
        );
    }
}
