import javax.swing.*;
import java.awt.*;

public class HeadingPanel extends JPanel
{
    private int WIDTH;
    private int HEIGHT;
    private void initializeViews()
    {
        setBackground(Colors.headerBackgroundColor);

        JLabel titleTextField = new JLabel("ourSQL");
		titleTextField.setFont(new Font("Arial", Font.PLAIN, 45));
		titleTextField.setHorizontalAlignment(JLabel.CENTER);
		titleTextField.setBounds(0, 10, WIDTH, 50);
		titleTextField.setForeground(Colors.headingTextColor);

		JLabel subTitleTextField = new JLabel("Your all in one solution for sports statistics needs");
		subTitleTextField.setFont(new Font("Arial", Font.PLAIN, 30));
		subTitleTextField.setHorizontalAlignment(JLabel.CENTER);
		subTitleTextField.setBounds(0, 60, WIDTH, 30);
		subTitleTextField.setForeground(Colors.subHeadingTextColor);

		JPanel dividerPanel = new JPanel();
		dividerPanel.setBounds(0, 98, WIDTH, 2);
        dividerPanel.setBackground(Colors.dividerColor);
        
        add (titleTextField);
        add (subTitleTextField);
        add (dividerPanel);
    }

    HeadingPanel(int width, int height)
    {
        super();
        WIDTH = width;
        HEIGHT = height;
        setLayout(null);
        initializeViews();
    }
}