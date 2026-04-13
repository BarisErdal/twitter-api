package com.twitter.twitter.util.mapper;

import com.twitter.twitter.dto.response.RetweetResponse;
import com.twitter.twitter.dto.response.UserResponse;
import com.twitter.twitter.entity.Retweet;
import com.twitter.twitter.entity.Tweet;
import com.twitter.twitter.entity.User;

public class RetweetMapper {

    public RetweetResponse toResponseDto(Retweet retweet) {
        Tweet originalTweet = retweet.getTweet();
        User originalTweetOwner = originalTweet.getUser();
        User retweetedByUser = retweet.getUser();

        UserResponse originalTweetOwnerResponse = new UserResponse(
                originalTweetOwner.getId(),
                originalTweetOwner.getUsername(),
                originalTweetOwner.getEmail(),
                originalTweetOwner.getBio(),
                originalTweetOwner.getTweets() == null ? 0 : originalTweetOwner.getTweets().size()
        );

        UserResponse retweetedByResponse = new UserResponse(
                retweetedByUser.getId(),
                retweetedByUser.getUsername(),
                retweetedByUser.getEmail(),
                retweetedByUser.getBio(),
                retweetedByUser.getTweets() == null ? 0 : retweetedByUser.getTweets().size()
        );

        return new RetweetResponse(
                retweet.getId(),
                originalTweet.getId(),
                originalTweet.getContent(),
                originalTweetOwnerResponse,
                retweetedByResponse,
                retweet.getComment()
        );
    }
}
