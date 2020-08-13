package championship;

import model.Player;
import model.Team;
import services.PlayerService;
import services.TeamService;

import java.util.*;

public class Messages {
    //just messages and options(next 4 methods)
    public void startingMessage(){
        System.out.println(" ------------------------------ ");
        System.out.println(" --- Football Championship! --- ");
        System.out.println(" ------------------------------ ");
        System.out.println("The rules of this championship are: ");
        System.out.println("1 - There are 3 Players in each team.");
        System.out.println("2 - Points system: win - 3 points, lose - 0 points, tie - 1 point");
        System.out.println("3 - Teams are ordered in descending order, after the number of points. If two teams have equal points then the teams will be ordered " +
                "descending after the number of goals scored.");
    }

    public void championshipAllOptions(){
        System.out.println(" --- ");
        System.out.println("Press 1 to add a team.");
        System.out.println("Press 2 to set up matches.");
        System.out.println("Press 3 to see the teams stats and standings.");
        System.out.println("Press 4 to see the players stats.");
        System.out.println("Press 5 to see the top scorers in the league.");
        System.out.println("Press 0 to exit the application.");
        System.out.println(" --- ");
    }

    public void championshipSecondaryOption3(){
        System.out.println(" --- ");
        System.out.println("Press 1 to see the stats of a specific team.");
        System.out.println("Press 2 to see the league standings.");
        System.out.println("Press 0 to go back to main options.");
        System.out.println(" --- ");
    }

    public void championshipSecondaryOption4(){
        System.out.println(" --- ");
        System.out.println("Press 1 to see the stats of a specific player.");
        System.out.println("Press 2 to see all the players.");
        System.out.println("Press 0 to go back to main options.");
        System.out.println(" --- ");
    }

    //message for wrong input
    public void messageOptionOtherInput(){
        System.out.println(" --- ");
        System.out.println("Please enter one of the options below!");

    }

    //checking the input from the user
    public void selectedOption() {
        championshipAllOptions();
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        while(input != 0) {
            if (input == 1) {
                messageOption1();
            } else if (input == 2) {
                messageOption2();
            } else if (input == 3) {
                messageOption3();
            } else if(input == 4) {
                messageOption4();
            } else if(input == 5) {
                messageOption5();
            }else {
                messageOptionOtherInput();
            }
            championshipAllOptions();
            input = sc.nextInt();
        }
    }

    //adding new teams and players(next 5 methods)
    public void messageOption1(){
        System.out.print("Please enter the name of the team: ");
        Scanner sc = new Scanner(System.in);
        String teamName = sc.nextLine();

        //this method creates a team with a given name, and with 0 goals(scored and goals difference) and 0 games played
        addNewTeam(teamName);

        System.out.println("Team " + teamName + " successfully added to the championship!");
        System.out.println("Add the players of team " + teamName + ":");

        String playerName;
        int idTeam = getIdTeam();
        for(int i = 0; i < 3; i++){
            System.out.print("Add player " + (i + 1) + ": ");
            playerName = sc.nextLine();
            addNewPlayer(playerName, idTeam); //this method creates a player with a given name, 0 goals scored and assigns him to a team
            System.out.println("Player " + playerName + " successfully added!");
        }

        //tre sa fac sa apara doar jucatorii de la echipa respectiva
        PlayerService playerService = new PlayerService();
        List<Player> playerList = playerService.getAllPlayers();
        System.out.print("Players: ");
        for(int i = 0; i < playerList.size() - 1; i++){
            System.out.print(playerList.get(i) + ", ");
        }
        System.out.println(playerList.get(playerList.size() - 1) + " were successfully added to the team " + teamName);

        //checking if the user wants to add another team; if yes then we repeat this process, else nothing
        messageOptionAddAnotherTeam();
        int input = sc.nextInt();
        while(input != 0) {
            if (input == 1) {
                messageOption1();
                break;
            } else {
                messageOptionOtherInput();
            }
            messageOptionAddAnotherTeam();
            input = sc.nextInt();
        }
    }

    public void addNewTeam(String teamName){
        Team team = new Team();
        team.setTeamName(teamName);
        team.setMatchesPlayed(0);
        team.setPoints(0);
        team.setGoalsScoredTeam(0);
        team.setGoalsDifference(0);
        TeamService teamService = new TeamService();
        teamService.addTeam(team);
    }

    public int getIdTeam(){
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        Team team = teamList.get(teamList.size() - 1);
        return team.getIdTeam();
    }

    public void addNewPlayer(String playerName, int idTeam){
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setMatchesPlayed(0);
        player.setGoalsScoredPlayer(0);
        player.setIdTeam(idTeam);
        PlayerService playerService = new PlayerService();
        playerService.addPlayer(player);
    }

    public void messageOptionAddAnotherTeam(){
        System.out.println(" --- ");
        System.out.println("Do you want to add another team?");
        System.out.println("Press 1 for YES.");
        System.out.println("Press 0 for NO.");
        System.out.println(" --- ");
    }

