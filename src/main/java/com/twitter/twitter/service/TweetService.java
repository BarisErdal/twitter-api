package com.twitter.twitter.service;



import com.twitter.twitter.dto.request.TweetRequest;
import com.twitter.twitter.dto.response.TweetResponse;

import java.util.List;

public interface TweetService {
    TweetResponse createTweet(TweetRequest request);
    List<TweetResponse> findByUserId(Long userId);
    TweetResponse findById(Long id);
    TweetResponse updateTweet(Long id, TweetRequest request);
    void deleteTweet(Long id);
}
