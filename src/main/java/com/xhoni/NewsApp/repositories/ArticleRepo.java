package com.xhoni.NewsApp.repositories;

import com.xhoni.NewsApp.models.Article;
import com.xhoni.NewsApp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends CrudRepository<Article, Long> {
    List<Article> findAll();
    List<Article> findAllByAddedArticle(User user);

}
