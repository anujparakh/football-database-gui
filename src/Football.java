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
			// System.exit(0);
		} // end try catch

		JOptionPane.showMessageDialog(null, "Successfully Connected to Database!", "Database Connected",
							JOptionPane.INFORMATION_MESSAGE);
	}
	public static void main(String[] args) 
	{
		mw = new MainWindow(); // the GUI
		setupDatabase();
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

}
