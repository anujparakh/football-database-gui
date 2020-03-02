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
    private JComboBox<String> joinTableDropdown;
    private JComboBox<String> joinColumnDropdown;
    private JComboBox<String> joinTypeDropdown;
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
        var tables = football.getListOfTables();
        tableDropdown = new JComboBox(tables);
        tableDropdown.setBounds(230, 130, 200, 30);
        tableDropdown.setForeground(Colors.nestedTextColor);
        tableDropdown.addItemListener(listener);
        add(tableDropdown);

        String[] empty = {"None"};
        joinTableDropdown = new JComboBox(empty);
        joinTableDropdown.setBounds(230, 200, 150, 30);
        joinTableDropdown.setForeground(Colors.nestedTextColor);
        joinTableDropdown.addItemListener(listener);
        add(joinTableDropdown);
        
        JLabel joinOnColLabel = new JLabel("On Column", SwingConstants.CENTER);
        joinOnColLabel.setBounds(390, 200, 90, 30);
        joinOnColLabel.setFont(new Font("Ariel", Font.PLAIN, 15));
        joinOnColLabel.setForeground(Colors.nestedTextColor);
        add(joinOnColLabel);

        joinColumnDropdown = new JComboBox(empty);
        joinColumnDropdown.setBounds(490, 200, 200, 30);
        joinColumnDropdown.setForeground(Colors.nestedTextColor);
        add(joinColumnDropdown);

        JLabel joinTypeLabel = new JLabel("With Type", SwingConstants.CENTER);
        joinTypeLabel.setBounds(700, 200, 80, 30);
        joinTypeLabel.setFont(new Font("Ariel", Font.PLAIN, 15));
        joinTypeLabel.setForeground(Colors.nestedTextColor);
        add(joinTypeLabel);

        String []types = {"Inner", "Left", "Right", "Full"};
        joinTypeDropdown = new JComboBox(types);
        joinTypeDropdown.setBounds(790, 200, 100, 30);
        joinTypeDropdown.setForeground(Colors.nestedTextColor);
        add(joinTypeDropdown);

        columnDropdowns = new ArrayList<JComboBox<String>>();
        for (int i = 0; i < numColumnInputs; ++i)
        {
            String[] columns = {"None"};
            JComboBox<String> dropdown = new JComboBox(columns);
            dropdown.setBounds(230 + i * (120+15), 270, 120, 30);
            dropdown.setForeground(Colors.nestedTextColor);
            add(dropdown);
            columnDropdowns.add(dropdown);
        }

        allColumnsCheckbox = new JCheckBox("All Columns");
        allColumnsCheckbox.setBounds(770, 300, 120, 50);
        add(allColumnsCheckbox);
        
        String[] years = {"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013"};
        yearDropdown = new JComboBox(years);
        yearDropdown.setBounds(230, 410, 200, 30);
        add(yearDropdown);

        limitField = new JTextField();
        limitField.setBounds(230, 342, 200, 26);
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

                // if the state combobox is changed 
                if (e.getSource() == tableDropdown)
                {
                    if (e.getStateChange() != e.SELECTED)
                        return;
                    String table = String.valueOf(tableDropdown.getSelectedItem());
                    if (! table.equals("None"))
                    {
                        // update Join Table options
                        var tables = football.getListOfTables();
                        joinTableDropdown.removeAllItems();
                        for (var toAdd: tables)
                        {
                            if (!toAdd.equals(table))
                                joinTableDropdown.addItem(toAdd);
                        }

                        // Update column selection options
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
                        joinTableDropdown.removeAllItems();
                        joinTableDropdown.addItem("None");
                        joinColumnDropdown.removeAllItems();
                        joinColumnDropdown.addItem("None");
                        for (var columnDropdown: columnDropdowns)
                        {
                            columnDropdown.removeAllItems();
                            columnDropdown.addItem("None");
                        }
                        repaint();
                    }
                } 
                else if(e.getSource() == joinTableDropdown)
                {
                    if (e.getStateChange() != e.SELECTED)
                        return;
                    String table = String.valueOf(joinTableDropdown.getSelectedItem());
                    if (! table.equals("None"))
                    {
                        // Update column selection options
                        ArrayList<String> newColumns = football.getAllColumnsForTable(table);
                        joinColumnDropdown.removeAllItems();
                        for (var column: newColumns)
                        {
                            for (int i = 0; i < columnDropdowns.get(0).getItemCount(); ++i)
                                if (column.equals(columnDropdowns.get(0).getItemAt(i)))
                                    joinColumnDropdown.addItem(column);
                        }
                        repaint();

                        allColumnsCheckbox.setSelected(true);
                        allColumnsCheckbox.setEnabled(false);
                    }
                    else
                    {
                        // Empty join column options
                        joinColumnDropdown.removeAllItems();
                        joinColumnDropdown.addItem("None");
                        allColumnsCheckbox.setEnabled(true);
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
            return "Please select a table.";

        // Make sure limit is a positive integer
        try
        {
            int limit = Integer.parseInt(limitField.getText());
            if (limit <= 0)
                return "Limit must be more than 0.";
        }
        catch(Exception ex)
        {
            return "Limit Entry Error.";
        }

        // Make sure atleast one column is selected
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
            return "No Columns Selected.";

        // If table join selected, make sure column selected
        if (!String.valueOf(joinTableDropdown.getSelectedItem()).equals("None"))
            if (String.valueOf(joinColumnDropdown.getSelectedItem()).equals("None"))
                return "Choose a column to join tables with.";


        // No problems
        return "";
    }

    String checkIfViewable()
    {
        // Data too big
        if (Integer.parseInt(limitField.getText()) > 50)
        {
            return "Data is too big. Please click download to output into csv file.";
        }
        
        // All columns selected
        if (allColumnsCheckbox.isSelected())
            return "Cannot show all columns in app. Please click download to output into csv file.";

        // If table join selected, can only output to csv
        if (!String.valueOf(joinTableDropdown.getSelectedItem()).equals("None"))
            return "Table join requested. Cannnot show all the data in app. Please click download to output into csv file.";

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

        String joinTable = String.valueOf(joinTableDropdown.getSelectedItem());
        String joinColumn = String.valueOf(joinColumnDropdown.getSelectedItem());
        String joinType = String.valueOf(joinTypeDropdown.getSelectedItem());
        if (!joinTable.equals("None"))
        {
            query += " " + joinType + " JOIN " + joinTable + " ON " + table + "." + joinColumn + " = " + joinTable + "." + joinColumn;
        }

        String year = String.valueOf(yearDropdown.getSelectedItem());
        query += " where " + table + ".year = " + year; 
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