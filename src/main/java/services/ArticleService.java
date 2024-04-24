package services;

import models.Article;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ArticleRepo;

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
}

