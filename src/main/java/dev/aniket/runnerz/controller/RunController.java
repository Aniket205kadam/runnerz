package dev.aniket.runnerz.controller;

import dev.aniket.runnerz.model.Run;
import dev.aniket.runnerz.service.RunService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/runs")
public class RunController {

    private RunService service;

    @Autowired
    public void setService(RunService service) {
        this.service = service;
    }

    //create the run record
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/user/{id}")
    public Run createRun(@Valid @RequestBody Run run, @PathVariable("id") Integer userId) {
        return service.createRun(userId, run);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/user/{id}")
    public Run updateRun(@Valid @RequestBody Run run, @PathVariable("id") Integer runId) {
        return service.updateRun(run, runId);
    }

    @GetMapping("/{id}")
    public Run getRun(@PathVariable("id") Integer runId) {
         return service.getRun(runId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{runId}/user/{userId}")
    public void deleteRun(@PathVariable Integer runId, @PathVariable Integer userId) {
        service.deleteRun(runId, userId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("")
    public List<Run> getRuns() {
        return service.getUserRuns();
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/location/{location}")
    public List<Run> findByLocation(@PathVariable String location) {
        return service.findByLocation(location);
    }
}