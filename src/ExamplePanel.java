import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ExamplePanel extends JPanel implements ActionListener
{

    private final int numColumnInputs = 5;

    private int currentExampleNumber = 0;

    private JLabel descriptionLabel;
    private JComboBox<String> tableDropdown;
    private JComboBox<String> joinTableDropdown;
    private JComboBox<String> joinColumnDropdown;
    private JComboBox<String> joinTypeDropdown;
    private ArrayList<JComboBox<String>> columnDropdowns;
    private JCheckBox allColumnsCheckbox;
    private JTextField limitField;
    private JComboBox<String> yearDropdown;

    private ArrayList<Dictionary> exampleQueries;

    private JLabel createSideLabel(String text, int y)
    {
        JLabel toCreate = new JLabel(text, SwingConstants.CENTER);
        toCreate.setBounds(10, y, 200, 50);
        toCreate.setFont(new Font("Arial", Font.PLAIN, 23));
        toCreate.setForeground(Colors.createLabelTextColor);
        toCreate.setBackground(Colors.createLabelBackgroundColor);
        toCreate.setOpaque(true);
        return toCreate;
    }

    private void initializeLabels()
    {
        setBackground(Color.white);
	
		descriptionLabel = new JLabel("", SwingConstants.CENTER);
		descriptionLabel.setBounds(0, 0, MainWindow.WIDTH - 300, 100);
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        descriptionLabel.setForeground(Colors.nestedHeadingTextColor);
        add(descriptionLabel);
        
        JLabel tableLabel = createSideLabel("Select Table", 120);
        add(tableLabel);
        JLabel joinLabel = createSideLabel("Join Table", 190);
        add(joinLabel);
        // JLabel joinColumnLabel = createSideLabel("On Column", 190);
        // joinColumnLabel.setBounds(480, 190, 200, 50);
        // add(joinColumnLabel);
        JLabel columnLabel = createSideLabel("Select Columns", 260);
        add(columnLabel);
        JLabel limitLabel = createSideLabel("Limit Rows", 330);
        add(limitLabel);
        JLabel yearLabel = createSideLabel("Year for Data", 400);
        add(yearLabel);
    }

    private void initializeDropDowns()
    {
        String[] empty = {"None"};
        tableDropdown = new JComboBox(empty);
        tableDropdown.setBounds(230, 130, 200, 30);
        tableDropdown.setForeground(Colors.nestedTextColor);
        add(tableDropdown);

        joinTableDropdown = new JComboBox(empty);
        joinTableDropdown.setBounds(230, 200, 150, 30);
        joinTableDropdown.setForeground(Colors.nestedTextColor);
        add(joinTableDropdown);
        
        JLabel joinOnColLabel = new JLabel("On Column", SwingConstants.CENTER);
        joinOnColLabel.setBounds(390, 200, 80, 30);
        joinOnColLabel.setFont(new Font("Ariel", Font.PLAIN, 13));
        joinOnColLabel.setForeground(Colors.nestedTextColor);
        add(joinOnColLabel);

        joinColumnDropdown = new JComboBox(empty);
        joinColumnDropdown.setBounds(480, 200, 190, 30);
        joinColumnDropdown.setForeground(Colors.nestedTextColor);
        add(joinColumnDropdown);

        JLabel joinTypeLabel = new JLabel("With Type", SwingConstants.CENTER);
        joinTypeLabel.setBounds(680, 200, 70, 30);
        joinTypeLabel.setFont(new Font("Ariel", Font.PLAIN, 13));
        joinTypeLabel.setForeground(Colors.nestedTextColor);
        add(joinTypeLabel);

        joinTypeDropdown = new JComboBox(empty);
        joinTypeDropdown.setBounds(760, 200, 100, 30);
        joinTypeDropdown.setForeground(Colors.nestedTextColor);
        add(joinTypeDropdown);

        columnDropdowns = new ArrayList<JComboBox<String>>();
        for (int i = 0; i < numColumnInputs; ++i)
        {
            String[] columns = {"None"};
            JComboBox<String> dropdown = new JComboBox(columns);
            dropdown.setBounds(230 + i * (110+15), 270, 110, 30);
            dropdown.setForeground(Colors.nestedTextColor);
            add(dropdown);
            columnDropdowns.add(dropdown);
        }

        allColumnsCheckbox = new JCheckBox("All Columns");
        allColumnsCheckbox.setBounds(730, 300, 120, 50);
        add(allColumnsCheckbox);
        
        yearDropdown = new JComboBox(empty);
        yearDropdown.setBounds(230, 410, 200, 30);
        add(yearDropdown);

        limitField = new JTextField();
        limitField.setBounds(230, 342, 200, 26);
        limitField.setFont(new Font("Arial", Font.PLAIN, 18));
        limitField.setText("10");
        add(limitField);

    }

    void initializeButtons()
    {
        JButton lastButton = new JButton ("Last");
        lastButton.setBounds(650, 450, 100, 30);
        add(lastButton);
        lastButton.setActionCommand("last");
        lastButton.addActionListener(this);

        JButton nextButton = new JButton ("Next");
        nextButton.setBounds(760, 450, 100, 30);
        nextButton.setActionCommand("next");
        nextButton.addActionListener(this);
        add(nextButton);
    }

    public void actionPerformed(ActionEvent e)
    {
        if ("next".equals(e.getActionCommand())) 
        {
            ++currentExampleNumber;
            if (currentExampleNumber == exampleQueries.size())
                currentExampleNumber = 0;
            setExample(currentExampleNumber);
        }
        else if ("last".equals(e.getActionCommand())) 
        {
            --currentExampleNumber;
            if (currentExampleNumber < 0)
                currentExampleNumber = exampleQueries.size() - 1;
            setExample(currentExampleNumber);
        }
    }

    void initializeExamples()
    {
        exampleQueries = new ArrayList<Dictionary> ();

        // No. 1
        Dictionary<String,String> exampleQuery = new Hashtable();
        exampleQuery.put("description", "1. This is a query to find a list of 20 conferences from the year 2013.");
        exampleQuery.put("table", "conference");
        exampleQuery.put("join-table", "None");
        exampleQuery.put("join-column", "None");
        exampleQuery.put("join-type", "None");
        exampleQuery.put("column1", "conference_code");
        exampleQuery.put("column2",  "name");
        exampleQuery.put("all-columns", ""); // empty means false
        exampleQuery.put("limit", "20");
        exampleQuery.put("year", "2013");
        exampleQueries.add(exampleQuery);

        // No.2
        exampleQuery = new Hashtable();
        exampleQuery.put("description", "2. This is a query to get all the data of 200 of the players who played in 2010.");
        exampleQuery.put("table", "player");
        exampleQuery.put("join-table", "None");
        exampleQuery.put("join-column", "None");
        exampleQuery.put("join-type", "None");
        exampleQuery.put("column1", "None");
        exampleQuery.put("column2",  "None");
        exampleQuery.put("all-columns", "true"); // empty means false
        exampleQuery.put("limit", "200");
        exampleQuery.put("year", "2010");
        exampleQueries.add(exampleQuery);

         // No.3
         exampleQuery = new Hashtable();
         exampleQuery.put("description", "3. This query performs an inner join on the game and statistics tables.");
         exampleQuery.put("table", "game");
         exampleQuery.put("join-table", "statistics");
         exampleQuery.put("join-column", "stadium_code");
         exampleQuery.put("join-type", "Inner");
         exampleQuery.put("column1", "None");
         exampleQuery.put("column2",  "None");
         exampleQuery.put("all-columns", "true"); // empty means false
         exampleQuery.put("limit", "200");
         exampleQuery.put("year", "2013");
         exampleQueries.add(exampleQuery);

          // No.4
        exampleQuery = new Hashtable();
        exampleQuery.put("description", "4. This is a query to get the result of a left join on player and team.");
        exampleQuery.put("table", "player");
        exampleQuery.put("join-table", "team");
        exampleQuery.put("join-column", "team_code");
        exampleQuery.put("join-type", "Left");
        exampleQuery.put("column1", "None");
        exampleQuery.put("column2",  "None");
        exampleQuery.put("all-columns", "true"); // empty means false
        exampleQuery.put("limit", "500");
        exampleQuery.put("year", "2013");
        exampleQueries.add(exampleQuery);


    }

    void setDropdown(JComboBox <String> dropdown, String toSet)
    {
        dropdown.removeAllItems();
        dropdown.addItem(toSet);
    }

    void setExample(int exampleNo)
    {
        descriptionLabel.setText((String) exampleQueries.get(exampleNo).get("description"));
        setDropdown(tableDropdown, (String) exampleQueries.get(exampleNo).get("table"));
        setDropdown(joinTableDropdown, (String) exampleQueries.get(exampleNo).get("join-table"));
        setDropdown(joinColumnDropdown, (String) exampleQueries.get(exampleNo).get("join-column"));
        setDropdown(joinTypeDropdown, (String) exampleQueries.get(exampleNo).get("join-type"));
        for (int i = 0; i < 2; ++i)
        {
            setDropdown(columnDropdowns.get(i), (String) exampleQueries.get(exampleNo).get("column" + (i+1)));
        }

        // check if all-columns isn't empty
        if (((String) exampleQueries.get(exampleNo).get("all-columns")).length() > 0)
            allColumnsCheckbox.setSelected(true);
        else
            allColumnsCheckbox.setSelected(false);

        limitField.setText((String) exampleQueries.get(exampleNo).get("limit"));
        setDropdown(yearDropdown, (String) exampleQueries.get(exampleNo).get("year"));
    }

    ExamplePanel()
    {
        super();
        initializeExamples();
        setLayout(null);
        initializeLabels();
        initializeButtons();
        initializeDropDowns();
        setExample(0);
    }
}