package com.example.cev.repository;

import com.example.cev.domain.Team;
import com.example.cev.domain.TeamStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByDeletedDateIsNull();

    @Query("select t.teamStatistics from Team t where t.id = ?1")
    TeamStatistics findTeamStatisticsByTeam_Id(Long id);

}
