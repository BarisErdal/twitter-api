package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.RetweetRequest;
import com.twitter.twitter.dto.response.RetweetResponse;

public interface RetweetService {
    RetweetResponse createRetweet(RetweetRequest request);
    void deleteRetweet(Long id);
}