    //setting up games between the teams(next methods)
    public void messageOption2(){
        Scanner sc = new Scanner(System.in);
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        System.out.println("All the teams: " + teamList);
        String homeTeam = null, awayTeam = null;
        boolean homeTeamBool = false, awayTeamBool = false;

        System.out.print("Please enter the name of the first team (home team): ");
        while(!homeTeamBool) {
            homeTeam = sc.nextLine();
            if (correctName(homeTeam)) {
                homeTeamBool = true;
            } else {
                System.out.println("Please enter the name of the team from the list: " + teamList);
            }
        }

        System.out.print("Please enter the name of the second team (away team): ");
        while(!awayTeamBool) {
            awayTeam = sc.nextLine();
            if (correctName(awayTeam)) {
                awayTeamBool = true;
            } else {
                System.out.println("Please enter the name of the team from the list: " + teamList);;
            }
        }

        System.out.println("The match started!");

        Team teamHome = findTeam(homeTeam);
        teamHome.setMatchesPlayed((teamHome.getMatchesPlayed() + 1));
        Team teamAway = findTeam(awayTeam);
        teamAway.setMatchesPlayed((teamAway.getMatchesPlayed() + 1));

        System.out.println("Please enter the final score!");
        System.out.println(" --- ");
        System.out.println("Number of goals scored by the home team (" + homeTeam + ") :");
        int homeTeamGoals = sc.nextInt();
        teamHome.setGoalsScoredTeam((teamHome.getGoalsScoredTeam() + homeTeamGoals));
        System.out.println("Number of goals scored by the away team (" + awayTeam + ") :");
        int awayTeamGoals = sc.nextInt();
        teamAway.setGoalsScoredTeam((teamAway.getGoalsScoredTeam() + awayTeamGoals));

        teamHome.setGoalsDifference((teamHome.getGoalsScoredTeam() - teamAway.getGoalsScoredTeam()));
        teamAway.setGoalsDifference((teamAway.getGoalsScoredTeam() - teamHome.getGoalsScoredTeam()));

        if(homeTeamGoals > awayTeamGoals){
            System.out.println("Home team (" + homeTeam + ") wins!");
            teamHome.setPoints((teamHome.getPoints() + 3));
        }
        else if(homeTeamGoals < awayTeamGoals){
            System.out.println("Away team (" + awayTeam + ") wins!");
            teamAway.setPoints((teamAway.getPoints() + 3));
        } else{
            System.out.println("The game " + homeTeam + " - " + awayTeam + " ended in a draw!");
            teamHome.setPoints((teamHome.getPoints() + 1));
            teamAway.setPoints((teamAway.getPoints() + 1));
        }
        teamService.updateTeam(teamHome);
        teamService.updateTeam(teamAway);

        System.out.println("Please enter the name of the players who scored in this game between " + homeTeam + " and " + awayTeam + " !");
        String playerName = null;

        PlayerService playerService = new PlayerService();
        List<Player> playerListHomeTeam = playerService.getAllSpecificPlayers(teamHome.getIdTeam());
        if(homeTeamGoals > 0) {
            System.out.println("For the home team (" + homeTeam + ")!");
            System.out.print("All the players: " + playerListHomeTeam);
            for (Player player : playerListHomeTeam) {
                player.setMatchesPlayed((player.getMatchesPlayed() + 1));
                playerService.updatePlayer(player);
            }
            System.out.println("");
            int playerGoals = 0;
            System.out.println("Remaining goals for the home team (" + homeTeam + "): " + homeTeamGoals);
            sc.nextLine();
            while(homeTeamGoals > 0) {
                System.out.println("Enter the name of the player who scored: ");
                playerName = sc.nextLine();
                for (int i = 0; i < playerListHomeTeam.size(); i++) {
                    if (playerName.equals(playerListHomeTeam.get(i).getPlayerName())) {
                        System.out.println("Enter how many goals " + playerListHomeTeam.get(i).getPlayerName() + " scored: ");
                        playerGoals = sc.nextInt();
                        boolean ok = false;
                        while (!ok) {
                            if (playerGoals <= homeTeamGoals) {
                                playerListHomeTeam.get(i).setGoalsScoredPlayer((playerListHomeTeam.get(i).getGoalsScoredPlayer() + playerGoals));
                                playerService.updatePlayer(playerListHomeTeam.get(i));
                                homeTeamGoals -= playerGoals;
                                playerGoals = 0;
                                System.out.println("Remaining goals for the home team (" + homeTeam + "): " + homeTeamGoals);
                                sc.nextLine();
                                ok = true;
                            } else {
                                System.out.println("The number of goals entered is too high!");
                            }
                        }
                    }else{
                        System.out.println("The name of the player is not correct");
                        System.out.println("All the players from team " + homeTeam + ": " + playerListHomeTeam);
                    }
                }
            }
        }else{
            System.out.println("Home team " + homeTeam + " did not score any goals!");
            for (Player player : playerListHomeTeam) {
                player.setMatchesPlayed((player.getMatchesPlayed() + 1));
                playerService.updatePlayer(player);
            }
        }

        System.out.println(" --- ");

        System.out.println("For the away team (" + awayTeam + ")!");
        List<Player> playerListAwayTeam = playerService.getAllSpecificPlayers(teamAway.getIdTeam());
        System.out.print("All the players: " + playerListAwayTeam);
        messageOptionSetUpAnotherGame();
        int input = sc.nextInt();
        while(input != 0) {
            if (input == 1) {
                messageOption2();
                break;
            } else {
                messageOptionOtherInput();
            }
            messageOptionSetUpAnotherGame();
            input = sc.nextInt();
        }

    }

