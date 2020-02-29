import java.util.*;

public class Football {
	public static MainWindow mw;
	
	public static List<List<String>> exampleTable;
	
	public static void generateExampleSQLTable() {
//		exampleTable.add("First Name");
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
		
		int numRows = new Random().nextInt(5) + 5;
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
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exampleTable = new ArrayList<>();

        mw = new MainWindow();
	}
	
	public static void handleUpdatePageDisplay(String pageCode, List<List<String>> data) {
		//	This is where new SQL data will get feeded in
		
		System.out.println("Updating data ..." + pageCode);
		
		if(pageCode.equals("download")) {
			/*
			 * 
			 * TODO: Download SQL Data
			 * 
			 * 
			 */
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
			generateExampleSQLTable();
			mw.setSQLOutput(exampleTable);
		}

		
		
		mw.updatePageCode(pageCode);
	}

}
