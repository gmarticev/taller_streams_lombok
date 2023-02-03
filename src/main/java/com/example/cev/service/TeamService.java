package com.example.cev.service;

import com.example.cev.domain.Team;
import com.example.cev.domain.TeamStatistics;
import com.example.cev.repository.StatisticsRepository;
import com.example.cev.repository.TeamRepository;
import com.example.cev.web.exceptions.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, StatisticsRepository statisticsRepository) {
        this.teamRepository = teamRepository;
        this.statisticsRepository = statisticsRepository;
    }

    public Team createTeam(Team team ) {
        if (team.getName() == null || team.getName().isBlank()) {
            throw new CustomError("El nombre está vacío");
        }
        if (team.getCity() == null || team.getCity().isBlank()) {
            throw new CustomError("La ciudad está vacía");
        }
        if (team.getOwner() == null || team.getOwner().isBlank()) {
            throw new CustomError("El propietario es obligatorio");
        }
        return this.teamRepository.save(team);
    }

    public List<Team> findAll() {
        return this.teamRepository.findAllByDeletedDateIsNull();
    }

    public Team updateTeam(Team team) {
        this.validateTeamId(team.getId());
        return this.createTeam(team);
    }

    public void deleteTeamById(Long id) {
        Team team = this.validateTeamId(id);
        team.setDeletedDate(LocalDateTime.now());
        this.teamRepository.save(team);
    }

    public Team findById(Long id) {
        if (id != null && id > 0) {
            Optional<Team> optionalTeam = this.teamRepository.findById(id);
//            if (optionalTeam.isPresent()) {
//                return optionalTeam.get();
//            }
//            return null;
            return optionalTeam.orElse(null);
        }
        throw new CustomError("El id no es valido");
    }

    public TeamStatistics findStatisticsByTeamId(Long teamId) {
        return this.teamRepository.findTeamStatisticsByTeam_Id(teamId);
//        return this.statisticsRepository.findByTeam_Id(teamId);
    }

    public List<?> prueba() {
        List<Team> teamList = this.teamRepository.findAll();

        Team team = new Team();
        team.setId(1L);
        team.setName("hola");
        team.setCity("city");
        team.setOwner("owner");

        Team team2 = Team.builder().id(1L).name("hola").city("city").owner("owner").build();


//        for (int i = 0; i < teamList.size(); i++) {
////            String name = teamList.get(i).getName();
//            teamList.get(i).setName(teamList.get(i).getName() + " -");
//        }

//        teamList.forEach(team -> team.setName(team.getName() + " -"));
//        List<Long> list = teamList.stream().map(team -> team.getId()).collect(Collectors.toList());
//        List<Long> list = teamList.stream().map(Team::getId).collect(Collectors.toList());
//        return teamList.stream().filter( team -> team.getCity().equalsIgnoreCase("Madrid")).collect(Collectors.toList());
//        return teamList.stream().anyMatch( team -> team.getCity().equalsIgnoreCase("Madrid"));
//        return Arrays.asList(teamList.stream().filter(team -> team.getCity().equalsIgnoreCase("Barsovia")).findFirst().orElse(null));
        return null;
    }

    private Team validateTeamId( Long id ) {
        if (id == null || id <= 0) {
            throw new CustomError("El id no es válido");
        }
        Optional<Team> optionalTeam = this.teamRepository.findById(id);
        if (optionalTeam.isEmpty()) {
            throw new CustomError("No se ha encontrado el equipo a actualizar");
        }
        return optionalTeam.get();
    }
}
