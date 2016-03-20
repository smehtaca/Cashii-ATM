import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by aumka on 3/20/2016.
 */
public class TitleScreenPanel extends JPanel
{
    BufferedImage img;
    JLabel label;

    public TitleScreenPanel()
    {
        try {
            img = ImageIO.read(getClass().getResource("logo.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        label = new JLabel(new ImageIcon(img));
        add(label);
        setVisible(true);
    }
}