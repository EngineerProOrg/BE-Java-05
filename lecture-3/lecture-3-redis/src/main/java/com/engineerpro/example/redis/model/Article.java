package com.engineerpro.example.redis.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(unique = true, name = "url", nullable = false, length = 200)
  private String url;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  // @JsonManagedReference
  private Category category;

  @ManyToMany
  @JoinTable(name = "article_author", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
  // @JsonManagedReference
  private List<Author> authors;
}
