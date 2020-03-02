import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainWindow extends JFrame {
	// MainFile Class [Will need to be changed to a new file when merged]

	private Football mainClass;

	// 1600 x 900 [75% of that number to look good on laptop]
	public static final int WIDTH = 1200;
	int pageCount = 0;
	public static final int HEIGHT = 675;
	private JFrame myJFrame;
	private Container body;
	private JButton[] sidebarButtons;

	// Context
	String currentPageCode;
	List<List<String>> dataRows;

	// For View Results Page
	JLabel nextPageField;
	JLabel lastPageField;
	JEditorPane resultsHtmlTableView;

	// Panels for each page
	JPanel tutorialPanel;
	CreatePanel createPanel;
	JPanel examplePanel;

	protected void generateMainWindow() 
	{
		myJFrame = new JFrame("Football Database GUI");
		myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myJFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// Set background to whitesmoke;
		myJFrame.getContentPane().setBackground(Colors.backgroundColor);
		myJFrame.pack();

		// Make it centered (for east testing)
		myJFrame.setLocationRelativeTo(null);
		myJFrame.setVisible(true);
		myJFrame.setResizable(false);

		body = myJFrame.getContentPane();
		body.setLayout(null);
	}

	protected void generateMainLayout() {

		// // START: Main Title / Subtitle Block
		// JLabel titleTextField = new JLabel("ourSQL");
		// titleTextField.setFont(new Font("Arial", Font.PLAIN, 45));
		// titleTextField.setHorizontalAlignment(JLabel.CENTER);
		// titleTextField.setBounds(0, 10, WIDTH, 50);
		// titleTextField.setForeground(Colors.headingTextColor);

		// JLabel subTitleTextField = new JLabel("Your all in one solution for sports statistics needs");
		// subTitleTextField.setFont(new Font("Arial", Font.PLAIN, 30));
		// subTitleTextField.setHorizontalAlignment(JLabel.CENTER);
		// subTitleTextField.setBounds(0, 60, WIDTH, 30);
		// subTitleTextField.setForeground(Colors.subHeadingTextColor);

		// JPanel headerBackgroundPanel = new JPanel();
		// headerBackgroundPanel.setBounds(0, 0, WIDTH, 50 + 30 + 20);
		// headerBackgroundPanel.setBackground(Colors.headerBackgroundColor);

		// JPanel dividerPanel = new JPanel();
		// dividerPanel.setBounds(0, 98, WIDTH, 2);
		// dividerPanel.setBackground(Colors.dividerColor);

		HeadingPanel headingPanel = new HeadingPanel(WIDTH, 100);
		headingPanel.setBounds(0, 0, WIDTH, 100);
		headingPanel.setVisible(true);
		body.add(headingPanel);

		// END: Main Title / Subtitle Block

		// START: Sidebar Navigation Block

		sidebarButtons = new JButton[5];
		String[] sidebarText = { "Tutorial", "Example Commands", "Create query", "View Results", "Download" };
		String[] sidebarDisplayCodes = { "tutorial", "example", "create", "view", "download" };

		int currentHeight = 100;
		for (int i = 0; i < 5; i++) {
			JButton tempSidebarButton = new JButton(sidebarText[i]);
			tempSidebarButton.setBounds(0, currentHeight, 299, 99);
			tempSidebarButton.setFont(new Font("Arial", Font.PLAIN, 24));
			tempSidebarButton.setBackground(Colors.sidebarBackgroundColor);
			tempSidebarButton.setForeground(Colors.sidebarTextColor);
			tempSidebarButton.setOpaque(true);
			tempSidebarButton.setBorderPainted(false);
			currentHeight += 100;

			if (currentPageCode.equals(sidebarDisplayCodes[i])) {
				tempSidebarButton.setBackground(Colors.sidebarSelectedBackgroundColor);
				tempSidebarButton.setOpaque(true);
				tempSidebarButton.setBorderPainted(false);
				tempSidebarButton.setForeground(Colors.sidebarSelectedTextColor);
			}

			sidebarButtons[i] = tempSidebarButton;
		}


		JPanel sidebarBorder = new JPanel();
		sidebarBorder.setBounds(299, 100, 1, 500);
		sidebarBorder.setBackground(Colors.sidebarBorderColor);
		body.add(sidebarBorder);

		JPanel bottomBorder = new JPanel();
		bottomBorder.setBounds(0, 600, WIDTH, 1);
		bottomBorder.setBackground(Colors.sidebarBorderColor);
		body.add(bottomBorder);

		for (int i = 0; i < 5; i++) {
			JPanel sidebarBorderDown = new JPanel();
			sidebarBorderDown.setBounds(0, (int) sidebarButtons[i].getBounds().getMaxY(),
					sidebarButtons[i].getBounds().width, 1);
			sidebarBorderDown.setBackground(Colors.sidebarBorderColor);
			// sidebarBorderDown.setAlwaysOnTop(true);
			body.add(sidebarBorderDown);
			body.add(sidebarButtons[i]);
		}

		for (int i = 0; i < 5; i++) {

			final Integer inner_I = new Integer(i);

			sidebarButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainClass.handleUpdatePageDisplay(sidebarDisplayCodes[inner_I], createPanel);
				}
			});
		}
	}


	protected void generateNestedWindow() 
	{
		String html;
		html = "<html><head></head><body bgcolor=\"" + Colors.backgroundColorHex + "\" >";
		html += "</body></html>";
		resultsHtmlTableView = new JEditorPane("text/html", html);

		resultsHtmlTableView.setBounds(300, 100, WIDTH - 300, 500);
		resultsHtmlTableView.setEditable(false);

		nextPageField = new JLabel();
		nextPageField.setFont(new Font("Arial", Font.PLAIN, 17));
		nextPageField.setHorizontalAlignment(JLabel.CENTER);
		nextPageField.setBounds(900, 600, 100, 20);
		nextPageField.setForeground(Colors.bottomSectionTextColor);

		nextPageField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Check if last page or not
				if ((pageCount + 1) * 12 + 1 <= dataRows.size()) {
					pageCount += 1;
					resultsHtmlTableView.setText(generateTableHTML());
				} else
					JOptionPane.showMessageDialog(null, "No more data to show.", "End of data",
							JOptionPane.WARNING_MESSAGE);

			}
		});

		body.add(nextPageField);

		lastPageField = new JLabel();
		lastPageField.setFont(new Font("Arial", Font.PLAIN, 17));
		lastPageField.setHorizontalAlignment(JLabel.CENTER);
		lastPageField.setBounds(800, 600, 100, 20);
		lastPageField.setForeground(Colors.bottomSectionTextColor);

		lastPageField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pageCount -= 1;

				if (pageCount == -1) {
					pageCount = 0;
				}
				resultsHtmlTableView.setText(generateTableHTML());
			}
		});

		body.add(lastPageField);
		body.add(resultsHtmlTableView);
	}

	// Generates the table in HTML for results viewing
	public String generateTableHTML() {
		String html;
		html = "<html><head></head><body bgcolor=\"" + Colors.backgroundColorHex + "\" >";

		html += "<table>";

		html += "<tr>";
		html += "<th> </th>";
		for (int i = 0; i < dataRows.get(0).size(); i++) {
			html += "<th><h2><strong>  &nbsp; " + dataRows.get(0).get(i) + " &nbsp;  &nbsp; </h2></strong></th>";
		}
		html += "</tr>";

		// WITH PAGINATION
		for (int i = 1 + (pageCount * 12); i < 1 + ((pageCount + 1) * 12); i++) {
			if (i < dataRows.size()) {
				html += "<tr>";
				html += "<td>" + i + "</td>";
				for (int j = 0; j < dataRows.get(i).size(); j++) {
					html += "<td><font face=\"Arial\" size=\"6\">  &nbsp; " + dataRows.get(i).get(j) + "</font></td>";
				}
				html += "</tr>";
			}
		}

		html += "</table>";

		html += "</body></html>";
		return html;
	}

	private void generateCreatePanel()
	{
		createPanel = new CreatePanel(mainClass);
		createPanel.setBounds(300, 100, WIDTH - 300, 500);
		body.add(createPanel);
	}

	private void generateTutorialPanel()
	{
		tutorialPanel = new JPanel();
		tutorialPanel.setBounds(300, 100, WIDTH - 300, 500);
		tutorialPanel.setBackground(Color.red);
		tutorialPanel.setVisible(true);
		body.add(tutorialPanel);
	}

	private void generateExamplePanel()
	{
		examplePanel = new JPanel();
		examplePanel.setBounds(300, 100, WIDTH - 300, 500);
		examplePanel.setBackground(Color.blue);
		examplePanel.setVisible(true);
		body.add(examplePanel);
	}


	private void setAllPanelsInvisible()
	{
		resultsHtmlTableView.setVisible(false);
		createPanel.setVisible(false);
		examplePanel.setVisible(false);
		tutorialPanel.setVisible(false);
	}

	public void updateNestedWindow() {
		resultsHtmlTableView.setText("");
		lastPageField.setText("");
		nextPageField.setText("");
		setAllPanelsInvisible();

		if (currentPageCode.equals("tutorial")) 
		{

			// resultsHtmlTableView.setText("<html><head></head><body bgcolor=\"" + Colors.backgroundColorHex + "\" >"
			// 		+ "<font face=\"Arial\" size=\"6\">"
			// 		+ "Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, "
			// 		+ "Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, "
			// 		+ "Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, Tutorial, "
			// 		+ "</font>" + "</body></html>");
		}

		else if (currentPageCode.equals("create"))
		{
			createPanel.setVisible(true);
		}

		else if (currentPageCode.equals("view")) 
		{
			resultsHtmlTableView.setVisible(true);
			lastPageField.setText("Last Page");
			nextPageField.setText("Next Page");
			resultsHtmlTableView.setText(generateTableHTML());
		} 		

	}

	

	protected void setSQLOutput(List<List<String>> output) {
		dataRows = output;
	}

	protected void updatePageCode(String pg) {
		currentPageCode = pg;
		pageCount = 0;

		// myJFrame.getContentPane().setBackground(new java.awt.Color(245, 245, 245));

		// BELOW IS SOOO MUCH MORE CONVENIENT but takes FOREEEEVER (like 5+ seconds on a
		// good desktop)
		// body.removeAll();

		String[] sidebarDisplayCodes = { "tutorial", "example", "create", "view", "download" };
		for (int i = 0; i < 5; i++) {
			JButton tempSidebarButton = sidebarButtons[i];

			tempSidebarButton.setFont(new Font("Arial", Font.PLAIN, 24));
			tempSidebarButton.setBackground(Colors.sidebarBackgroundColor);
			tempSidebarButton.setForeground(Colors.sidebarTextColor);

			if (currentPageCode.equals(sidebarDisplayCodes[i])) {
				tempSidebarButton.setBackground(Colors.sidebarSelectedBackgroundColor);
				tempSidebarButton.setForeground(Colors.sidebarSelectedTextColor);
			}
		}
		updateNestedWindow();
	}

	MainWindow() {
		mainClass = new Football();
		/*
		 * Context: What is given to dynamically update the view Page Route - "tutorial"
		 * - "example" - "create" - "view" - Requires outputMatrix - "download" -
		 * Requires outputMatrix outputMatrix (optional for some routes) - Each ROW is a
		 * SQL row [ { name: "John" lastName: "Doe" age: 53 ... } ... ]
		 */

		currentPageCode = "tutorial";

		// Generate the main views
		generateMainWindow();
		generateMainLayout();
		// Generate all the tabbed views
		generateNestedWindow();
		generateCreatePanel();
		generateExamplePanel();
		generateTutorialPanel();
		// Update with opening panel
		updateNestedWindow();

		body.repaint();
	}
}
