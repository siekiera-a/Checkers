package logic;

import java.util.ArrayList;
import java.util.List;

class Chessboard {

    private final static int BOARD_WIDTH = 8;
    private final static int BOARD_HEIGHT = 8;
    private Pawn[][] board;

    private Chessboard() {
        board = createEmptyBoard();
    }

    /**
     * Create chessboard
     *
     * @return new chessboard with placed pawns
     */
    static Chessboard createChessboard() {
        return new Chessboard();
    }

    /**
     * Place black pawns on top, white pawns on bottom
     *
     * @return array with placed pawns
     */
    private Pawn[][] createEmptyBoard() {
        // left top corner field is empty
        Pawn[][] board = new Pawn[BOARD_HEIGHT][BOARD_WIDTH];

        // place black pawns
        for (int row = 0; row < 3; row++) {
            for (int column = (row + 1) % 2; column < BOARD_WIDTH; column += 2) {
                board[row][column] = new Pawn(Player.BLACK, Pawn.Direction.BOTTOM);
            }
        }

        // place white pawns
        for (int row = BOARD_HEIGHT - 3; row < BOARD_HEIGHT; row++) {
            for (int column = (row + 1) % 2; column < BOARD_WIDTH; column += 2) {
                board[row][column] = new Pawn(Player.WHITE, Pawn.Direction.TOP);
            }
        }

        return board;
    }

    /**
     * Check if field on the given position is out of the board
     *
     * @param pos position to check
     * @return true if field is out of the board, otherwise false
     */
    boolean fieldOutOfBoard(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        return x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT;
    }

    /**
     * Check if field on given position exists on the board and is empty
     *
     * @return true if field is empty and exists, otherwise false
     */
    boolean fieldEmpty(Position pos) {
        return pos != null && !fieldOutOfBoard(pos) && board[pos.getY()][pos.getX()] == null;
    }

    /**
     * Get pawn position on the board
     *
     * @return If pawn is on the board return its position, otherwise null
     */
    Position getPawnPosition(Pawn pawn) {
        if (pawn == null) {
            return null;
        }

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                if (board[row][column] == pawn) {
                    return new Position(column, row);
                }
            }
        }

        return null;
    }

    /**
     * Remove pawn from the board
     *
     * @param pawn pawn to remove
     */
    void removePawn(Pawn pawn) {
        Position pawnPos = getPawnPosition(pawn);

        if (pawnPos != null) {
            board[pawnPos.getY()][pawnPos.getX()] = null;
        }
    }

    /**
     * Return pawn on given position
     *
     * @return Pawn or null
     */
    Pawn getPawn(Position pos) {
        if (pos == null || fieldOutOfBoard(pos)) {
            return null;
        }

        return board[pos.getY()][pos.getX()];
    }

    /**
     * Move pawn at new position if exists and is on the board
     *
     * @param pawn        pawn to move
     * @param newPosition new pawn position
     */
    void movePawn(Pawn pawn, Position newPosition) {
        Position currentPosition = getPawnPosition(pawn);

        if (currentPosition != null && fieldEmpty(newPosition)) {
            board[newPosition.getY()][newPosition.getX()] = pawn;
            board[currentPosition.getY()][currentPosition.getX()] = null;
        }
    }

    /***
     * Remove the pawn from the chessboard and place the king in his place
     * @param pawn pawn to promote
     */
    void promoteToKing(Pawn pawn) {
        Position pawnPosition = getPawnPosition(pawn);
        if (pawnPosition != null) {
            King king = new King(pawn.getPlayer());
            removePawn(pawn);
            board[pawnPosition.getY()][pawnPosition.getX()] = king;
        }
    }

    /**
     * Get player pawns
     *
     * @return List of player pawns
     */
    List<Pawn> getPlayerPawns(Player player) {
        List<Pawn> pawns = new ArrayList<>();
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                Pawn pawn = board[row][column];
                if (pawn != null && pawn.getPlayer() == player) {
                    pawns.add(pawn);
                }
            }
        }
        return pawns;
    }

    /***
     * transform chessboard to array of fields
     *
     * @return chessboard state
     */
    Field[][] getFields() {
        Field[][] states = new Field[BOARD_HEIGHT][BOARD_HEIGHT];

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                Pawn pawn = board[row][column];
                if (pawn == null) {
                    states[row][column] = Field.EMPTY;
                } else if (pawn instanceof King) {
                    states[row][column] = pawn.getPlayer() ==
                        Player.BLACK ? Field.BLACK_KING : Field.WHITE_KING;
                } else {
                    states[row][column] = pawn.getPlayer() ==
                        Player.BLACK ? Field.BLACK_PAWN : Field.WHITE_PAWN;
                }
            }
        }

        return states;
    }

}
