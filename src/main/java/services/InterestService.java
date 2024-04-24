package services;

import models.Interest;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.InterestRepo;

import java.util.List;
import java.util.Optional;

@Service
public class InterestService {

    @Autowired
    private InterestRepo interestRepo;

    public InterestService(InterestRepo interestRepo) {
        this.interestRepo = interestRepo;
    }


    //find all
    public List<Interest> getAllInterest(){
        return interestRepo.findAll();
    }
    public Interest getInterestById(Long id){
        Optional<Interest> optional = interestRepo.findById(id);
        return optional.orElse(null);

    }


    public List<Interest> getByUser(User user){

        return interestRepo.findAllByAddedInterest(user);
    }

    public Interest createInterest(Interest interest){
        return interestRepo.save(interest);
    }

    public void  deleteinterest(Interest interest)
    {
        interestRepo.delete(interest);
    }
}

