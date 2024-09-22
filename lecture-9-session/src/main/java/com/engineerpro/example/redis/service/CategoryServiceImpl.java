package com.engineerpro.example.redis.service;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.dto.GetCategoryArticlesRequest;
import com.engineerpro.example.redis.dto.GetCategoryArticlesResponse;
import com.engineerpro.example.redis.exception.CategoryNotFoundException;
import com.engineerpro.example.redis.model.Category;
import com.engineerpro.example.redis.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
  private static final int CACHE_TIME_IN_MINUTE = 5;
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public GetCategoryArticlesResponse getArticles(GetCategoryArticlesRequest request)
      throws CategoryNotFoundException, IOException {

    String cacheKey = String.format("category:%d:articles",
        request.getCategoryId());
    String cachedData = redisTemplate.opsForValue().get(cacheKey);
    GetCategoryArticlesResponse response;
    if (Objects.isNull(cachedData)) {
      log.info("cache miss, get from mysql");
      Optional<Category> optionalCategory = categoryRepository.findById(request.getCategoryId());
      if (optionalCategory.isEmpty()) {
        throw new CategoryNotFoundException();
      }
      Category category = optionalCategory.get();
      response = GetCategoryArticlesResponse.builder().articles(category.getArticles())
          .build();
      redisTemplate.opsForValue().set(cacheKey,
          objectMapper.writeValueAsString(response),
          Duration.ofMinutes(CACHE_TIME_IN_MINUTE));
    } else {
      log.info("cache hit");
      response = objectMapper.readValue(cachedData,
          GetCategoryArticlesResponse.class);
    }
    return response;
  }

}
