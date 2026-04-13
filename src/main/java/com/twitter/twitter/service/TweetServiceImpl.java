package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.TweetRequest;
import com.twitter.twitter.dto.response.TweetResponse;
import com.twitter.twitter.entity.Tweet;
import com.twitter.twitter.entity.User;
import com.twitter.twitter.exception.ResourceNotFoundException;
import com.twitter.twitter.exception.UnauthorizedException;
import com.twitter.twitter.repository.TweetRepository;
import com.twitter.twitter.repository.UserRepository;
import com.twitter.twitter.security.SecurityUtils;
import com.twitter.twitter.util.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    private final TweetMapper tweetMapper = new TweetMapper();

    @Override
    @Transactional
    public TweetResponse createTweet(TweetRequest request) {
        User user = findCurrentUser();
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(request.content());
        Tweet savedTweet = tweetRepository.save(tweet);
        return tweetMapper.toResponseDto(savedTweet, false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TweetResponse> findByUserId(Long userId) {
        log.info("Fetching tweets for userId: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId);
        }
        return tweetRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(tweet -> tweetMapper.toResponseDto(tweet, false))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TweetResponse findById(Long id) {
        log.info("Fetching tweet with id: {}", id);
        Tweet tweet = tweetRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", id));
        return tweetMapper.toResponseDto(tweet, true);
    }

    @Override
    @Transactional
    public TweetResponse updateTweet(Long id, TweetRequest request) {
        User currentUser = findCurrentUser();
        log.info("Updating tweet {} by userId: {}", id, currentUser.getId());
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", id));
        validateOwnership(tweet.getUser().getId(), currentUser.getId(), "update");
        tweet.setContent(request.content());
        Tweet updatedTweet = tweetRepository.save(tweet);
        log.info("Tweet {} updated successfully", id);
        return tweetMapper.toResponseDto(updatedTweet, false);
    }

    @Override
    @Transactional
    public void deleteTweet(Long id) {
        User currentUser = findCurrentUser();
        log.info("Deleting tweet {} by userId: {}", id, currentUser.getId());
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", id));
        validateOwnership(tweet.getUser().getId(), currentUser.getId(), "delete");
        tweetRepository.delete(tweet);
        log.info("Tweet {} deleted successfully", id);
    }

    private User findCurrentUser() {
        Long currentUserId = securityUtils.getCurrentUserId();
        return userRepository.findById(currentUserId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", currentUserId));
    }

    private void validateOwnership(Long ownerUserId,
                                   Long currentUserId,
                                   String action) {
        if (!ownerUserId.equals(currentUserId)) {
            throw new UnauthorizedException(
                    "You are not authorized to " + action + " this tweet");
        }
    }
}
