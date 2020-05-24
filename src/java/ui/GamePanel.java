package ui;

import logic.Field;
import logic.Move;
import logic.Player;
import logic.Position;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class GamePanel extends JPanel implements MouseListener {

    private final Color blackKingFill = new Color(35, 35, 35);
    private final Color whiteKingFill = new Color(190, 190, 190);
    private final int padding = 5;
    private int fieldWidth;
    private int fieldHeight;
    private final static int BOARD_WIDTH = 8;
    private final static int BOARD_HEIGHT = 8;
    private final Color helperColor = Color.GREEN;
    private PlayerController playerController;

    public GamePanel(int width, int height) {
        fieldWidth = width / 8;
        fieldHeight = height / 8;

        playerController = new PlayerController(fieldWidth, fieldHeight);
        setPreferredSize(new Dimension(width, height));
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

    private void drawChessboard(Graphics g) {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                Color color = (row + column) % 2 == 0 ? Color.LIGHT_GRAY : Color.GRAY;
                drawRect(row, column, color, g);
            }
        }
    }

    private void drawCheckers(Field[][] fields, Graphics g) {
        for (int row = 0; row < fields.length; row++) {
            for (int column = 0; column < fields[row].length; column++) {
                Field field = fields[row][column];
                if (field != Field.EMPTY) {
                    drawField(row, column, field, g);
                }
            }
        }
    }

    private void drawBorder(Position position, Color color, Graphics g) {
        int[] coordinates = calcPositions(position.getY(), position.getX());
        int thickness = 3;
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.setColor(color);
        g.drawRect(coordinates[0], coordinates[1], fieldWidth - 1, fieldHeight - 1);
    }

    /***
     * Calculate left top corner position of the field
     * @return [x, y] position
     */
    private int[] calcPositions(int row, int column) {
        int[] positions = new int[2];
        positions[0] = column * fieldWidth;
        positions[1] = row * fieldHeight;
        return positions;
    }

    private void drawRect(int row, int column, Color color, Graphics g) {
        g.setColor(color);
        int[] positions = calcPositions(row, column);
        g.fillRect(positions[0], positions[1], fieldWidth, fieldHeight);
    }

    private void drawField(int row, int column, Field state, Graphics g) {
        int[] positions = calcPositions(row, column);
        int x = positions[0];
        int y = positions[1];


        switch (state) {
            case BLACK_PAWN: {
                drawPawn(x, y, Color.BLACK, g);
                break;
            }
            case BLACK_KING: {
                drawKing(x, y, Color.BLACK, g);
                break;
            }
            case WHITE_PAWN: {
                drawPawn(x, y, Color.WHITE, g);
                break;
            }
            case WHITE_KING: {
                drawKing(x, y, Color.WHITE, g);
                break;
            }
        }
    }

    private void drawPawn(int x, int y, Color color, Graphics g) {
        g.setColor(color);
        g.fillOval(x + padding, y + padding, fieldWidth - 2 * padding, fieldHeight - 2 * padding);
    }

    private void drawKing(int x, int y, Color color, Graphics g) {
        drawPawn(x, y, color, g);

        float fillPercentage = 0.7f;
        int width = (int) ((fieldWidth - 2 * padding) * fillPercentage);
        int height = (int) ((fieldHeight - 2 * padding) * fillPercentage);

        Color c = (color == Color.BLACK) ? blackKingFill : whiteKingFill;

        g.setColor(c);
        g.fillOval(x + (fieldWidth - width) / 2, y + (fieldHeight - height) / 2, width, height);
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
