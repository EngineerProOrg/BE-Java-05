package com.engineerpro.example.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.example.redis.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
  Category findOneByName(String name);
}
