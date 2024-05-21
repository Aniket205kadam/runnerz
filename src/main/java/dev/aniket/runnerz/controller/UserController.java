package dev.aniket.runnerz.controller;

import dev.aniket.runnerz.model.Run;
import dev.aniket.runnerz.model.User;
import dev.aniket.runnerz.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    //get user by the ID
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer userId) {
        return service.getUser(userId);
    }

    //get users

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("")
    public List<User> getUsers() {
        return service.getUsers();
    }

    //add user
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public User addUser(@Valid @RequestBody User user) {
        return service.addUser(user);
    }

    //update user
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable("id") Integer userId) {
        return service.updateUser(user, userId);
    }

    //delete user
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer userId) {
        service.DeleteUser(userId);
    }

    @GetMapping("/{userId}/runs")
    public List<Run> userRuns(@PathVariable Integer userId) {
        return service.getUserRuns(userId);
    }
}
