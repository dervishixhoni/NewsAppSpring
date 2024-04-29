package com.xhoni.NewsApp.services;

import com.xhoni.NewsApp.models.LoginUser;
import com.xhoni.NewsApp.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.xhoni.NewsApp.repositories.UserRepo;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //find all
    public List<User> findUser(){
        return userRepo.findAll();
    }
    //find by id
    public User findUserId(Long id){
        Optional<User> optional = userRepo.findById(id);
        return optional.orElse(null);
    }

    public Optional<User> findbyEmail(String email){
        return userRepo.findByEmail(email);
    }
    //create
    public User createUser(User user){
        return userRepo.save(user);
    }
    //update
    public User updateUser(User user){
        return userRepo.save(user);
    }
    //delete
    public void deleteUserId(Long id){
        userRepo.deleteById(id);
    }

    //register
    public User register(User createUser , BindingResult result) {
        Optional<User> optionalUser = findbyEmail(createUser.getEmail());
        if (optionalUser.isPresent()){
            result.rejectValue("email", "Match", "Email already take!");
        }
        if (!createUser.getPassword().equals(createUser.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "Match", "Passwords must match!");
        }
        if (result.hasErrors()){
            return null;
        }
        String code = generateCode();
        sendEmail(createUser.getEmail(),code);
        String hashed = BCrypt.hashpw(createUser.getPassword(), BCrypt.gensalt());
        createUser.setPassword(hashed);
        createUser.setVerificationCode(code);
        createUser.setIsVerified(false);
        return userRepo.save(createUser);
    }

    public User loggin(LoginUser loginUser, BindingResult result){
        Optional<User> optionalUser = userRepo.findByEmail(loginUser.getEmail());
        if (optionalUser.isEmpty()){
            result.rejectValue("email", "Match", "Email must match!");
            return null;
        }
        User user = optionalUser.get();
        if (!BCrypt.checkpw(loginUser.getPassword(), user.getPassword())){
            result.rejectValue("password", "Match", "Passwords must match!");
            return null;
        }
        return user;
    }

    public User verify(User user, User userId, BindingResult result){
        System.out.println("Incoming verification code: "+user.getVerificationCode()+"\nUser verification code: "+userId.getVerificationCode());
        if (!user.getVerificationCode().equals(userId.getVerificationCode())){
            result.rejectValue("verificationCode", "Match", "Verification code must match!");
            System.out.println(result.getAllErrors());
            return null;
        }
        userId.setIsVerified(true);
        return userRepo.save(userId);
    }

    public void sendEmail(String emailAddr, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(env.fromEmail, env.password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, emailAddr,"Verify Your Email", "Use this verification code to activate your account: "+verificationCode);
    }
    public boolean isAllowedFile(String filename){
        return Arrays.asList(env.allowedExtensions).contains(filename.substring(filename.lastIndexOf(".")));
    }
    public String generateCode(){
        String string = "0123456789ABCDEFGHIJKELNOPKQSTUV";
        StringBuilder vCode = new StringBuilder("");
        Random rand = new Random();
        for (int i=0;i<6;i++){
            vCode.append(string.charAt(rand.nextInt(string.length())));
        }
        return vCode.toString();
    }
}
