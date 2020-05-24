package ui;

import javax.swing.JFrame;
import java.awt.HeadlessException;

public class Frame extends JFrame {

    public Frame(String title, int width, int height) throws HeadlessException {
        super(title);

        GamePanel panel = new GamePanel(width, height);
        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
}
