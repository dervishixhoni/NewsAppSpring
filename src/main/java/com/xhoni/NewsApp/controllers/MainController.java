package com.xhoni.NewsApp.controllers;

import com.xhoni.NewsApp.models.*;
import com.xhoni.NewsApp.services.ArticleService;
import com.xhoni.NewsApp.services.InterestService;
import com.xhoni.NewsApp.services.UserService;
import com.xhoni.NewsApp.services.env;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private InterestService interestService;

    @GetMapping("/")
    public String index(@ModelAttribute("user") User user, @ModelAttribute("loginUser") LoginUser loginUser, HttpSession session, Model model) {
        if (session.getAttribute("userId") != null) {
            model.addAttribute("user", userService.findUserId((Long) session.getAttribute("userId")));
            return "redirect:/home";
        }
        model.addAttribute("user", new User());
        model.addAttribute("loginUser", new LoginUser());
        return "index";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result,
                               HttpSession session, Model model) {
        User createUsers = userService.register(user, result);
        if (result.hasErrors()) {
            model.addAttribute("loginUser", new LoginUser());
            return "index";
        }
        session.setAttribute("userId", createUsers.getId());
        return "redirect:/verifyemail";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result,
                        HttpSession session, Model model) {
        User loggedUser = userService.loggin(loginUser, result);
        if (result.hasErrors()) {
            model.addAttribute("user", new User());
            return "index";
        }
        session.setAttribute("userId", loggedUser.getId());
        return "redirect:/verifyemail";
    }

    @GetMapping("/verifyemail")
    public String verifyEmail(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        if (user.getIsVerified()) {
            if (interestService.getByUser(user).isEmpty()) {
                return "redirect:/getstarted";
            }
            return "redirect:/home";
        }
        model.addAttribute("user", user);
        return "verify";
    }

    @PostMapping("/activateaccount")
    public String activateAccount(@ModelAttribute("user") User POSTuser, BindingResult result, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        userService.verify(POSTuser, userService.findUserId(userId), result);
        if (result.hasErrors()) {
            return "redirect:/verifyemail";
        }
        return "redirect:/getstarted";

    }

    @GetMapping("/resendcode")
    public String resendCode(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        if (user.getIsVerified()) {
            return "redirect:/getstarted";
        }
        user.setVerificationCode(userService.generateCode());
        userService.updateUser(user);
        return "redirect:/verifyemail";
    }


    @GetMapping("/home")
    public String dashboard(Model model, HttpSession session) throws IOException {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        if (!userService.findUserId(userId).getIsVerified()) {
            return "redirect:/verifyemail";
        }
        ArrayList<Interest> interests = new ArrayList<>(interestService.getByUser(userService.findUserId(userId)));
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < interests.size() - 1; i++) {
            word.append(interests.get(i).getKey_Word());
            word.append("+OR+");
        }
        word.append(interests.get(interests.size() - 1).getKey_Word());
        String url = "https://newsapi.org/v2/everything?q=" + word + "&apiKey=" + env.API_KEY;
//        String json = IOUtils.toString(url.getBytes(), "UTF-8");
//        JSONObject object = new JSONObject(new JSONTokener(json));
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

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
        model.addAttribute("newArticle", new Article());
        model.addAttribute("articles", filteredArticles);
        model.addAttribute("user", userService.findUserId(userId));
        model.addAttribute("keywords", interests);
        model.addAttribute("savedArticlesObject", articleService.getByUser(userService.findUserId(userId)));
        model.addAttribute("savedArticles", articleService.getArticleUrls(articleService.getByUser(userService.findUserId(userId))));
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/getstarted")
    public String getStarted(@ModelAttribute("interests") InterestString interests, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        if (!user.getIsVerified()) {
            return "redirect:/verifyemail";
        }
        if (!interestService.getByUser(user).isEmpty()) {
            return "redirect:/home";
        }
        model.addAttribute("user", user);
        model.addAttribute("interests", new InterestString());
        return "getstarted";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        if (!Objects.equals(id, session.getAttribute("userId"))) {
            return "redirect:/logout";
        }
        model.addAttribute("interest", new Interest());
        model.addAttribute("user", userService.findUserId(id));
        model.addAttribute("interests", interestService.getByUser(userService.findUserId(id)));
        model.addAttribute("articles", articleService.getByUser(userService.findUserId(id)));
        model.addAttribute("passwordReset", new PasswordReset());
        return "profile";
    }

    @PostMapping("/editProfile")
    public String editProfile(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        User editUser = userService.findUserId((Long) session.getAttribute("userId"));
        System.out.println(editUser.getId());
        System.out.println(user.getId());
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setEmail(user.getEmail());
        userService.updateUser(editUser);
        return "redirect:/profile/" + session.getAttribute("userId");
    }

    @PostMapping("/editPassword")
    public String editPassword(@Valid @ModelAttribute("passwordReset") PasswordReset passwordReset, BindingResult result, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "redirect:/profile/" + session.getAttribute("userId");
        }
        User editUser = userService.findUserId((Long) session.getAttribute("userId"));
        editUser.setPassword(passwordReset.getNewPassword());
        userService.updateUser(editUser);
        return "redirect:/profile/" + session.getAttribute("userId");
    }

    @PostMapping("/saveArticle")
    public String saveArticle(@ModelAttribute("article") Article article, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        article.setAddedArticle(user);
        articleService.createArticle(article);
        return "redirect:/home";
    }

    @PostMapping("/unsaveArticle")
    public String deleteArticle(@ModelAttribute("article") Article article, HttpSession session, Model model, HttpServletRequest request){
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        Article deleteArticle = articleService.getArticleById(article.getId());
        articleService.deletearticle(deleteArticle);
        return "redirect:"+ request.getHeader("Referer");
    }

    @PostMapping("/editArticle")
    public String editArticle(@ModelAttribute("article") Article article, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        Article editArticle = articleService.getArticleById(article.getId());
        if (!Objects.equals(editArticle.getAddedArticle().getId(), user.getId())) {
            return "redirect:/profile/" + user.getId();
        }
        model.addAttribute("article", editArticle);
        return "redirect:/profile/" + user.getId();
    }

    @PostMapping("/addInterest")
    public String addInterest(@ModelAttribute("interest") Interest interest, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        interest.setAddedInterest(user);
        interestService.createInterest(interest);
        return "redirect:/profile/" + user.getId();
    }

    @PostMapping(value = "/deleteInterest")
    public String deleteInterest(@ModelAttribute("interest") Interest interest, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        Interest deleteInterest = interestService.getInterestById(interest.getId());
        interestService.deleteinterest(deleteInterest);
        return "redirect:/profile/" + user.getId();
    }

    @PostMapping("/setInterests")
    public String setInterest(@RequestParam("interests") String interests, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        for (String word : interests.split(",")) {
            Interest interest = new Interest();
            interest.setKey_Word(word);
            interest.setAddedInterest(user);
            interestService.createInterest(interest);
        }
        return "redirect:/home";
    }
}