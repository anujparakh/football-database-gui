import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainWindow extends JFrame
{
	//	MainFile Class [Will need to be changed to a new file when merged]
	
	private Football mainClass;
	
	
	// 1600 x 900 [75% of that number to look good on laptop]	
	int WIDTH = 1200;
	int pageCount = 0;
	int HEIGHT = 675;
    private JFrame myJFrame;
    private Container body;
    private JButton[] sidebarButtons;
    
    // Context
    String currentPageCode;
    List<List<String>> dataRows;
    
    JLabel nextPageField;
    JLabel lastPageField;
    JEditorPane htmlTable;
    JScrollPane scrollPane;
    
    protected void generateMainWindow() {
		myJFrame = new JFrame("Testing Name");
		myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myJFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        // Set background to whitesmoke;
		myJFrame.getContentPane().setBackground(new java.awt.Color(245, 245, 245));

		myJFrame.pack();
		
	    // Make it centered (for east testing)	    
		myJFrame.setLocationRelativeTo(null);

		myJFrame.setVisible(true);

        body = myJFrame.getContentPane();
        body.setLayout(null);
    }
    
    protected void generateMainLayout() {
    	
    	// START: Main Title / Subtitle Block   	
    	JLabel titleTextField = new JLabel("ourSQL");
        titleTextField.setFont(new Font("Tahoma", Font.PLAIN, 50));
        titleTextField.setHorizontalAlignment(JLabel.CENTER);
        titleTextField.setBounds(0, 10, WIDTH, 50);
        titleTextField.setForeground(Color.WHITE);

    	JLabel subTitleTextField = new JLabel("Your all in one solution for sports statistics needs");
    	subTitleTextField.setFont(new Font("Tahoma", Font.PLAIN, 30));
    	subTitleTextField.setHorizontalAlignment(JLabel.CENTER);
    	subTitleTextField.setBounds(0, 60, WIDTH, 30);
    	subTitleTextField.setForeground(Color.WHITE);

		JPanel headerBackgroundPanel = new JPanel();
		headerBackgroundPanel.setBounds(0, 0, WIDTH, 50 + 30 + 20);
		headerBackgroundPanel.setBackground(new java.awt.Color(66, 82, 87));
    	// END: Main Title / Subtitle Block   	

        
    	// START: Sidebar Navigation Block   	
		
		sidebarButtons = new JButton[5];
		String[] sidebarText = {"Tutorial", "Example Commands", "Create query", "View Results", "Download"};
		String[] sidebarDisplayCodes = {"tutorial", "example", "create", "view", "download"};

		int currentHeight = 100;
		for(int i = 0; i < 5; i++) {
			JButton tempSidebarButton = new JButton(sidebarText[i]);
			tempSidebarButton.setBounds(0, currentHeight, 300, 100);
			tempSidebarButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
			tempSidebarButton.setBackground(new java.awt.Color(170, 169, 173));
			currentHeight += 100;
			
			if(currentPageCode.equals(sidebarDisplayCodes[i])) {
				tempSidebarButton.setBackground(Color.BLACK);
				tempSidebarButton.setForeground(Color.WHITE);
			}
						
			sidebarButtons[i] = tempSidebarButton;
		}
		
		
        body.add(titleTextField);
        body.add(subTitleTextField);
        body.add(headerBackgroundPanel);
        
        for(int i = 0; i < 5; i++) {
        	body.add(sidebarButtons[i]);
        }
        
        for(int i = 0; i < 5; i++) {
        	
        	final Integer inner_I = new Integer(i);
        	
        	sidebarButtons[i].addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  mainClass.handleUpdatePageDisplay(sidebarDisplayCodes[inner_I], dataRows);
		      }
        	});
        }
    }
    
    protected void generateNestedWindow() {
    	// 0 - 100 pixels with full widths are taken
    	// 100 - 600 pixels with 300 pixel widths are all taken
    	// This function generates 100 - 600 height and 300 - width pixels
    	
//    	nextPageField = new JLabel();
//    	nextPageField.setFont(new Font("Tahoma", Font.PLAIN, 50));
//    	nextPageField.setHorizontalAlignment(JLabel.CENTER);
//    	nextPageField.setBounds(300, 100, WIDTH - 300, 500);
//    	nextPageField.setForeground(Color.BLACK);
//    	
//    	body.add(nextPageField);
    	
    	String html;
    	html="<html><head></head><body>";
	    html+="</body></html>";
    	htmlTable = new JEditorPane("text/html",html);

    	htmlTable.setBounds(300, 100, WIDTH - 300, 500);
    	htmlTable.setEditable(false);
    	
    	nextPageField = new JLabel();
    	nextPageField.setFont(new Font("Tahoma", Font.PLAIN, 17));
    	nextPageField.setHorizontalAlignment(JLabel.CENTER);
    	nextPageField.setBounds(900, 600, 100, 20);
    	nextPageField.setForeground(Color.BLACK);
    	    	
    	nextPageField.addMouseListener(new MouseAdapter()  
    	{  
    	    public void mouseClicked(MouseEvent e)  
    	    {  
    	    	System.out.println("Next Page ...");
    	    	pageCount += 1;
    	    	htmlTable.setText(generateTableHTML());
    	    }  
    	}); 
    	
    	
    	body.add(nextPageField);

    	lastPageField = new JLabel();
    	lastPageField.setFont(new Font("Tahoma", Font.PLAIN, 17));
    	lastPageField.setHorizontalAlignment(JLabel.CENTER);
    	lastPageField.setBounds(800, 600, 100, 20);
    	lastPageField.setForeground(Color.BLACK);
    	    	
    	lastPageField.addMouseListener(new MouseAdapter()  
    	{  
    	    public void mouseClicked(MouseEvent e)  
    	    {  
    	    	System.out.println("Last Page ...");
    	    	pageCount -= 1;

    	    	if(pageCount == -1) {
    	    		pageCount = 0;
    	    	}
    	    	htmlTable.setText(generateTableHTML());
    	    }  
    	}); 
    	
    	
    	body.add(lastPageField);

    	
    	
		body.add(htmlTable);
//		body.add(nextPage);
    }
    
    public String generateTableHTML() {
    	String html;
    	html="<html><head></head><body>";
    	
//    	html += "<table><tr><th></th><th>Savings</th></tr><tr><td>January</td><td>$100</td></tr><tr><td>February</td><td>$80</td></tr></table>";
    	
    	html += "<table>";
    	
    	html += "<tr>";
		html += "<th> </th>";
    	for(int i = 0; i < dataRows.get(0).size(); i++) {
    		html += "<th><h2><strong>  &nbsp; " + dataRows.get(0).get(i) + " &nbsp;  &nbsp; </h2></strong></th>";
    	}
    	html += "</tr>";
    	
//		WITHOUT PAGINATION
//    	for(int i = 1; i < dataRows.size(); i++) {
//        	html += "<tr>";
//    		html += "<td>"+ i + "</td>";	
//        	for(int j = 0; j < dataRows.get(i).size(); j++) {
//        		html += "<td><font face=\"Tahoma\" size=\"6\">  &nbsp; "+ dataRows.get(i).get(j) + "</font></td>";
//        	}
//        	html += "</tr>";
//    	}
    	
//    	WITH PAGINATION
    	for(int i = 1 + (pageCount * 12); i < 1 + ((pageCount + 1) * 12); i++) {
        	if (i < dataRows.size()) {
        		html += "<tr>";
        		html += "<td>"+ i + "</td>";	
            	for(int j = 0; j < dataRows.get(i).size(); j++) {
            		html += "<td><font face=\"Tahoma\" size=\"6\">  &nbsp; "+ dataRows.get(i).get(j) + "</font></td>";
            	}
            	html += "</tr>";
        	}
    	}
    	
    	html += "</table>";
    	
    	html+="</body></html>";
    	
    	System.out.println(html);
    	
    	return html;
    }
    
    public void updateNestedWindow() {
		htmlTable.setText("");

    	if(currentPageCode.equals("tutorial")) {
    		/*
    		 * 
    		 * TODO: Actually have a functioning description
    		 * 
    		 * 
    		 */
    		htmlTable.setText("<html><head></head><body>"
    				+ "<font face=\"Tahoma\" size=\"6\">"
    				+ "Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, "
    				+ "Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, "
    				+ "Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, "
    				+ "</font>"
    				+ "</body></html>");
    	}
    	
    	if(currentPageCode.equals("view")) {
        	lastPageField.setText("Last Page");
        	nextPageField.setText("Next Page");
    		htmlTable.setText(generateTableHTML());
    	} else {
        	lastPageField.setText("");
        	nextPageField.setText("");
    	}
    	

    	
    	
//		htmlTable.setText(generateTableHTML());
    }

    MainWindow() {
    	mainClass = new Football();
    	/*
    	 *	Context: What is given to dynamically update the view
    	 *     Page Route
    	 *     		- "tutorial"
    	 *     		- "example"
    	 *     		- "create"
    	 *     		- "view"
    	 *     			- Requires outputMatrix
    	 *     		- "download"
    	 *     			- Requires outputMatrix
    	 *     outputMatrix (optional for some routes)
    	 *     		- Each ROW is a SQL row
    	 *     		[
    	 *     			{
    	 *     				name: "John"
    	 *     				lastName: "Doe"
    	 *     				age: 53
    	 *     				...
    	 *     			}
    	 *     			...
    	 *     		]
    	 */
    	
    	currentPageCode = "tutorial";
    	
        generateMainWindow();
        generateMainLayout();
        
        generateNestedWindow();
        updateNestedWindow();
    }
    
    protected void setSQLOutput(List<List<String>> output) {
    	dataRows = output;
    }
    
    protected void updatePageCode(String pg) {
    	currentPageCode = pg;
    	pageCount = 0;
    	
    	
//		myJFrame.getContentPane().setBackground(new java.awt.Color(245, 245, 245));
		
    	
//    	BELOW IS SOOO MUCH MORE CONVENIENT but takes FOREEEEVER (like 5+ seconds on a good desktop) 
//		body.removeAll();
    	
		String[] sidebarDisplayCodes = {"tutorial", "example", "create", "view", "download"};
    	for(int i = 0; i < 5; i++) {
			JButton tempSidebarButton = sidebarButtons[i];
			
			tempSidebarButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
			tempSidebarButton.setBackground(new java.awt.Color(170, 169, 173));
			tempSidebarButton.setForeground(Color.BLACK);

			if(currentPageCode.equals(sidebarDisplayCodes[i])) {
				tempSidebarButton.setBackground(Color.BLACK);
				tempSidebarButton.setForeground(Color.WHITE);
			}
        }
    	
    	
    	updateNestedWindow();
    }
}
