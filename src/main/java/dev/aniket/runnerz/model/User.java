package dev.aniket.runnerz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @NotEmpty
    private String name;
    @Email
    private String emailId;
    private LocalDate dataOfBirth;
    @Positive
    private Double age;
    //weight in kg
    @Positive
    private Double weight;
    //height in cm
    @Positive
    private Double height;
    private String city;
    private List<String> runs;
}
