package ui;

import logic.Field;
import logic.Move;
import logic.Player;
import logic.Position;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

class PlayerView extends GameView implements MouseListener {

    private final Color helperColor = Color.GREEN;
    private PlayerController playerController;

    PlayerView(int width, int height) {
        super(width, height);
        playerController = new PlayerController(fieldWidth, fieldHeight);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Field[][] fields = playerController.getFields();
        drawChessboard(g);
        drawAvailableMoves(playerController.getAvailableMoves(), g);
        Move activeMove = playerController.getActive();
        if (activeMove != null) {
            drawActiveMove(activeMove, g);
        }
        drawCheckers(fields, g);
    }

    private void drawActiveMove(Move move, Graphics g) {
        Position active = move.getStartPosition();
        drawRect(active.getY(), active.getX(), helperColor, g);
        move.getAvailablePositions()
            .forEach(pos -> drawRect(pos.getY(), pos.getX(), helperColor, g));
    }

    private void drawAvailableMoves(List<Move> moves, Graphics g) {
        moves.forEach(move -> drawBorder(move.getStartPosition(), helperColor, g));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        playerController.handleClick(e);

        repaint();
        if (playerController.gameEnded()) {
            String player = playerController.whoseTurn() == Player.BLACK ? "White" : "Black";
            JOptionPane.showMessageDialog(null, player + " won!");
            System.exit(1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
