package ui;

import logic.Field;
import logic.GameController;
import logic.Player;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GamePanel extends JPanel {

    private final Color blackKingFill = new Color(35, 35, 35);
    private final Color whiteKingFill = new Color(190, 190, 190);
    private final float fillPercentage = 0.7f;
    private final int padding = 5;
    private int fieldWidth;
    private int fieldHeight;
    private GameController game;

    public GamePanel(int width, int height) {
        fieldWidth = width / 8;
        fieldHeight = height / 8;

        game = GameController.startNewGame(Player.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Field[][] fields = game.getFields();
        drawCheckers(fields, g);
    }

    private void drawCheckers(Field[][] fields, Graphics g) {
        for (int row = 0; row < fields.length; row++) {
            for (int column = 0; column < fields[row].length; column++) {
                Color color = (row + column) % 2 == 0 ? Color.LIGHT_GRAY : Color.GRAY;
                drawRect(row, column, color, g);
                Field field = fields[row][column];
                if (field != Field.EMPTY) {
                    drawField(row, column, field, g);
                }
            }
        }
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

        int width = (int) ((fieldWidth - 2 * padding) * fillPercentage);
        int height = (int) ((fieldHeight - 2 * padding) * fillPercentage);

        Color c = (color == Color.BLACK) ? blackKingFill : whiteKingFill;

        g.setColor(c);
        g.fillOval(x + (fieldWidth - width) / 2, y + (fieldHeight - height) / 2, width, height);
    }
}
