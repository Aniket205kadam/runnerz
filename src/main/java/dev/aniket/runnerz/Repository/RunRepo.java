package dev.aniket.runnerz.Repository;

import dev.aniket.runnerz.model.Location;
import dev.aniket.runnerz.model.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RunRepo extends JpaRepository<Run, Integer> {

    List<Run> findAllByLocation(Location location);

    Run findByRunCode(String runCode);

}
