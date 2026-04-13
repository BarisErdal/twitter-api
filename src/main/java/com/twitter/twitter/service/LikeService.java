package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.LikeRequest;
import com.twitter.twitter.dto.response.LikeResponse;

public interface LikeService {
    LikeResponse createLike(LikeRequest request);
    void dislike(LikeRequest request);
}
