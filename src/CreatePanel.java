import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.EventListener;

public class CreatePanel extends JPanel implements EventListener
{
    private final int numColumnInputs = 5;

    private static Football football;
    private JComboBox<String> tableDropdown;
    private ArrayList<JComboBox<String>> columnDropdowns;
    private JCheckBox allColumnsCheckbox;
    private JTextField limitField;
    private JComboBox<String> yearDropdown;

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
	
		JLabel label = new JLabel("Create Query", SwingConstants.CENTER);
		label.setBounds(0, 0, MainWindow.WIDTH - 300, 100);
		label.setFont(new Font("Arial", Font.PLAIN, 45));
        label.setForeground(Colors.nestedHeadingTextColor);
        add(label);
        
        JLabel tableLabel = createSideLabel("Select Table", 120);
        add(tableLabel);
        JLabel joinLabel = createSideLabel("Join Tables", 190);
        add(joinLabel);
        JLabel columnLabel = createSideLabel("Select Columns", 260);
        add(columnLabel);
        JLabel limitLabel = createSideLabel("Limit Rows", 330);
        add(limitLabel);
        JLabel yearLabel = createSideLabel("Year for Data", 400);
        add(yearLabel);
    }

    private void initializeDropDowns()
    {
        var tables = football.getListOfTables();
        tableDropdown = new JComboBox(tables);
        tableDropdown.setBounds(250, 120, 200, 50);
        tableDropdown.setForeground(Colors.nestedTextColor);
        tableDropdown.addItemListener(listener);
        add(tableDropdown);

        columnDropdowns = new ArrayList<JComboBox<String>>();
        for (int i = 0; i < numColumnInputs; ++i)
        {
            String[] columns = {"None"};
            JComboBox<String> dropdown = new JComboBox(columns);
            dropdown.setBounds(250 + i * (100+15), 260, 120, 50);
            dropdown.setForeground(Colors.nestedTextColor);
            add(dropdown);
            columnDropdowns.add(dropdown);
        }

        allColumnsCheckbox = new JCheckBox("All Columns");
        allColumnsCheckbox.setBounds(710, 300, 120, 50);
        add(allColumnsCheckbox);
        
        String[] years = {"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013"};
        yearDropdown = new JComboBox(years);
        yearDropdown.setBounds(250, 400, 200, 50);
        add(yearDropdown);

        limitField = new JTextField();
        limitField.setBounds(250, 342, 200, 26);
        limitField.setFont(new Font("Arial", Font.PLAIN, 18));
        limitField.setText("10");
        add(limitField);

    }

    // For event listener
    private static ItemListener listener;
    
    private void setListener()
    {
        listener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent e) 
            {
                System.out.println("Got event change");

                // if the state combobox is changed 
                if (e.getSource() == tableDropdown)
                {
                    if (e.getStateChange() != e.SELECTED)
                        return;
                    String table = String.valueOf(tableDropdown.getSelectedItem());
                    if (! table.equals("None"))
                    {
                        ArrayList<String> newColumns = football.getAllColumnsForTable(table);
                        for (var columnDropdown: columnDropdowns)
                        {
                            columnDropdown.removeAllItems();
                            for (var column: newColumns)
                            {
                                columnDropdown.addItem(column);
                            }
                        }
                        repaint();
                    }
                    else
                    {
                        for (var columnDropdown: columnDropdowns)
                        {
                            columnDropdown.removeAllItems();
                            columnDropdown.addItem("None");
                        }
                        repaint();
                    }
                } 
            }
        };
    }

    String checkProblems()
    {
        // Actual Problems
        String table = String.valueOf(tableDropdown.getSelectedItem());
        if (table == "None")
            return "No table selected";
        try
        {
            Integer.parseInt(limitField.getText());
        }
        catch(Exception ex)
        {
            return "Limit Entry Error";
        }

        boolean flag = true;
        for (var columnDropdown: columnDropdowns)
        {
            String column = String.valueOf(columnDropdown.getSelectedItem());
            if (!column.equals("None"))
            {
                flag = false;
                break;
            }
        }
        if (flag && !allColumnsCheckbox.isSelected())
        {
            return "No Columns Selected";
        }
        return "";
    }

    String checkIfViewable()
    {
        // Data too big
        if (Integer.parseInt(limitField.getText()) > 50)
        {
            return "Data too big, click download to output into .csv file.";
        }
        
        // All columns selected
        if (allColumnsCheckbox.isSelected())
            return "Cannot show all columns in app. Please click download to output into csv file.";

        return "";
    }

    String convertToQuery ()
    {
        String query = "select ";
        String table = String.valueOf(tableDropdown.getSelectedItem());

        if (allColumnsCheckbox.isSelected())
        {
            query += "* ";
        }
        else
        { 
            for (var columnDropdown: columnDropdowns)
            {
                String column = String.valueOf(columnDropdown.getSelectedItem());
                if (!column.equals("None"))
                    query += column + ", ";
            }
            // get rid of last ", "
            query = query.substring(0, query.length() - 2);
        }

        query += " from " + table;
        String year = String.valueOf(yearDropdown.getSelectedItem());
        query += " where " + " year = " + year; 
        query += " limit " +  Integer.parseInt(limitField.getText()) + ";";
        return query;
    }

    // Constructor
    CreatePanel(Football football_db)
    {
        football = football_db;
        setLayout(null);
        setListener();
        initializeLabels();
        initializeDropDowns();
    }
}