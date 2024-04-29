package com.xhoni.NewsApp.repositories;

import com.xhoni.NewsApp.models.Interest;
import com.xhoni.NewsApp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepo extends CrudRepository<Interest, Long> {
    List<Interest> findAll();
    List<Interest> findAllByAddedInterest(User user);

}
