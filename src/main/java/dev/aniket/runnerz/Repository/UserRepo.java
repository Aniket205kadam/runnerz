package dev.aniket.runnerz.Repository;

import dev.aniket.runnerz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmailId(String emailId);

    //"select s from Student s where s.name = ?1
    @Query("SELECT s FROM User s WHERE s.userId = ?1")
    User findUserRuns(Integer userId);
}
