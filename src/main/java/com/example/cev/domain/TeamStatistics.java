package com.example.cev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatistics {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JsonIgnoreProperties({"teamStatistics"})
    private Team team;
    private int totalMatches;
    private int totalGoals;
    private double goalsPerMatch;
}
