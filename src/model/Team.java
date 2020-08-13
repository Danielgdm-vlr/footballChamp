package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teams", schema = "football")
public class Team implements Comparable<Team> {
    private int idTeam;
    private String teamName;
    private int matchesPlayed;
    private int points;
    private int goalsScoredTeam;
    private int goalsDifference;

    @Id
    @Column(name = "idTeam")
    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    @Basic
    @Column(name = "teamName")
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Basic
    @Column(name = "goalsScoredTeam")
    public int getGoalsScoredTeam() {
        return goalsScoredTeam;
    }

    public void setGoalsScoredTeam(int goalsScoredTeam) {
        this.goalsScoredTeam = goalsScoredTeam;
    }

    @Basic
    @Column(name = "goalsDifference")
    public int getGoalsDifference() {
        return goalsDifference;
    }

    public void setGoalsDifference(int goalsDifference) {
        this.goalsDifference = goalsDifference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return idTeam == team.idTeam &&
                matchesPlayed == team.matchesPlayed &&
                points == team.points &&
                goalsScoredTeam == team.goalsScoredTeam &&
                goalsDifference == team.goalsDifference &&
                Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeam, teamName, matchesPlayed, points, goalsScoredTeam, goalsDifference);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", matchesPlayed=" + matchesPlayed +
                ", points=" + points +
                ", goalsScoredTeam=" + goalsScoredTeam +
                ", goalsDifference=" + goalsDifference +
                '}';
    }

    public int compareTo(Team team) {
        int comparePoints = ((Team)team).getGoalsScoredTeam();
        return comparePoints - this.goalsScoredTeam;
    }
}
