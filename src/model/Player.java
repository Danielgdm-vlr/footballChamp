package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "players", schema = "football")
public class Player implements Comparable<Player>{
    private int idPlayer;
    private String playerName;
    private int matchesPlayed;
    private int goalsScoredPlayer;
    private int idTeam;

    @Id
    @Column(name = "idPlayer")
    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    @Basic
    @Column(name = "playerName")
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Basic
    @Column(name = "matchesPlayed")
    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    @Basic
    @Column(name = "goalsScoredPlayer")
    public int getGoalsScoredPlayer() {
        return goalsScoredPlayer;
    }

    public void setGoalsScoredPlayer(int goalsScoredPlayer) {
        this.goalsScoredPlayer = goalsScoredPlayer;
    }

    @Basic
    @Column(name = "idTeam")
    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return idPlayer == player.idPlayer &&
                matchesPlayed == player.matchesPlayed &&
                goalsScoredPlayer == player.goalsScoredPlayer &&
                idTeam == player.idTeam &&
                Objects.equals(playerName, player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlayer, playerName, matchesPlayed, goalsScoredPlayer, idTeam);
    }

    public int compareTo(Player player){
        int compareGoals = ((Player) player).getGoalsScoredPlayer();
        return compareGoals - this.goalsScoredPlayer;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", matchesPlayed=" + matchesPlayed +
                ", goalsScoredPlayer=" + goalsScoredPlayer +
                ", idTeam=" + idTeam +
                '}';
    }
}
