package com.xhoni.NewsApp.services;

import com.xhoni.NewsApp.models.Article;
import com.xhoni.NewsApp.models.Interest;
import com.xhoni.NewsApp.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhoni.NewsApp.repositories.ArticleRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public static JSONArray getFilteredArticles(ArrayList<Interest> interests) throws IOException {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < interests.size() - 1; i++) {
            word.append(interests.get(i).getKey_Word());
            word.append("+OR+");
        }
        word.append(interests.getLast().getKey_Word());
        String url = "https://newsapi.org/v2/everything?q=" + word + "&apiKey=" + env.API_KEY;
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject object = new JSONObject(response.toString());
        JSONArray articles = object.getJSONArray("articles");
        JSONArray filteredArticles = new JSONArray();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            if (!article.getString("title").equals("[Removed]") && !article.isNull("urlToImage")) {
                filteredArticles.put(article);
            }
        }
        return filteredArticles;
    }
}


