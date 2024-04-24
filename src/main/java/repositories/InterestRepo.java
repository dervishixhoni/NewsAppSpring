package repositories;

import models.Interest;
import models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepo extends CrudRepository<Interest, Long> {
    List<Interest> findAll();
    List<Interest> findAllByAddedInterest(User user);

}