    public boolean correctName(String teamName){
        boolean teamNameBool = false;
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        for(Team team : teamList){
            if((teamName.equals(team.getTeamName()))) {
                teamNameBool = true;
            }
        }
        if(teamNameBool)
            return true;
        return false;
    }

    public Team findTeam(String teamName){
        TeamService teamService = new TeamService();
        return teamService.findTeam(teamName);
    }

    public void messageOptionSetUpAnotherGame(){
        System.out.println(" --- ");
        System.out.println("Do you want to set up another game?");
        System.out.println("Press 1 for YES.");
        System.out.println("Press 0 for NO.");
        System.out.println(" --- ");
    }

    //returning team stats and championship standings(next 3 methods)
    public void messageOption3(){
        championshipSecondaryOption3();
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        while(input != 0) {
            if (input == 1) {
                messageOption31();
            } else if (input == 2) {
                messageOption32();
            } else {
                messageOptionOtherInput();
            }
            championshipSecondaryOption3();
            input = sc.nextInt();
        }
    }

    public void messageOption31() {
        Scanner sc = new Scanner(System.in);
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        System.out.println("All the teams in the championship: " + teamList);
        System.out.println("Please enter the teams name");
        String teamName = sc.nextLine();
        System.out.println("Stats: ");
        for(Team team : teamList){
            if(teamName.equals(team.getTeamName()))
                teamStats(team);
        }
        System.out.println(" --- ");
    }

    public void messageOption32(){
        System.out.println("Championship standings: ");
        System.out.println(" --- ");
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        Collections.sort(teamList);
        System.out.println(teamList);
    }

    public void teamStats(Team team){
        System.out.println(" --- ");
        PlayerService playerService = new PlayerService();
        List<Player> playerList = playerService.getAllSpecificPlayers(team.getIdTeam());
        System.out.println("Players: " + playerList);
        System.out.println("Matches played: " + team.getMatchesPlayed());
        System.out.println("Points: " + team.getPoints());
        System.out.println("Goals scored: " + team.getGoalsScoredTeam());
        System.out.println("Goals difference: " + team.getGoalsDifference());
    }

    //all players stats and a specific player stats(next 5 methods)
    public void messageOption4(){
        championshipSecondaryOption4();
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        while(input != 0){
            if(input == 1){
                messageOption41();
            }else if(input == 2){
                messageOption42();
            }else{
                messageOptionOtherInput();
            }
            championshipSecondaryOption4();
            input = sc.nextInt();
        }
    }

    public void messageOption41(){
        Scanner sc = new Scanner(System.in);
        PlayerService playerService = new PlayerService();
        System.out.println("All the players in the league: " + playerService.getAllPlayers());
        System.out.print("Enter the player`s name: ");
        String playerName = sc.nextLine();
        List<Player> playerList= playerService.getAllPlayers();
        for(Player player : playerList){
            if(playerName.equals(player.getPlayerName()));
                playerStats(player);
        }
    }

    public void messageOption42(){
        PlayerService playerService = new PlayerService();
        List<Player> playerList= playerService.getAllPlayers();
        for(Player player : playerList)
            playerStats(player);
    }

    public void playerStats(Player player){
        int idTeam = player.getIdTeam();
        System.out.println(" --- ");
        System.out.println("Team: " + getTeamName(idTeam));
        System.out.println("Matches Played: " + player.getMatchesPlayed());
        System.out.println("Goals scored: " + player.getGoalsScoredPlayer());
        System.out.println(" --- ");
    }

    public String getTeamName(int idTeam){
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        for(Team team : teamList){
            if(team.getIdTeam() == idTeam)
                return team.getTeamName();
        }
        return null;
    }

    //top scorers in the league
    public void messageOption5(){
        PlayerService playerService = new PlayerService();
        List<Player> playerList = playerService.getAllPlayers();
        Collections.sort(playerList);
        System.out.println("Top scorer: " + playerList.get(0).getPlayerName() + ", goals scored: " + playerList.get(0).getGoalsScoredPlayer() +
                ", matches played: "+ playerList.get(0).getMatchesPlayed());
        for(int i = 1; i < playerList.size() - 1; i++){
            System.out.println((i + 1) + ": " + playerList.get(i).getPlayerName() + ", goals scored: " + playerList.get(i).getGoalsScoredPlayer() +
                    ", matches played: "+ playerList.get(i).getMatchesPlayed());
        }
        championshipAllOptions();
    }
}
