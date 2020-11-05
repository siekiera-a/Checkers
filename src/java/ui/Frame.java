package ui;

import record.Reader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.List;
import java.util.Optional;

public class Frame extends JFrame {

    private GameView panel;
    private int width;
    private int height;

    public Frame(String title, int width, int height) throws HeadlessException {
        super(title);

        this.width = width;
        this.height = height;

        setLayout(new BorderLayout(0, 0));

        panel = new PlayerView(width, height);
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
        JMenuItem read = new JMenuItem("Wczytaj gre");
        JMenuItem exit = new JMenuItem("Zamknij");

        rules.addActionListener(e -> displayRules());
        read.addActionListener(e -> readGame().ifPresent(file -> {
            Component oldView = panel;
            try {
                Reader reader = new Reader(file.toPath());
                panel = new ReplayView(width, height, reader);

                remove(oldView);

                add(new BarView(width, reader, panel::repaint), BorderLayout.PAGE_START);
                add(panel, BorderLayout.PAGE_END);

                pack();
                repaint();
            } catch (Exception er) {
                JOptionPane.showMessageDialog(null, er.getMessage());
            }
        }));
        exit.addActionListener(e -> System.exit(1));

        menu.add(rules);
        menu.add(read);
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

    /***
     * Select file with replay data
     * @return file with data
     */
    private Optional<File> readGame() {
        JFileChooser fileChooser = new JFileChooser(".");

        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith("json");
            }

            @Override
            public String getDescription() {
                return "JSON files";
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.showOpenDialog(null);
        return Optional.ofNullable(fileChooser.getSelectedFile());
    }
}
