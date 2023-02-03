package com.example.cev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String owner;
    @OneToOne
    @JsonIgnoreProperties({"team"})
    private TeamStatistics teamStatistics;
    @JsonIgnore
    private LocalDateTime deletedDate;
}
