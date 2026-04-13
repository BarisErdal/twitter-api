package com.twitter.twitter.repository;

import com.twitter.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByUserIdOrderByIdDesc(Long userId);

    @Query("SELECT t FROM Tweet t " +
            "LEFT JOIN FETCH t.user " +
            "WHERE t.id = :id")
    Optional<Tweet> findByIdWithDetails(@Param("id") Long id);
}
