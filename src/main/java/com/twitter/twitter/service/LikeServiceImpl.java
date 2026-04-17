package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.LikeRequest;
import com.twitter.twitter.dto.response.LikeResponse;
import com.twitter.twitter.entity.Like;
import com.twitter.twitter.entity.Tweet;
import com.twitter.twitter.entity.User;
import com.twitter.twitter.exception.DuplicateResourceException;
import com.twitter.twitter.exception.ResourceNotFoundException;
import com.twitter.twitter.repository.LikeRepository;
import com.twitter.twitter.repository.TweetRepository;
import com.twitter.twitter.repository.UserRepository;
import com.twitter.twitter.security.SecurityUtils;
import com.twitter.twitter.util.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private final LikeMapper likeMapper = new LikeMapper();

    @Override
    @Transactional
    public LikeResponse createLike(LikeRequest request) {
        User currentUser = findCurrentUser();
        Tweet tweet = tweetRepository.findById(request.tweetId())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", request.tweetId()));

        if (likeRepository.existsByTweetIdAndUserId(tweet.getId(), currentUser.getId())) {
            throw new DuplicateResourceException("You have already liked this tweet");
        }

        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(currentUser);

        Like savedLike = likeRepository.save(like);
        //log.info("Like {} created by userId: {} for tweetId: {}", savedLike.getId(), currentUser.getId(), tweet.getId());

        return likeMapper.toResponseDto(savedLike);
    }

    @Override
    @Transactional
    public void dislike(LikeRequest request) {
        User currentUser = findCurrentUser();
        Tweet tweet = tweetRepository.findById(request.tweetId())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", request.tweetId()));

        Like like = likeRepository.findByTweetIdAndUserId(tweet.getId(), currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Like not found for tweet id: " + tweet.getId() + " and user id: " + currentUser.getId()));

        likeRepository.delete(like);
       // log.info("Like {} removed by userId: {} for tweetId: {}", like.getId(), currentUser.getId(), tweet.getId());
    }

    private User findCurrentUser() {
        Long currentUserId = securityUtils.getCurrentUserId();
        return userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId));
    }
}
