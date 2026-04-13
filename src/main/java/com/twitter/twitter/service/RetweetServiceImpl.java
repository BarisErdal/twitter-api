package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.RetweetRequest;
import com.twitter.twitter.dto.response.RetweetResponse;
import com.twitter.twitter.entity.Retweet;
import com.twitter.twitter.entity.Tweet;
import com.twitter.twitter.entity.User;
import com.twitter.twitter.exception.DuplicateResourceException;
import com.twitter.twitter.exception.ResourceNotFoundException;
import com.twitter.twitter.exception.UnauthorizedException;
import com.twitter.twitter.repository.RetweetRepository;
import com.twitter.twitter.repository.TweetRepository;
import com.twitter.twitter.repository.UserRepository;
import com.twitter.twitter.security.SecurityUtils;
import com.twitter.twitter.util.mapper.RetweetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private final RetweetMapper retweetMapper = new RetweetMapper();

    @Override
    @Transactional
    public RetweetResponse createRetweet(RetweetRequest request) {
        User currentUser = findCurrentUser();
        Tweet tweet = tweetRepository.findById(request.tweetId())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", request.tweetId()));

        if (retweetRepository.existsByTweetIdAndUserId(tweet.getId(), currentUser.getId())) {
            throw new DuplicateResourceException("You have already retweeted this tweet");
        }

        Retweet retweet = new Retweet();
        retweet.setTweet(tweet);
        retweet.setUser(currentUser);
        retweet.setComment(request.comment());

        Retweet savedRetweet = retweetRepository.save(retweet);
        log.info("Retweet {} created by userId: {} for tweetId: {}",
                savedRetweet.getId(), currentUser.getId(), tweet.getId());

        return retweetMapper.toResponseDto(savedRetweet);
    }

    @Override
    @Transactional
    public void deleteRetweet(Long id) {
        User currentUser = findCurrentUser();
        Retweet retweet = retweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retweet", id));

        validateOwnership(retweet, currentUser.getId());
        retweetRepository.delete(retweet);
        log.info("Retweet {} deleted by userId: {}", id, currentUser.getId());
    }

    private User findCurrentUser() {
        Long currentUserId = securityUtils.getCurrentUserId();
        return userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", currentUserId));
    }

    private void validateOwnership(Retweet retweet, Long currentUserId) {
        if (!retweet.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to delete this retweet");
        }
    }
}
