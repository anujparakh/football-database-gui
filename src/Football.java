import java.util.*;
import javax.swing.*;
import java.sql.*;
import java.io.FileWriter;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

public class Football {

	// Global Variables Needed
	public static MainWindow mw;
	private static Connection sqlConnection;

	// Temp Function for examples
	public static List<List<String>> generateExampleSQLTable() 
	{
		var exampleTable = new ArrayList<List<String>>();
		exampleTable.clear();
		
		List<String> headerRow = new ArrayList<>();
		headerRow.add("First Name");
		headerRow.add("Last Name");
		headerRow.add("State");
		headerRow.add("Age");
		headerRow.add("Year Played");
		headerRow.add("ID");
		headerRow.add("isFootballPlayer");
		
		exampleTable.add(headerRow);
		
		int numRows = new Random().nextInt(5 + 25) + 5;
		for(int i = 0; i < numRows; i++) {
			List<String> dataRow = new ArrayList<>();
			dataRow.add("Johnson");
			dataRow.add("Doe");
			dataRow.add("TX");
			
			int randomAge = new Random().nextInt(5 + 1) + 15;
			dataRow.add(Integer.toString(randomAge));
			int randomYear = new Random().nextInt(12 + 1) + 2006;
			dataRow.add(Integer.toString(randomYear));
			int randomID = new Random().nextInt(9999999) + 10000000;
			dataRow.add(Integer.toString(randomID));
			dataRow.add("True");
			exampleTable.add(dataRow);

		}

		return exampleTable;
	}
	
	private static void setupDatabase()
	{
		dbSetup my = new dbSetup();
		sqlConnection = null;
		try {
			Class.forName("org.postgresql.Driver");
			sqlConnection = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/oursql21", my.user,
					my.pswd);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Could Not Connect to Database!", "Database Connection Error",
			JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			
		} // end try catch

