package services;

import dao.TeamDao;
import model.Team;

import javax.persistence.Persistence;
import java.util.List;

public class TeamService {
    public TeamDao teamDao;

    public TeamService(){
        try{
            teamDao = new TeamDao(Persistence.createEntityManagerFactory("footballChampionship"));
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    public void addTeam(Team team) {
        teamDao.create(team);
    }

    public List<Team> getAllTeams(){
        return teamDao.findAll();
    }

    public void updateTeam(Team team){
        teamDao.update(team);
    }

    public Team findTeam(String teamName){
        return teamDao.findSpecificTeam(teamName);
    }
}
