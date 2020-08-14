package services;

import dao.PlayerDao;
import model.Player;

import javax.persistence.Persistence;
import java.util.List;

public class PlayerService {
    public PlayerDao playerDao;

    public PlayerService(){
        try{
            playerDao = new PlayerDao(Persistence.createEntityManagerFactory("footballChampionship"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addPlayer(Player player){ playerDao.create(player);}

    public void updatePlayer(Player player){
        playerDao.update(player);
    }

    public Player findPlayer(String playerName){
        return playerDao.findAfterName(playerName);
    }

    public List<Player> getAllPlayers(){
        return playerDao.findAll();
    }

    public List<Player> getAllSpecificPlayers(int idTeam){
        return playerDao.findAllSpecific(idTeam);
    }
}
