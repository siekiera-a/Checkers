package ui;

import record.Reader;

import java.awt.Graphics;

class ReplayView extends GameView {

    private final Reader reader;

    ReplayView(int width, int height, Reader reader) {
        super(width, height);
        this.reader = reader;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChessboard(g);
        drawCheckers(reader.getMove().getBoard(), g);
    }

}
