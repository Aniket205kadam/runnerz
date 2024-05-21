package dev.aniket.runnerz.Repository;

import dev.aniket.runnerz.model.UniqueKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniqueKeyRepo extends JpaRepository<UniqueKey, Integer> {
    UniqueKey findByKey(String key);
}
