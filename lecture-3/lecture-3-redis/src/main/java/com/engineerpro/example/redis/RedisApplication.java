package com.engineerpro.example.redis;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.example.redis.model.Article;
import com.engineerpro.example.redis.model.Author;
import com.engineerpro.example.redis.model.Category;
import com.engineerpro.example.redis.repository.ArticleRepository;
import com.engineerpro.example.redis.repository.AuthorRepository;
import com.engineerpro.example.redis.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class RedisApplication {

	static CategoryRepository categoryRepository;
	static ArticleRepository articleRepository;
	static AuthorRepository authorRepository;

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
		authorRepository = context.getBean(AuthorRepository.class);

		Category entertainmentCategory = createCategory("Entertainment");
		Category sportCategory = createCategory("Sport");

		Author authorA = Author.builder().name("Nguyen Van A").build();
		Author authorB = Author.builder().name("Dinh Van B").build();
		Author authorC = Author.builder().name("Tran Van C").build();

		authorRepository.save(authorA);
		authorRepository.save(authorB);
		authorRepository.save(authorC);

		articleRepository
				.save(Article.builder().url("spider-man-live-action")
						.title("Spider-Man: Live-Action")
						.content("...").category(entertainmentCategory)
						.authors(List.of(authorA, authorB))
						.build());

		articleRepository
				.save(Article.builder().url("superman")
						.title("Superman")
						.content("...").category(entertainmentCategory)
						.authors(List.of(authorA, authorC))
						.build());
		log.info("created all records");
		// list the article by an author
		Author author = authorRepository.findById(authorA.getId()).get();
		// for (Article article : author.getArticles()) {
		// 	log.info(String.format("author A: article - %s", article.getId()));
		// }
	}
}
