package com.xhoni.NewsApp.services;

import com.xhoni.NewsApp.models.Article;
import com.xhoni.NewsApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhoni.NewsApp.repositories.ArticleRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }


    //find all
    public List<Article> getAllArticle(){
        return articleRepo.findAll();
    }
    public Article getArticleById(Long id){
        Optional<Article> optional = articleRepo.findById(id);
        return optional.orElse(null);

    }


    public List<Article> getByUser(User user){

        return articleRepo.findAllByAddedArticle(user);
    }

    public Article createArticle(Article article){
        return articleRepo.save(article);
    }

    public void  deletearticle(Article article)
    {
        articleRepo.delete(article);
    }

    public ArrayList<String> getArticleUrls(List<Article> articles){
        ArrayList<String> urls = new ArrayList<>();
        for (Article article : articles){
            urls.add(article.getUrl());
        }
        return urls;
    }
}


