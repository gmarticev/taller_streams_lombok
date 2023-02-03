package com.example.cev.repository;

import com.example.cev.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("select m from Match m where m.homeTeam.id = ?1 or m.awayTeam.id = ?2")
    List<Match> findAllByHomeTeam_IdIsOrAwayTeam_IdIs(Long homeTeamId, Long awayTeamId);


}
