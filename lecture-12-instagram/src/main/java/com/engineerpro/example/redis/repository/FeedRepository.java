package com.engineerpro.example.redis.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FeedRepository {
    private static final String FEED_KEY_PREFIX = "feed:";

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    public Long getFeedSize(int profileId) {
        String feedKey = FEED_KEY_PREFIX + profileId;
        return redisTemplate.opsForList().size(feedKey);
    }

    public void addPostToFeed(int postId, int profileId) {
        String feedKey = FEED_KEY_PREFIX + profileId;
        redisTemplate.opsForList().leftPush(feedKey, Long.valueOf(postId));
        // uncomment if need to limit the number of posts in the feed
        // redisTemplate.opsForList().trim(feedKey, 0, 1000); // Keep only the latest
        // 1000 posts
    }

    public List<Long> getFeed(int profileId, int limit, int page) {
        String feedKey = FEED_KEY_PREFIX + profileId;
        int start = (page - 1) * limit;
        int end = start + limit - 1;
        return redisTemplate.opsForList().range(feedKey, start, end);
    }
}