		JOptionPane.showMessageDialog(null, "Successfully Connected to Database!", "Database Connected",
							JOptionPane.INFORMATION_MESSAGE);
	}
	public static void main(String[] args) 
	{
		setupDatabase();

		mw = new MainWindow(); // the GUI
	}
	
	public static void handleUpdatePageDisplay(String pageCode, CreatePanel createPanel) 
	{
		//	This is where new SQL data will get feeded in
		
		if(pageCode.equals("download")) 
		{
			// Check Create Page
			String checkResult = createPanel.checkProblems();
			if (! checkResult.equals(""))
			{
				JOptionPane.showMessageDialog(null, checkResult, "Create Error",
							JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Get Query from Create page
			String queryString = createPanel.convertToQuery();

			// Execute Query and Display Result
			List<List<String>> results = executeQuery(queryString);
			writeDataToCSV(results, "output.csv");

			JOptionPane.showMessageDialog(null, "Data downloaded to output.csv", "Download Finished",
							JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if(pageCode.equals("view")) {
			// Check Create Page
			String checkResult = createPanel.checkProblems();
			if (! checkResult.equals(""))
			{
				JOptionPane.showMessageDialog(null, checkResult, "Create Error",
							JOptionPane.WARNING_MESSAGE);
				return;
			}
			String viewProblems = createPanel.checkIfViewable();
			if (! viewProblems.equals(""))
			{
				JOptionPane.showMessageDialog(null, viewProblems, "Data too big",
							JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Get Query from Create page
			String queryString = createPanel.convertToQuery();

			// Execute Query and Display Result
			List<List<String>> results = executeQuery(queryString);
			mw.setSQLOutput(results);
		}

		mw.updatePageCode(pageCode);
	}

	private static void writeDataToCSV(List<List<String>> data, String outputFileName)
	{
		try
		{
			FileWriter myWriter = new FileWriter(outputFileName);
	      	// myWriter.write("Files in Java might be tricky, but it is fun enough!");
			for (List<String> row: data)
			{
				for(int i = 0; i < row.size() - 1; ++i)
				{
					myWriter.write(row.get(i) +", ");
				}

				myWriter.write(row.get(row.size() - 1) + "\n");
			}

			myWriter.close();
		}
		catch(Exception ex)
		{
			System.out.println("An error occurred in CSV writing");
			ex.printStackTrace();		
		}
	}

	private List<List<String>> executeQueryNoHeader (String sqlQuery)
	{
		try 
		{
			// create a statement object
			Statement stmt = sqlConnection.createStatement();
			// send statement to DBMS
			ResultSet result = stmt.executeQuery(sqlQuery);
			ResultSetMetaData resultSetMetaData = result.getMetaData();

			List<List<String>> toReturn = new ArrayList<List<String>>();
			while(result.next())
			{
				List<String> row = new ArrayList<>();
				for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i)
				{
					row.add(result.getString(i + 1));
				}
				toReturn.add(row);
			}

			return toReturn;
		}
		catch(Exception ex)
		{
			System.out.println ("Error executing query: " + sqlQuery);
		}
		
		return null;
	}

	private String getTeamCode (String teamName, String year)
	{
		String query = "select team_code from 	team where name = '" + teamName
				+ "' and year = '"
				+ year + "';";
		var result = executeQueryNoHeader(query);
		return result.get(0).get(0);
 	}

	// Executes a given SQL query and returns the result
	public static List<List<String>> executeQuery(String sqlQuery)
	{
		try 
		{
			// create a statement object
			Statement stmt = sqlConnection.createStatement();
			// send statement to DBMS
			ResultSet result = stmt.executeQuery(sqlQuery);
			ResultSetMetaData resultSetMetaData = result.getMetaData();

			List<List<String>> toReturn = new ArrayList<List<String>>();
			List<String> headerRow = new ArrayList<>();
			for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i)
			{
				headerRow.add(resultSetMetaData.getColumnName(i + 1));
			}

			toReturn.add(headerRow);

			while(result.next())
			{
				List<String> row = new ArrayList<>();
				for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i)
				{
					row.add(result.getString(i + 1));
				}
				toReturn.add(row);
			}

			return toReturn;
		}
		catch(Exception ex)
		{
			System.out.println ("Error executing query: " + sqlQuery);
		}
		
		return null;
	}

	public ArrayList<String> getAllColumnsForTable(String table)
	{
		try
		{
			Statement stmt = sqlConnection.createStatement();
			ResultSet result = stmt.executeQuery("select * from " + table + ";");
			ResultSetMetaData resultSetMetaData = result.getMetaData();
			ArrayList<String> headerRow = new ArrayList<>();
			headerRow.add("None");
			for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i)
			{
				headerRow.add(resultSetMetaData.getColumnName(i + 1));
			}

			return headerRow;
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting column names");
		}

		return null;

	}

	public String[] getListOfTables()
	{
		String[] toReturn = {"None", "conference", "drive", "game", "game_statistics", "kickoff", "kickoff_return", "pass", "play", "player", "player_game_statistics", "punt", "punt_return", "reception", "rush", "stadium", "team", "team_game_statistics"};
		return toReturn;
	}

	public String[] getListOfTeams()
	{
		var result = executeQuery("select name from team where year = '2013';");
		List<String> toReturn = new ArrayList<>();
		result.forEach(toReturn::addAll);
		toReturn.remove(0);
		return toReturn.stream().toArray(String[] ::new);
	}

	public String[] getListOfConferences()
	{
		var result = executeQuery("select name from conference where year = '2013';");
		List<String> toReturn = new ArrayList<>();
		result.forEach(toReturn::addAll);
		toReturn.remove(0);
		return toReturn.stream().toArray(String[] ::new);
	}

	public String solveQuestion1(String team1, String team2)
	{
		String answer = "answer 1";
		List<List<String>> teamCodes = executeQuery("SELECT * FROM team WHERE YEAR = 2013;");
		List<List<String>> teamGameStats = executeQuery("SELECT * FROM team_game_statistics;");
		System.out.println ("working on it...");
		answer = Questions.generateVictoryChain(teamGameStats, teamCodes, getTeamCode(team1, "2013"), getTeamCode(team2, "2013"));
		return answer;
	}

	public String solveQuestion3(String team)
	{
		String answer = "answer 3";
		String query = Questions.generateQuestion3Query(team);

		var result = executeQueryNoHeader(query).get(0);
		answer = result.get(0) + " scored " + result.get(1) + " rushing yards against " + team + " in " + result.get(2);
		return answer;
	}

	public List<List<String>> solveQuestion4(String conference)
	{
		
		String query = Questions.generateQuestion4Query(conference);
		var result = executeQueryNoHeader(query);
		var header = new ArrayList<String>();
		header.add("Team");
		header.add("Home Advantage");
		result.add(0, header);
		return result;
	}

	public String solveQuestion5(String conference)
	{
		String query = Questions.generateQuestion5Query(conference);
		var result = executeQueryNoHeader(query).get(0);
		String answer = "In the conference " + result.get(0) 
						+ ", " + result.get(1) 
						+ " scored " + result.get(2)
						+ " on " + result.get(3);
		return answer;
	}

}
