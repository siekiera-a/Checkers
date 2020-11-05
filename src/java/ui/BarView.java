package ui;

import record.Reader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

class BarView extends JPanel {

    private final static String format = "<html>%s</html>";
    private final JButton prevButton;
    private final JButton nextButton;
    private final JLabel label;

    /***
     *
     * @param width component width
     * @param reader the object from which data for the replay will be read
     * @param repaint method which will be executed, when we click next/previous button
     */
    BarView(int width, Reader reader, Runnable repaint) {
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        label = new JLabel();

        label.setForeground(Color.WHITE);
        label.setText(String.format(format, reader.getMove().getPlayer()));

        nextButton.addActionListener(e -> {
            reader.nextMove();
            repaint.run();
            label.setText(String.format(format, reader.getMove().getPlayer()));
        });

        prevButton.addActionListener(e -> {
            reader.previousMove();
            repaint.run();
            label.setText(String.format(format, reader.getMove().getPlayer()));
        });

        add(prevButton, BorderLayout.LINE_START);
        add(label, BorderLayout.CENTER);
        add(nextButton, BorderLayout.LINE_END);

        setPreferredSize(new Dimension(width, 50));
    }

}
