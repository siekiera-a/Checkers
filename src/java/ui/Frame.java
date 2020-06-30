package ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import java.util.List;

public class Frame extends JFrame {

    public Frame(String title, int width, int height) throws HeadlessException {
        super(title);

        GamePanel panel = new GamePanel(width, height);
        add(panel);

        JMenuBar bar = new JMenuBar();
        bar.add(createMenu());
        setJMenuBar(bar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    private JMenu createMenu() {
        JMenu menu = new JMenu("Gra");

        JMenuItem rules = new JMenuItem("Zasady");
        JMenuItem exit = new JMenuItem("Zamknij");

        rules.addActionListener(e -> displayRules());
        exit.addActionListener(e -> System.exit(1));

        menu.add(rules);
        menu.add(exit);

        return menu;
    }

    private void displayRules() {
        List<String> rules = List.of(
            "Zasady gry:",
            "1. Pionek może bić tylko do przodu o 1",
            "2. Król może bić do przodu i do tyłu o 1",
            "3. Jeśli możemy zbić pionka przeciwnika, trzeba to zrobić",
            "4. Można zbijac kilka pionków w jednej turze",
            "5. Zwykły pionek może wykonywać wiele bić, ale ruchy zawsze muszą być do przodu",
            "6. Jeśli pionek dojdzie do końca szachownicy, staje się Królem",
            "7. Jeśli gracz nie ma żadnego możliwego ruchu to przegrał"
        );

        JOptionPane.showMessageDialog(null,
            String.join("\n", rules), "Zasady",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
