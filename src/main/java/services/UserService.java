package services;
import models.LoginUser;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import repositories.UserRepo;

import java.util.List;
import java.util.Optional;

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
        if (result.hasErrors()){
            return null;
        }
        String hashed = BCrypt.hashpw(createUser.getPassword(), BCrypt.gensalt());
        createUser.setPassword(hashed);
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
}
