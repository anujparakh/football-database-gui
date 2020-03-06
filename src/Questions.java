import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Questions {
	public static void generateVictoryChain(
			List<List<String>> teamGameStats,
			List<List<String>> teamCodes
		) {
	    HashMap<String, List<String>> teamCodeMap = new HashMap<String, List<String> >();

	    // MAKE 0 in future. 1 is header column	    
	    for(int i = 1; i < teamGameStats.size(); i++) {
	    	List<String> current = teamGameStats.get(i);
	    	String teamCode = current.get(0);
	    	String gameCode = current.get(1);
	    	System.out.println(current.get(35));
	    	int points = Integer.parseInt(current.get(35));
	    	int year = Integer.parseInt(current.get(68));

	    	
	    	
//	    	System.out.println(current);
	    	if(teamCodeMap.containsKey(gameCode)) {
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
	    
	    HashMap<String, List<String>> teamToWinsList = new HashMap<String, List<String> >();

	    
	    for(String key : teamCodeMap.keySet()) {
//	    	Data is all clean
//	    	if(teamCodeMap.get(key) != 2) {
//	    		System.out.println("ERROR ...");
//	    	}
//	    	System.out.println(teamCodeMap.get(key));
	    	String gameCode = key;
	    	List<String> ele = teamCodeMap.get(gameCode);
	    	String teamOne = ele.get(0);
	    	int teamOneScore = Integer.parseInt(ele.get(1));
	    	String teamTwo = ele.get(2);
	    	int teamTwoScore = Integer.parseInt(ele.get(3));
	    	String year = ele.get(4);

	    	String winnerTeam = "";
	    	String loserTeam = "";
	    	if(teamOneScore != teamTwoScore) {
	    		if(teamOneScore > teamTwoScore) {
	    			winnerTeam = teamOne;
	    			loserTeam = teamTwo;
	    		} else {
	    			winnerTeam = teamTwo;
	    			loserTeam = teamOne;
	    		}
	    		
		    	if(teamToWinsList.containsKey(winnerTeam)) {
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
	    
	    
//	    for(String key : teamToWinsList.keySet()) {
//	    	System.out.println(teamToWinsList.get(key));
//	    }

//	    PERFORM BFS
	    String start = "697"; // Texas A&M team code
	    String end = "388"; // Marshall team code
	    
	    HashMap<String, String> visited = new HashMap<String, String>();
	    

	    
//	    List<String> -> pair of [team, and path until it]
	    Queue< List<String> > q = new LinkedList< List<String> >();
	    List<String> startList = new ArrayList<>();
	    startList.add(start);
	    q.add(startList);
	    
	    List<String> solution = new ArrayList<>();
	    boolean valid = false;
	    
	    while(!q.isEmpty()) {
	    	List<String> currentTeamHistoryList = q.poll();

	    	
	    	String currentTeam = currentTeamHistoryList.get(currentTeamHistoryList.size() - 1);
	    	
	    	System.out.println("... Checking " + currentTeam);
	    	
	    	if(currentTeam.equals(end)) {
	    		solution = currentTeamHistoryList;
	    		valid = true;
	    		System.out.println("FINISHED!!!" + currentTeamHistoryList);
	    		break;
	    	}
	    	
	    	
	    	List<String> defeatedTeams = teamToWinsList.get(currentTeam);
	    	if(defeatedTeams != null) {
		    	for(int i = 0; i < defeatedTeams.size(); i+= 3) {
		    		String defeated = defeatedTeams.get(i);
//		    		System.out.println(defeated);
		    		if (! visited.containsKey(defeated)) {
		    			visited.put(defeated, "");
		    	        ArrayList newPath = new ArrayList(); 
		    	        
		    	        for(int j = 0; j < currentTeamHistoryList.size(); j++) {
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
	    
	    HashMap<String, String> teamCodeToName = new HashMap<String, String >();
	    
	    for(int i = 1; i < teamCodes.size(); i++) {
	    	teamCodeToName.put(teamCodes.get(i).get(0), teamCodes.get(i).get(1));
	    }
	    
	    if(valid) {
	    	System.out.println("SOLUTION!!");
	    	System.out.println("START: (" + start + ") " + teamCodeToName.get(start) + "   END: (" + end + ") " + teamCodeToName.get(end));
	    	
//	    	teamCodeToName
	    	System.out.println(teamCodeToName.get(solution.get(0)) + " defeated ");

//	    	System.out.println(solution.get(0) + " defeated ");
	    	for(int i = 1; i < solution.size(); i+=3) {

	    		String outputS = "";
	    		outputS += teamCodeToName.get(solution.get(i + 2));
	    		outputS += " in " + solution.get(i + 1);
	    		outputS += " (Game: "  + solution.get(i) + ")";
	    		
	    		if(i + 3 < solution.size()) {
	    			outputS += " which defeated ";
	    		}
	    		System.out.println(outputS);
	    		
//	    		System.out.println(solution.get(i) + " which defeated ");
	    	}
	    } else {
	    	System.out.println("FAILED ... ");
	    }
	    
	    
//		System.out.println("... ...");
	    
	}
}
