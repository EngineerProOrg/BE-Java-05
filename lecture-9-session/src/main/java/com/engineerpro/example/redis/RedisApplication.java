package com.engineerpro.example.redis;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.example.redis.model.Article;
import com.engineerpro.example.redis.model.Category;
import com.engineerpro.example.redis.repository.ArticleRepository;
import com.engineerpro.example.redis.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class RedisApplication {

	static CategoryRepository categoryRepository;
	static ArticleRepository articleRepository;

	static Category createCategory(String name) {
		Category category = Category.builder().name(name).build();
		categoryRepository.save(category);
		log.info("created category {}={}", category.getId(), category.getName());
		return category;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RedisApplication.class, args);

		categoryRepository = context.getBean(CategoryRepository.class);
		articleRepository = context.getBean(ArticleRepository.class);

		try {
			Category entertainmentCategory = createCategory("Entertainment");
			Category sportCategory = createCategory("Sport");

			articleRepository
					.save(Article.builder().url("spider-man-live-action")
							.title("Spider-Man: Live-Action")
							.content("...").category(entertainmentCategory).build());

			articleRepository
					.save(Article.builder().url("superman")
							.title("Superman")
							.content("...").category(entertainmentCategory).build());
		} catch (Exception e) {
			log.error("Cannot create seed data");
		}
	}

}
