package championship;

import model.Player;
import model.Team;
import services.PlayerService;
import services.TeamService;

import java.util.*;

public class ChampOptions{
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

    //setting up games between the teams(next 8 methods)
    public void messageOption2(){
        Scanner sc = new Scanner(System.in);
        TeamService teamService = new TeamService();
        List<Team> teamList = teamService.getAllTeams();
        System.out.println("All the teams: " + teamList);

        String homeTeamName = null, awayTeamName = null;
        boolean homeTeamBool = false, awayTeamBool = false;

        System.out.print("Please enter the name of the first team (home team): ");
        while(!homeTeamBool) {
            homeTeamName = sc.nextLine();
            if (correctTeamName(homeTeamName)) {
                homeTeamBool = true;
            } else {
                System.out.println("Please enter the name of the team from the list: " + teamList);
            }
        }

        System.out.print("Please enter the name of the second team (away team): ");
        while(!awayTeamBool) {
            awayTeamName = sc.nextLine();
            if (correctTeamName(awayTeamName)) {
                awayTeamBool = true;
            } else {
                System.out.println("Please enter the name of the team from the list: " + teamList);;
            }
        }

        System.out.println("The match started!");
        System.out.println(" --- ");
        System.out.println("The match ended!");
        System.out.println("Please enter the final score!");
        System.out.println("Number of goals scored by the home team (" + homeTeamName + ") :");
        int homeTeamGoals = sc.nextInt();
        System.out.println("Number of goals scored by the away team (" + awayTeamName + ") :");
        int awayTeamGoals = sc.nextInt();
        updateTeam(homeTeamName, awayTeamName, homeTeamGoals, awayTeamGoals);

        System.out.println("Please enter the name of the players who scored in this game between " + homeTeamName + " and " + awayTeamName + " !");
        playerGoalsForTeam(homeTeamName, homeTeamGoals);
        playerGoalsForTeam(awayTeamName, awayTeamGoals);

        System.out.println(" --- ");
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

    public boolean correctTeamName(String teamName){
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

    public void updateTeam(String homeTeamName, String awayTeamName, int homeTeamGoals, int awayTeamGoals){
        TeamService teamService = new TeamService();
        Team homeTeam = findTeam(homeTeamName);
        homeTeam.setMatchesPlayed((homeTeam.getMatchesPlayed() + 1));
        homeTeam.setGoalsScoredTeam((homeTeam.getGoalsScoredTeam() + homeTeamGoals));
        homeTeam.setGoalsDifference((homeTeam.getGoalsDifference() + (homeTeamGoals - awayTeamGoals)));

        Team awayTeam = findTeam(awayTeamName);
        awayTeam.setMatchesPlayed((awayTeam.getMatchesPlayed() + 1));
        awayTeam.setGoalsScoredTeam((awayTeam.getGoalsScoredTeam() + awayTeamGoals));
        awayTeam.setGoalsDifference((awayTeam.getGoalsDifference() + (awayTeamGoals - homeTeamGoals)));

        if(homeTeamGoals > awayTeamGoals){
            System.out.println("Home team (" + homeTeamName + ") wins!");
            homeTeam.setPoints((homeTeam.getPoints() + 3));
        }
        else if(homeTeamGoals < awayTeamGoals){
            System.out.println("Away team (" + awayTeamName + ") wins!");
            awayTeam.setPoints((awayTeam.getPoints() + 3));
        } else{
            System.out.println("The game " + homeTeamName + " - " + awayTeamName + " ended in a draw!");
            homeTeam.setPoints((homeTeam.getPoints() + 1));
            awayTeam.setPoints((awayTeam.getPoints() + 1));
        }

        teamService.updateTeam(homeTeam);
        teamService.updateTeam(awayTeam);
    }

    public Team findTeam(String teamName){
        TeamService teamService = new TeamService();
        return teamService.findTeam(teamName);
    }

    public void playerGoalsForTeam(String teamName, int teamGoals){
        Scanner sc = new Scanner(System.in);
        TeamService teamService = new TeamService();
        Team team = teamService.findTeam(teamName);
        String playerName = null;
        PlayerService playerService = new PlayerService();
        List<Player> playerList = getSpecificPlayers(team.getIdTeam());
        if(teamGoals > 0) {
            System.out.println("For team (" + team.getTeamName() + ")!");
            System.out.print("All the players: " + playerList);
            for (Player player : playerList) {
                player.setMatchesPlayed((player.getMatchesPlayed() + 1));
                playerService.updatePlayer(player);
            }
            System.out.println("");
            int playerGoals = 0;
            System.out.println("Remaining goals for team (" + team.getTeamName() + "): " + teamGoals);
            while(teamGoals > 0) {
                System.out.println("Enter the name of the player who scored: ");
                playerName = sc.nextLine();
                if (correctPlayerName(playerName)) {
                    Player player = playerService.findPlayer(playerName);
                    System.out.println("Enter how many goals " + player.getPlayerName() + " scored: ");
                    boolean ok = true;
                    while (ok) {
                        playerGoals = sc.nextInt();
                        if (playerGoals <= teamGoals) {
                            player.setGoalsScoredPlayer((player.getGoalsScoredPlayer() + playerGoals));
                            playerService.updatePlayer(player);
                            teamGoals -= playerGoals;
                            playerGoals = 0;
                            System.out.println("Remaining goals for the home team (" + team.getTeamName() + "): " + teamGoals);
                            sc.nextLine();
                            ok = false;
                        } else {
                            System.out.println("The number of goals entered is too high!");
                            System.out.println("Please enter the correct number of goals! ");
                        }
                    }
                }else{
                    System.out.println("The name of the player is not correct");
                    System.out.println("All the players from team " + team.getTeamName() + ": " + playerList);
                }
            }
        }else{
            System.out.println("Home team " + team.getTeamName() + " did not score any goals!");
            for (Player player : playerList) {
                player.setMatchesPlayed((player.getMatchesPlayed() + 1));
                playerService.updatePlayer(player);
            }
        }
    }

    public List<Player> getSpecificPlayers(int idTeam){
        PlayerService playerService = new PlayerService();
        return playerService.getAllSpecificPlayers(idTeam);
    }

    public boolean correctPlayerName(String playerName){
        PlayerService playerService = new PlayerService();
        List<Player> playerList = playerService.getAllPlayers();
        for(Player player:playerList){
            if(playerName.equals(player.getPlayerName()))
                return true;
        }
        return false;
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
            if(playerName.equals(player.getPlayerName())){
               playerStats(player);
            }
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
        System.out.println("Name: " + player.getPlayerName());
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
        for(int i = 1; i < playerList.size(); i++){
            System.out.println((i + 1) + ": " + playerList.get(i).getPlayerName() + ", goals scored: " + playerList.get(i).getGoalsScoredPlayer() +
                    ", matches played: "+ playerList.get(i).getMatchesPlayed());
        }
    }
}
