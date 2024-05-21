package dev.aniket.runnerz.service;

import dev.aniket.runnerz.Repository.UniqueKeyRepo;
import dev.aniket.runnerz.model.UniqueKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueKeyService {
    private UniqueKeyRepo repo;

    @Autowired
    public void setRepo(UniqueKeyRepo repo) {
        this.repo = repo;
    }

    public Boolean checkKey(String key) {
        UniqueKey exitingKey = repo.findByKey(key);
        if (exitingKey == null) {
            return true;
        }
        return false;
    }
}
