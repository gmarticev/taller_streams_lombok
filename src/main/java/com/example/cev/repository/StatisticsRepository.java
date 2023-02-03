package com.example.cev.repository;

import com.example.cev.domain.TeamStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<TeamStatistics, Long> {
    TeamStatistics findByTeam_Id(Long id);
}
