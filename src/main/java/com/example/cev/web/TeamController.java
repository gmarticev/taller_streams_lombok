package com.example.cev.web;

import com.example.cev.domain.Team;
import com.example.cev.domain.TeamStatistics;
import com.example.cev.service.MatchService;
import com.example.cev.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("team")
@RestController
public class TeamController {

    private final TeamService teamService;
    private final MatchService matchService;

    @Autowired
    public TeamController(TeamService teamService, MatchService matchService) {
        this.teamService = teamService;
        this.matchService = matchService;
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return this.teamService.createTeam(team);
    }

    @GetMapping
    public List<Team> findAllTeams() {
        return this.teamService.findAll();
    }

    @PutMapping
    public Team updateTeam(@RequestBody Team team) {
        return this.teamService.updateTeam(team);
    }

    @GetMapping("statistics/{id}")
    public TeamStatistics findStatisticsByTeamId(@PathVariable Long id) {
        return this.teamService.findStatisticsByTeamId(id);
    }

    @GetMapping("goals/{id}")
    public Integer countTotalTeamGoals(@PathVariable Long id) {
        return this.matchService.countTotalTeamGoals(id);
    }

    @DeleteMapping("{id}")
    public void deleteTeam(@PathVariable Long id) {
        this.teamService.deleteTeamById(id);
    }

    // localhost:8080/team/prueba
    @GetMapping("prueba")
    public List<?> prueba() {
        return this.teamService.prueba();
    }

}
