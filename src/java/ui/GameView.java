package ui;

import logic.Field;
import logic.Position;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameView extends JPanel {

    private final Color blackKingFill = new Color(35, 35, 35);
    private final Color whiteKingFill = new Color(190, 190, 190);
    private final int padding = 5;
    protected final int fieldWidth;
    protected final int fieldHeight;
    private final static int BOARD_WIDTH = 8;
    private final static int BOARD_HEIGHT = 8;

    protected GameView(int width, int height) {
        fieldWidth = width / 8;
        fieldHeight = height / 8;

        setPreferredSize(new Dimension(width, height));
    }

    protected void drawChessboard(Graphics g) {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                Color color = (row + column) % 2 == 0 ? Color.LIGHT_GRAY : Color.GRAY;
                drawRect(row, column, color, g);
            }
        }
    }

    protected void drawCheckers(Field[][] fields, Graphics g) {
        for (int row = 0; row < fields.length; row++) {
            for (int column = 0; column < fields[row].length; column++) {
                Field field = fields[row][column];
                if (field != Field.EMPTY) {
                    drawField(row, column, field, g);
                }
            }
        }
    }

    protected void drawBorder(Position position, Color color, Graphics g) {
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

    protected void drawRect(int row, int column, Color color, Graphics g) {
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
}
