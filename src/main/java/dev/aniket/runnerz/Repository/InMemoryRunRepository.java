package dev.aniket.runnerz.Repository;


import dev.aniket.runnerz.model.Location;
import dev.aniket.runnerz.model.Run;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRunRepository {
    private List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    public Optional<Run> findById(Integer runId) {
        return runs.stream()
                .filter(n -> (n.getRunId().equals(runId)))
                .findFirst();
    }

    //create
    public Run save(Run run) {
        runs.add(run);
        return runs.get(run.getRunId()-1);
    }

    //update
    public void update(Run run) {
        Optional<Run> existingRun = findById(run.getRunId());
        if (existingRun.isPresent()) {
            System.out.println(existingRun.get().getRunId());
            runs.set(runs.indexOf(existingRun.get()), run);

        }
    }

    //delete
    public void delete(Integer runId) {
        Optional<Run> existingRun = runs.stream().filter(n -> n.getRunId() == runId).findFirst();
        if (existingRun.isPresent()) {
            runs.remove(existingRun.get());
        }
    }

//    @PostConstruct
//    private void init() {
//        runs.add(new Run(1,
//                "Monday Morining Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(30),
//                3.0,
//                Location.INDOOR));
//
//        runs.add(new Run(2,
//                "Wednesday Evening Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(60),
//                2.0,
//                Location.OUTDOOR));
//    }

}
/*  @PostConstruct   */
//When the class is the Bean then this annotation is works
//When this class Bean is created that time this @PostConstruct method is called automatically

/* init(): This method is annotated with @PostConstruct and is called after the RunRepository bean is fully initialized. It populates the runs list with sample data.*/