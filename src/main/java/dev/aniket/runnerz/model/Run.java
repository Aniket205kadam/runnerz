package dev.aniket.runnerz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer runId;
    @NotEmpty
    private String title;
    private LocalDateTime startedOn;
    private LocalDateTime completedOn;
    @Positive
    private Double kilometers;
    private Location location;

    private String runCode;
    @NotEmpty
    private String userName;
}

/*Here we also use the record*/