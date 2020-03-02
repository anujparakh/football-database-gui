import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class TutorialPanel extends JPanel
{
    TutorialPanel()
    {
        super();
        ImageIcon ii = loadImage();
        JLabel label = new JLabel(ii);
        label.setBounds(0, 0, 900, 500);
        label.setVisible(true);
        add(label);
        repaint();
    }

    private ImageIcon loadImage() {

        ImageIcon ii = new ImageIcon("imgs/tutorialImage.png");
        System.out.println ("Image loaded");
        return new ImageIcon(ii.getImage().getScaledInstance(900, 500, Image.SCALE_DEFAULT));
    }
}