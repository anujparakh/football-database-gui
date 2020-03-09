import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class QuestionsPanel extends JPanel implements ActionListener
{
    private int currentQuestionNumber = 0;

    private Football footballDb;
    private JLabel questionDescriptionLabel;
    private JLabel q1Team1Label;
    private JLabel allQuestionsDropdownLabel;
    private JComboBox<String> q1Team1Dropdown;
    private JComboBox<String> q1Team2Dropdown;
    private JComboBox<String> q3TeamDropdown;
    private JComboBox<String> q4ConferenceDropdown;
    private JComboBox<String> q5ConferenceDropdown;
    
    private ArrayList<String> questionDescriptions;

    private JLabel createSideLabel(String text, int x, int y)
    {
        JLabel toCreate = new JLabel(text, SwingConstants.CENTER);
        toCreate.setBounds(x, y, 220, 50);
        toCreate.setFont(new Font("Arial", Font.PLAIN, 23));
        toCreate.setForeground(Colors.createLabelTextColor);
        toCreate.setBackground(Colors.createLabelBackgroundColor);
        toCreate.setOpaque(true);
        return toCreate;
    }

    private void initializeLabels()
    {
        setBackground(Color.white);
	
		questionDescriptionLabel = new JLabel("", SwingConstants.CENTER);
		questionDescriptionLabel.setBounds(50, 0, MainWindow.WIDTH - 400, 250);
		questionDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        questionDescriptionLabel.setForeground(Colors.nestedHeadingTextColor);
        add(questionDescriptionLabel);
        
        q1Team1Label = createSideLabel("Select Team 1", 50, 275);
        q1Team1Label.setVisible(false);
        add(q1Team1Label);

        allQuestionsDropdownLabel = createSideLabel("Select Team 2", 50, 350);
        add(allQuestionsDropdownLabel);
    }

    private void initializeDropDowns()
    {
        String[] empty = {"None"};
        String[] teams = footballDb.getListOfTeams();
        String[] conferences = footballDb.getListOfConferences();

        q1Team1Dropdown = new JComboBox(teams);
        q1Team1Dropdown.setBounds(290, 285, 200, 30);
        q1Team1Dropdown.setForeground(Colors.nestedTextColor);
        q1Team1Dropdown.setSelectedItem("Texas A&M");
        add(q1Team1Dropdown);

        q1Team2Dropdown = new JComboBox(teams);
        q1Team2Dropdown.setBounds(290, 360, 200, 30);
        q1Team2Dropdown.setForeground(Colors.nestedTextColor);
        add(q1Team2Dropdown);

        q3TeamDropdown = new JComboBox(teams);
        q3TeamDropdown.setBounds(290, 360, 200, 30);
        q3TeamDropdown.setForeground(Colors.nestedTextColor);
        q3TeamDropdown.setSelectedItem("Texas A&M");
        add(q3TeamDropdown);

        q4ConferenceDropdown = new JComboBox(conferences);
        q4ConferenceDropdown.setBounds(290, 360, 200, 30);
        q4ConferenceDropdown.setForeground(Colors.nestedTextColor);
        add(q4ConferenceDropdown);

        q5ConferenceDropdown = new JComboBox(conferences);
        q5ConferenceDropdown.setBounds(290, 360, 200, 30);
        q5ConferenceDropdown.setForeground(Colors.nestedTextColor);
        add(q5ConferenceDropdown);

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

        JButton runButton = new JButton("Find Answer");
        runButton.setBounds(150, 440, 150, 50);
        runButton.setActionCommand("run");
        runButton.addActionListener(this);
        add(runButton);
    }

    private void hideEverything()
    {
        q1Team1Label.setVisible(false);
        q1Team1Dropdown.setVisible(false);
        q1Team2Dropdown.setVisible(false);
        q3TeamDropdown.setVisible(false);
        q4ConferenceDropdown.setVisible(false);
        q5ConferenceDropdown.setVisible(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        if ("next".equals(e.getActionCommand())) 
        {
            ++currentQuestionNumber;
            if (currentQuestionNumber == questionDescriptions.size())
                currentQuestionNumber = 0;
            setQuestion(currentQuestionNumber);
        }
        else if ("last".equals(e.getActionCommand())) 
        {
            --currentQuestionNumber;
            if (currentQuestionNumber < 0)
                currentQuestionNumber = questionDescriptions.size() - 1;
            setQuestion(currentQuestionNumber);
        }
        else if ("run".equals(e.getActionCommand()))
        {
            String answer = "";
            List<List<String>> q4Answer = new ArrayList<List<String>> ();
            switch (currentQuestionNumber)
            {
                case 0:
                    String team1 = String.valueOf(q1Team1Dropdown.getSelectedItem());
                    String team2 = String.valueOf(q1Team2Dropdown.getSelectedItem());

                    answer = footballDb.solveQuestion1(team1, team2);
                    break;
                case 1:
                    String team3 = String.valueOf(q3TeamDropdown.getSelectedItem());

                    answer = footballDb.solveQuestion3(team3);
                    break;
                case 2:
                    String conference4 = String.valueOf(q4ConferenceDropdown.getSelectedItem());

                    q4Answer = footballDb.solveQuestion4(conference4);
                    break;
                case 3:
                    String conference5 = String.valueOf(q5ConferenceDropdown.getSelectedItem());

                    answer = footballDb.solveQuestion5(conference5);
                    break;
                case 4:
                    break;
                default:
                    break;
            }

            // Question 4 is a special case
            if (currentQuestionNumber == 2)
            {
                String html = "";
                html = "<html><head></head><body>";

                html += "<table>";

                html += "<tr>";
                    for(int j = 0; j < q4Answer.get(0).size(); j++) {
                        html += "<td><b><font face=\"Arial\" size=\"4\">  &nbsp; "+ q4Answer.get(0).get(j) + "</font><b></td>";
                    }
        
                    html += "</tr>";

                for(int i = 1; i < q4Answer.size(); i++) {
                    html += "<tr>";
                    for(int j = 0; j < q4Answer.get(i).size(); j++) {
                        html += "<td><font face=\"Arial\" size=\"4\">  &nbsp; "+ q4Answer.get(i).get(j) + "</font></td>";
                    }
        
                    html += "</tr>";
                }
                html += "</table>";
                html += "</body></html>";
                JOptionPane.showMessageDialog(null, html, "Answer", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (currentQuestionNumber == 0)
            {
                String html = answer;
                JOptionPane.showMessageDialog(null, html, "Answer", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, answer, "Answer", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    void initializeDescriptions()
    {
        questionDescriptions = new ArrayList<String>();
        questionDescriptions.add("Question 1. How can team 1 get bragging rights over team 2 i.e. is there a victory chain between the two?");
        questionDescriptions.add("Question 3. Which team has the most rushing yards in a game against selected team?");
        questionDescriptions.add("Question 4. What is the average home team advantage (from 0 to 100) of all the teams in given conference?");
        questionDescriptions.add("Question 5. (Custom). In a given conference, which team scored the most points in a single game?");
        questionDescriptions.add("Bonus: something...");

    }

    void setQuestion (int questionNo)
    {
        hideEverything();
        questionDescriptionLabel.setText("<html> " + questionDescriptions.get(questionNo) + " </html>");

        switch (questionNo)
        {
            case 0:
                // Question 1
                q1Team1Label.setVisible(true);
                q1Team1Dropdown.setVisible(true);
                q1Team2Dropdown.setVisible(true);
                allQuestionsDropdownLabel.setText("Select Team 2");
                break;
            case 1:
                // Question 3
                allQuestionsDropdownLabel.setText("Select Team");
                q3TeamDropdown.setVisible(true);
                break;
            case 2:
                // Question 4
                allQuestionsDropdownLabel.setText("Select Conference");
                q4ConferenceDropdown.setVisible(true);
                break;
            case 3:
                // Question 5
                allQuestionsDropdownLabel.setText("Select Conference");
                q5ConferenceDropdown.setVisible(true);
                break;
            case 4:
                // Bonus
                break;
            default:
                break;
        }

    }
    

    QuestionsPanel(Football football)
    {
        super();
        footballDb = football;
        initializeDescriptions();
        setLayout(null);
        initializeDropDowns();
        initializeLabels();
        initializeButtons();
        setQuestion(0);
    }
}