package controller;

import jakarta.servlet.http.HttpSession;
import javax.validation.Valid;

import models.Article;
import models.Interest;
import models.LoginUser;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private InterestService interestService;

    @GetMapping("/")
    public String index(@ModelAttribute("user") User user, HttpSession session, Model model){
        if (session.getAttribute("userId")!=null){
            model.addAttribute("user",userService.findUserId((Long) session.getAttribute("userId")));
            return "redirect:/dashboard";
        }
        return "index";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result,
                               HttpSession session, Model model) {
        User createUsers = userService.register(user, result);
        if (result.hasErrors()) {
            return "redirect:/";
        }
        session.setAttribute("userId", createUsers.getId());
        return "redirect:/verifyemail";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result,
                        HttpSession session, Model model) {
        User loggedUser = userService.loggin(loginUser, result);
        if (result.hasErrors()) {
            return "redirect:/";
        }
        session.setAttribute("userId", loggedUser.getId());
        return "redirect:/home";
    }

    @GetMapping("/verifyemail")
    public String verifyEmail(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        if (user.getIsVerified()) {
            return "redirect:/home";
        }
        model.addAttribute("user", user);
        return "verify";
    }

    @PostMapping("/activateaccount")
    public String activateAccount(@ModelAttribute("user") User POSTuser, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        if (user.getIsVerified()) {
            return "redirect:/getstarted";
        }
        if (POSTuser.getVerificatonCode().equals(user.getVerificatonCode())) {
            user.setIsVerified(true);
            userService.updateUser(user);
            return "redirect:/getstarted";
        }
        return "redirect:/verifyemail";
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
        user.setVerificatonCode(userService.generateCode());
        userService.updateUser(user);
        return "redirect:/verifyemail";
    }



    @GetMapping("/home")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        if (!userService.findUserId(userId).getIsVerified()) {
            return "redirect:/verifyemail";
        }
        ArrayList interests = new ArrayList(interestService.getByUser(userService.findUserId(userId)));
        String word = "";
        for (int i = 0; i < interests.size()-1; i++) {
            word += interests.get(i)+"OR+";
        }
        word += interests.get(interests.size()-1);
        String url = "https://newsapi.org/v2/everything?q="+word+"&apiKey="+env.API_KEY;
        String json = IOUtils.toString(url.getBytes(), "UTF-8");
        JSONObject object = new JSONObject(new JSONTokener(json));
        model.addAttribute("articles", object.getJSONArray("articles"));
        model.addAttribute("user", userService.findUserId(userId));
        model.addAttribute("interests", interests);
        model.addAttribute("savedArticles", articleService.getByUser(userService.findUserId(userId)));
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/getstarted")
    public String getStarted(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        if (!user.getIsVerified()) {
            return "redirect:/verifyemail";
        }
        if (interestService.getByUser(user).size() > 0){
            return "redirect:/home";
        }
        model.addAttribute("user", user);
        return "getstarted";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        if (id != (Long) session.getAttribute("userId")) {
            return "redirect:/logout";
        }
        model.addAttribute("user", userService.findUserId(id));
        model.addAttribute("interests", interestService.getByUser(userService.findUserId(id)));
        model.addAttribute("articles", articleService.getByUser(userService.findUserId(id)));
        return "profile";
    }

    @PostMapping("/editprofile")
    public String editProfile(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "redirect:/profile/"+user.getId();
        }
        User editUser = userService.findUserId(user.getId());
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setEmail(user.getEmail());
        userService.updateUser(editUser);
        return "redirect:/profile/"+user.getId();
    }

    @PostMapping("/editpassword")
    public String editPassword(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "redirect:/profile/"+user.getId();
        }
        User editUser = userService.findUserId(user.getId());
        editUser.setPassword(user.getPassword());
        userService.updateUser(editUser);
        return "redirect:/profile/"+user.getId();
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

    @PostMapping("/deleteArticle")
    public String deleteArticle(@ModelAttribute("article") Article article, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        Article deleteArticle = articleService.getArticleById(article.getId());
        if (deleteArticle.getAddedArticle().getId() != user.getId()) {
            return "redirect:/home";
        }
        articleService.deletearticle(deleteArticle);
        return "redirect:/home";
    }

    @PostMapping("/editArticle")
    public String editArticle(@ModelAttribute("article") Article article, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        Article editArticle = articleService.getArticleById(article.getId());
        if (editArticle.getAddedArticle().getId() != user.getId()) {
            return "redirect:/profile/"+user.getId();
        }
        model.addAttribute("article", editArticle);
        return "redirect:/profile/"+user.getId();
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
        return "redirect:/profile/"+user.getId();
    }

    @PostMapping("/deleteInterest")
    public String deleteInterest(@ModelAttribute("interest") Interest interest, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findUserId(userId);
        Interest deleteInterest = interestService.getInterestById(interest.getId());
        if (deleteInterest.getAddedInterest().getId() != user.getId()) {
            return "redirect:/profile/"+user.getId();
        }
        interestService.deleteinterest(deleteInterest);
        return "redirect:/profile/"+user.getId();
    }

    @PostMapping("/setInterest")
    public String setInterest(@ModelAttribute("interests") String interests, HttpSession session, Model model) {
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