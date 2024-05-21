package dev.aniket.runnerz.service;

import dev.aniket.runnerz.Repository.RunRepo;
import dev.aniket.runnerz.model.Location;
import dev.aniket.runnerz.model.Run;
import dev.aniket.runnerz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RunService {
    private RunRepo repo;
    private UserService userService;
    private UniqueKeyService uniqueKeyService;

    @Autowired
    public void setRepo(RunRepo repo) {
        this.repo = repo;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUniqueKeyService(UniqueKeyService uniqueKeyService) {
        this.uniqueKeyService = uniqueKeyService;
    }


    public Run createRun(Integer userId, Run run) {
        if (!userService.userExist(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is not found.");
        }
        String key = uniqueKeyGenerator(5);
        //this key is assign to the run
        run.setRunCode(key);

        //this key is added to the user
        User user = userService.getUser(userId);
        List<String> existingUserRuns = user.getRuns();
        if (existingUserRuns == null) {
            existingUserRuns = new ArrayList<>();
            existingUserRuns.add(key);
        } else {
            existingUserRuns.add(key);
        }
        user.setRuns(existingUserRuns);

        //add the userName to the run
        run.setUserName(user.getName());

        if (!run.getCompletedOn().isAfter(run.getStartedOn())) {
            throw new IllegalArgumentException("Completed On must be after Started On");
        }
        return repo.save(run);
    }

    public Run updateRun(Run run, Integer runId) {
        if (!run.getCompletedOn().isAfter(run.getStartedOn())) {
            throw new IllegalArgumentException("Completed On must be after Started On");
        }
        run.setRunId(runId);

        Run exitingRun = getRun(runId);
        run.setRunCode(exitingRun.getRunCode());
        run.setUserName(exitingRun.getUserName());
        return repo.save(run);
    }

    public Run getRun(Integer runId) {
        Optional<Run> existingRun = repo.findById(runId);
        if (existingRun.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run ID is not found.");
        return existingRun.get();
    }

    public void deleteRun(Integer runId, Integer userId) {
        Run run = getRun(runId);
        User user = userService.getUser(userId);

        //get the key from the run
        String key = run.getRunCode();

        List<String> runs = user.getRuns();

        //remove the key from the user account
        runs = runs.stream().filter(n -> (!n.equals(key))).collect(Collectors.toList());
        //now added the updated runs list
        user.setRuns(runs);
        //delete the run by the ID
        repo.deleteById(runId);
    }

    public List<Run> getUserRuns() {
        return repo.findAll();
    }

    public List<Run> findByLocation(String location) {
        Location enumLocation = null;
        if (location.equalsIgnoreCase("OUTDOOR"))
            enumLocation = Location.OUTDOOR;
        else if (location.equalsIgnoreCase("INDOOR"))
            enumLocation = Location.INDOOR;
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter only OUTDOOR and INDOOR request");
        return repo.findAllByLocation(enumLocation);
    }

    //generate unique key
    private String uniqueKeyGenerator(Integer stringSize) {
        Random random = new Random();
        char[] chars = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        String key = "";

        loop:while(true) {
            for (int i = 0; i < stringSize; i++) {
                key += chars[random.nextInt(chars.length)];
            }

            if (uniqueKeyService.checkKey(key)) {
                break loop;
            }
        }
        return key;
    }

}
