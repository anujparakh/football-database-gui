import java.util.*;
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
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} // end try catch

		System.out.println("Opened Database Successfully");
	}
	public static void main(String[] args) 
	{
		mw = new MainWindow(); // the GUI
		setupDatabase();
	}
	
	public static void handleUpdatePageDisplay(String pageCode, List<List<String>> data) 
	{
		//	This is where new SQL data will get feeded in
		System.out.println("Updating data ..." + pageCode);
		
		if(pageCode.equals("download")) {
			/*
			 * 
			 * TODO: Download SQL Data
			 * 
			 * 
			 */			
			List<List<String>> exampleTable = executeQuery("select first_name, last_name from player;");
			writeDataToCSV(exampleTable, "test.csv");
			System.out.println("DOWNLOAD::: " + data);
		}
		
		if(pageCode.equals("view")) {
			/*
			 * 
			 * TODO: Use whatever is in Create Query to
			 * Build and return a SQL reponse converted
			 * to a string matrix
			 * 
			 */
			List<List<String>> exampleTable = executeQuery("select first_name, last_name from player limit 20;");
			mw.setSQLOutput(exampleTable);
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

}
