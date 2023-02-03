package com.example.cev.web;

import com.example.cev.domain.Match;
import com.example.cev.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("match")
@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return this.matchService.createMatch(match);
    }

    @GetMapping("team/{id}")
    public List<Match> findAllMatchesByTeamId(@PathVariable Long id) {
        return this.matchService.findAllMatchesByTeamId(id);
    }

}
