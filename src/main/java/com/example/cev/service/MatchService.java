package com.example.cev.service;

import com.example.cev.domain.Match;
import com.example.cev.domain.Team;
import com.example.cev.domain.TeamStatistics;
import com.example.cev.repository.MatchRepository;
import com.example.cev.repository.StatisticsRepository;
import com.example.cev.web.exceptions.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final StatisticsRepository statisticsRepository;
    private final TeamService teamService;

    @Autowired
    public MatchService(MatchRepository matchRepository, StatisticsRepository statisticsRepository, TeamService teamService) {
        this.matchRepository = matchRepository;
        this.statisticsRepository = statisticsRepository;
        this.teamService = teamService;
    }

    public Match createMatch( Match match ) {
        if (match.getHomeTeam() == null || match.getAwayTeam() == null) {
            throw new CustomError("El equipo esta mal");
        }
        Team homeTeam = this.teamService.findById(match.getHomeTeam().getId());
        Team awayTeam = this.teamService.findById(match.getAwayTeam().getId());

        if (homeTeam == null || awayTeam == null) {
            throw new CustomError("No se han encontrado los equipos");
        }
        if (Objects.equals(homeTeam.getId(), awayTeam.getId())) {
            throw new CustomError("Los equipos no pueden ser los mismos");
        }
        Match matchSaved = this.matchRepository.save(match);

        // Actualizar estadísticas del primer equipo
        TeamStatistics homeTeamStatistics = this.getTeamStatistics(homeTeam);
        homeTeamStatistics = this.statisticsRepository.save(homeTeamStatistics);
        homeTeam.setTeamStatistics(homeTeamStatistics);
        this.teamService.updateTeam(homeTeam);


        // Actualizar estadísticas del equipo visitante
        TeamStatistics awayTeamStatistics = this.getTeamStatistics(awayTeam);
        awayTeamStatistics = this.statisticsRepository.save(awayTeamStatistics);
        awayTeam.setTeamStatistics(awayTeamStatistics);
        this.teamService.updateTeam(awayTeam);

        return matchSaved;
    }

    private TeamStatistics getTeamStatistics(Team team) {
        TeamStatistics statistics = new TeamStatistics();
        List<Match> matches = this.matchRepository.findAll();
        statistics.setTeam(team);

        // Contar total goles
        // condA ? A : B
        /*
            if (condA) {
                A
            } else {
                B
            }
         */
        int totalGoals = matches.stream()
                // Filtrar (match -> Un elemento de la lista matches)
                .filter(match -> match.getHomeTeam().getId().equals(team.getId()) || match.getAwayTeam().getId().equals(team.getId()))
                // Mapear (match -> Un elemento de la lista matches)
                .mapToInt(match -> match.getHomeTeam().getId().equals(team.getId()) ? match.getHomeGoals() : match.getAwayGoals()) //IntStream<1,3,4,5>
                // Sumar todos los elementos de la lista
                .sum(); // 1 + 3 + 4 + 5 = 13
        statistics.setTotalGoals(totalGoals);

        // Contar total partidos
        int totalMatches = (int) matches.stream()
                // Filtrar  (match -> Un elemento de la lista matches)
                .filter( match -> match.getHomeTeam().getId().equals(team.getId()) || match.getAwayTeam().getId().equals(team.getId()))
                // Contar el total de elementos del stream
                .count();
        statistics.setTotalMatches(totalMatches);

        // Contar goler x partido
        statistics.setGoalsPerMatch(totalGoals / (double) totalMatches);

        return statistics;
    }

    public List<Match> findAllMatchesByTeamId(Long id) {
        // Todas las llamadas que hagáis son más rápidas en BBDD
        return this.matchRepository.findAllByHomeTeam_IdIsOrAwayTeam_IdIs(id, id);
    }

    public Integer countTotalTeamGoals(Long id) {
        List<Match> matchesByTeam = this.findAllMatchesByTeamId(id);
        return matchesByTeam.stream()
        .mapToInt(match -> match.getHomeTeam().getId().equals(id) ? match.getHomeGoals() : match.getAwayGoals())
        .sum();
    }
}
