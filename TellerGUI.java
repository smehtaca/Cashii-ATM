import javax.swing.*;
import java.awt.*;


// @TODO: Reorder code

public class TellerGUI extends JFrame
{
    private JFrame frame; // Normal frame
    private TitleScreenPanel titlePanel; // Panel for title image
    private JPanel loginItems;

    void init()
    {
        frame = new JFrame("Cashii 1.0");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        titlePanel = new TitleScreenPanel();
        loginItems = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);
        loginItems.setBackground(Color.DARK_GRAY);
        frame.add(titlePanel, BorderLayout.NORTH);
        addLoginItems();
        frame.add(loginItems, BorderLayout.CENTER);
        frame.pack();
    }

    void addLoginItems()
    {
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        userLabel.setForeground(Color.WHITE);
        loginItems.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        loginItems.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        loginItems.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);

        loginItems.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        loginItems.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(180, 80, 80, 25);
        loginItems.add(registerButton);
    }
}
