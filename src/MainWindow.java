import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame
{
	//	MainFile Class [Will need to be changed to a new file when merged]
	
	private Football mainClass;
	
	
	// 1600 x 900 [75% of that number to look good on laptop]	
	int WIDTH = 1200;
	int HEIGHT = 675;
    private JFrame myJFrame;
    private Container body;
    private JButton[] sidebarButtons;
    
    // Context
    String currentPageCode;

    
    JLabel todoField;
    
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
		    	  System.out.println("Prime load file button clicked.");
		    	  mainClass.handleUpdatePageDisplay(sidebarDisplayCodes[inner_I]);
		      }
        	});
        }
    }
    
    protected void generateNestedWindow() {
    	// 0 - 100 pixels with full widths are taken
    	// 100 - 600 pixels with 300 pixel widths are all taken
    	// This function generates 100 - 600 height and 300 - width pixels
    	
    	todoField = new JLabel();
    	todoField.setFont(new Font("Tahoma", Font.PLAIN, 50));
    	todoField.setHorizontalAlignment(JLabel.CENTER);
    	todoField.setBounds(300, 100, WIDTH - 300, 500);
    	todoField.setForeground(Color.BLACK);
    	
    	body.add(todoField);
    }
    
    public void updateNestedWindow() {
    	todoField.setText(currentPageCode);
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
    
    protected void updatePageCode(String pg) {
    	currentPageCode = pg;
    	
//		myJFrame.getContentPane().setBackground(new java.awt.Color(245, 245, 245));
		
    	
//    	BELOW IS SOOO MUCH MORE CONVENIENT but takes FOREEEEVER (like 5+ seconds on a good desktop) 
//		body.removeAll();
    	
		String[] sidebarDisplayCodes = {"tutorial", "example", "create", "view", "download"};
    	for(int i = 0; i < 5; i++) {
			JButton tempSidebarButton = sidebarButtons[i];
			
			tempSidebarButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
			tempSidebarButton.setBackground(new java.awt.Color(170, 169, 173));
			
			if(currentPageCode.equals(sidebarDisplayCodes[i])) {
				tempSidebarButton.setBackground(Color.BLACK);
				tempSidebarButton.setForeground(Color.WHITE);
			}
        }
    	
    	
    	updateNestedWindow();
    }
}
