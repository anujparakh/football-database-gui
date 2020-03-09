import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Questions {

    public static String generateVictoryChain(List<List<String>> teamGameStats, List<List<String>> teamCodes, String team1Code, String team2Code)
    {
        HashMap<String, List<String>> teamCodeMap = new HashMap<String, List<String>>();
        String toReturn = "";

        // MAKE 0 in future. 1 is header column
        for (int i = 1; i < teamGameStats.size(); i++) {
            List<String> current = teamGameStats.get(i);
            String teamCode = current.get(0);
            String gameCode = current.get(1);
            System.out.println(current.get(35));
            int points = Integer.parseInt(current.get(35));
            int year = Integer.parseInt(current.get(68));

            // System.out.println(current);
            if (teamCodeMap.containsKey(gameCode)) {
                List<String> ele = teamCodeMap.get(gameCode);
                ele.add(teamCode);
                ele.add(Integer.toString(points));
                ele.add(Integer.toString(year));

                teamCodeMap.put(gameCode, ele);
            } else {
                List<String> ele = new ArrayList<>();
                ele.add(teamCode);
                ele.add(Integer.toString(points));
                teamCodeMap.put(gameCode, ele);
            }
        }

        HashMap<String, List<String>> teamToWinsList = new HashMap<String, List<String>>();

        for (String key : teamCodeMap.keySet()) {
            // Data is all clean
            // if(teamCodeMap.get(key) != 2) {
            // System.out.println("ERROR ...");
            // }
            // System.out.println(teamCodeMap.get(key));
            String gameCode = key;
            List<String> ele = teamCodeMap.get(gameCode);
            String teamOne = ele.get(0);
            int teamOneScore = Integer.parseInt(ele.get(1));
            String teamTwo = ele.get(2);
            int teamTwoScore = Integer.parseInt(ele.get(3));
            String year = ele.get(4);

            String winnerTeam = "";
            String loserTeam = "";
            if (teamOneScore != teamTwoScore) {
                if (teamOneScore > teamTwoScore) {
                    winnerTeam = teamOne;
                    loserTeam = teamTwo;
                } else {
                    winnerTeam = teamTwo;
                    loserTeam = teamOne;
                }

                if (teamToWinsList.containsKey(winnerTeam)) {
                    List<String> lossTeams = teamToWinsList.get(winnerTeam);
                    lossTeams.add(loserTeam);
                    lossTeams.add(gameCode);
                    lossTeams.add(year);

                    teamToWinsList.put(winnerTeam, lossTeams);
                } else {
                    List<String> lossTeams = new ArrayList<>();
                    lossTeams.add(loserTeam);
                    lossTeams.add(gameCode);
                    lossTeams.add(year);
                    teamToWinsList.put(winnerTeam, lossTeams);
                }
            }
        }

        // for(String key : teamToWinsList.keySet()) {
        // System.out.println(teamToWinsList.get(key));
        // }

        // PERFORM BFS
        String start = team1Code; // Texas A&M team code
        String end = team2Code; // Marshall team code

        HashMap<String, String> visited = new HashMap<String, String>();

        // List<String> -> pair of [team, and path until it]
        Queue<List<String>> q = new LinkedList<List<String>>();
        List<String> startList = new ArrayList<>();
        startList.add(start);
        q.add(startList);

        List<String> solution = new ArrayList<>();
        boolean valid = false;

        while (!q.isEmpty()) {
            List<String> currentTeamHistoryList = q.poll();

            String currentTeam = currentTeamHistoryList.get(currentTeamHistoryList.size() - 1);

            System.out.println("... Checking " + currentTeam);

            if (currentTeam.equals(end)) {
                solution = currentTeamHistoryList;
                valid = true;
                System.out.println("FINISHED!!!" + currentTeamHistoryList);
                break;
            }

            List<String> defeatedTeams = teamToWinsList.get(currentTeam);
            if (defeatedTeams != null) {
                for (int i = 0; i < defeatedTeams.size(); i += 3) {
                    String defeated = defeatedTeams.get(i);
                    // System.out.println(defeated);
                    if (!visited.containsKey(defeated)) {
                        visited.put(defeated, "");
                        ArrayList newPath = new ArrayList();

                        for (int j = 0; j < currentTeamHistoryList.size(); j++) {
                            newPath.add(currentTeamHistoryList.get(j));
                        }

                        newPath.add(defeatedTeams.get(i + 1));
                        newPath.add(defeatedTeams.get(i + 2));
                        newPath.add(defeated);

                        q.add(newPath);
                    }
                }
            }
        }

        HashMap<String, String> teamCodeToName = new HashMap<String, String>();

        for (int i = 1; i < teamCodes.size(); i++) {
            teamCodeToName.put(teamCodes.get(i).get(0), teamCodes.get(i).get(1));
        }

        if (valid) {
            System.out.println("SOLUTION!!");
            System.out.println("START: (" + start + ") " + teamCodeToName.get(start) + "   END: (" + end + ") "
                    + teamCodeToName.get(end));

            // teamCodeToName
            System.out.println(teamCodeToName.get(solution.get(0)) + " defeated ");
            toReturn += teamCodeToName.get(solution.get(0)) + " defeated ";
            // System.out.println(solution.get(0) + " defeated ");
            for (int i = 1; i < solution.size(); i += 3) {

                String outputS = "";
                outputS += teamCodeToName.get(solution.get(i + 2));
                outputS += " in " + solution.get(i + 1);
                // outputS += " (Game: " + solution.get(i) + ")";

                if (i + 3 < solution.size()) 
                {
                    outputS += " which defeated ";
                }

                toReturn += outputS + "\n";
	    		System.out.println(outputS);
            }

            return toReturn;
        } else {
            System.out.println("FAILED ... ");
        }

        return "Some problem..";
    }

    public static String generateQuestion3Query(String team) {
        String query = 
        "select team.name, rush_yard, team_game_statistics.year from"
        + " team_game_statistics"
        + " INNER JOIN team ON team.team_code=team_game_statistics.team_code"
        + " AND team.year=team_game_statistics.year"
        + " where game_code in"
        + " ("
        + " select game_code from game"
        + " where (home_team_code, year) in"
        + " ("
        + " select team_code, year"
        + " from team where name='" + team + "'"
        + " )"
        + " OR (visit_team_code, year) in"
        + " ("
        + " select team_code, year"
        + " from team where name='" + team + "'"
        + " )"
        + " )"
        + " AND"
        + " team_game_statistics.team_code not in"
        + " ("
        + " select team_code"
        + " from team where name='" + team + "'"
        + " )"
        + " ORDER BY rush_yard DESC limit 10;";
        
        
        return query;
    }

    public static String generateQuestion4Query(String conference) {
        String query = "SELECT home_team, 100 * ROUND(sum(home_team_won)/(sum(home_team_lost)+sum(home_team_won)),2) as home_feild_victory_percentage"
                + " FROM "
                + " (select home_team, home_team_points, visit_team, visit_team_points, (Cast(home_team_points as float) - CAST(visit_team_points as int)) as difference,"
                + " CASE" + "     WHEN (Cast(home_team_points as float) - CAST(visit_team_points as int)) > 0 THEN 1"
                + "     WHEN (Cast(home_team_points as float) - CAST(visit_team_points as int)) = 0 THEN .5"
                + "      ELSE 0" + " END AS home_team_won," + " CASE"
                + "     WHEN (Cast(home_team_points as float) - CAST(visit_team_points as int)) = 0 THEN .5"
                + "     WHEN (Cast(home_team_points as float) - CAST(visit_team_points as int)) < 0 THEN 1"
                + "      ELSE 0" + " END AS home_team_lost" + " From "
                + " (SELECT team.name as visit_team, team_game_statistics.points as visit_team_points, conference.name as conference_name, game.date, game.game_code as visit_game_code,  stadium.name as stadium_name "
                + " FROM team"
                + " JOIN conference ON team.conference_code = conference.conference_code and team.year = conference.year"
                + " JOIN game ON team.team_code = game.visit_team_code"
                + " JOIN stadium ON game.stadium_code = stadium.stadium_code and game.year = stadium.year"
                + " JOIN team_game_statistics ON game.game_code = team_game_statistics.game_code and team.team_code = team_game_statistics.team_code"
                + " WHERE conference.name = '" + conference + "' and team.year = '2013'"
                + " ORDER BY game.date)visit_table" + " JOIN"
                + " (SELECT team.name as home_team, team_game_statistics.points as home_team_points, conference.name as conference_name, game.date, game.game_code as home_game_code, stadium.name as stadium_name "
                + " FROM team"
                + " JOIN conference ON team.conference_code = conference.conference_code and team.year = conference.year"
                + " JOIN game ON team.team_code = game.home_team_code"
                + " JOIN stadium ON game.stadium_code = stadium.stadium_code and game.year = stadium.year"
                + " JOIN team_game_statistics ON game.game_code = team_game_statistics.game_code and team.team_code = team_game_statistics.team_code"
                + " WHERE conference.name = '" + conference + "' and team.year = '2013'"
                + " ORDER BY game.date)home_table" + " ON visit_table.visit_game_code = home_table.home_game_code"
                + " ORDER BY home_team)output_table" + " GROUP BY output_table.home_team";
        return query;
    }

    public static String generateQuestion5Query(String conference) {
        String query = "SELECT conference_name, team_name, points, date" + " FROM" + " ("
                + " SELECT DISTINCT ON (conference.name) conference.name AS conference_name, team.name AS team_name,  team_game_statistics.points AS points, game.date AS date"
                + " FROM conference" + " INNER JOIN team"
                + " ON conference.conference_code = team.conference_code AND conference.year = team.year"
                + " INNER JOIN team_game_statistics"
                + " ON team.team_code = team_game_statistics.team_code AND conference.year = team.year"
                + " INNER JOIN game"
                + " ON team_game_statistics.game_code = game.game_code AND conference.year = game.year"
                + " ORDER BY conference.name ASC, CAST(team_game_statistics.points AS int) DESC" + " ) as best_games"
                + " WHERE conference_name = '" + conference + "';";
        return query;
    }

    Questions() {
    }
}