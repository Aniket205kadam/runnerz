package dev.aniket.runnerz.service;

import dev.aniket.runnerz.Repository.RunRepo;
import dev.aniket.runnerz.Repository.UserRepo;
import dev.aniket.runnerz.model.Run;
import dev.aniket.runnerz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepo repo;

    private RunRepo runRepo;

    @Autowired
    public void setRunRepo(RunRepo runRepo) {
        this.runRepo = runRepo;
    }

    @Autowired
    public void setRepo(UserRepo repo) {
        this.repo = repo;
    }


    public User addUser(User user) {
        //check user age to access this application then you age is greater the 5 years
        if (user.getAge() <= 5){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oops! It looks like the age you entered isn't valid. Please double-check and enter a valid age.");
        }
        //check user height
        if (user.getHeight() == 0.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oops! It looks like the height you entered isn't valid. Please double-check and enter a valid height in centimeters.");
        }
        //check user weight
        if (user.getWeight() == 0.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oops! It looks like the weight you entered isn't valid. Please double-check and enter a valid weight in kilograms.");
        }
        //check user email, this email is already present in the Database or not
        if (!emailChecker(user.getEmailId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ID is already in use. Please try another one.");
        }
        return repo.save(user);
    }

    public User updateUser(User user, Integer userId) {
        user.setUserId(userId);
        return repo.save(user);
    }

    public void DeleteUser(Integer userId) {
        repo.deleteById(userId);
    }

    public User getUser(Integer userId) {
        Optional<User> existingUser = repo.findById(userId);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The given user ID is not found.");
        }
        return existingUser.get();
    }

    public List<User> getUsers() {
        return repo.findAll();
    }

    private Boolean emailChecker(String emailId) {
        User user = repo.findByEmailId(emailId);
        if (user == null) return true;
        else return false;
    }

    public Boolean userExist(Integer userId) {
        if (repo.findById(userId).isEmpty()) return false;
        return true;
    }

    public List<Run> getUserRuns(Integer userId) {
        User user = repo.findUserRuns(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User is not exist.");
        }
        List<String> userRunsCodes = user.getRuns();
        return getUserRuns(userRunsCodes);
    }
    public List<Run> getUserRuns(List<String> runCodes) {
        List<Run> runs = new ArrayList<>();
        for (String key : runCodes) {
            runs.add(runRepo.findByRunCode(key));
        }
        return runs;
    }
}
