package logic;

public class Chessboard {

    public final static int BOARD_WIDTH = 8;
    public final static int BOARD_HEIGHT = 8;
    private Pawn[][] board;

    private Chessboard() {
        board = createEmptyBoard();
    }

    /**
     * Create chessboard
     *
     * @return new chessboard with placed pawns
     */
    public static Chessboard createChessboard() {
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
                board[row][column] = new Pawn(Pawn.Color.BLACK, Pawn.Direction.BOTTOM);
            }
        }

        // place white pawns
        for (int row = BOARD_HEIGHT - 3; row < BOARD_HEIGHT; row++) {
            for (int column = row % 2; column < BOARD_WIDTH; column += 2) {
                board[row][column] = new Pawn(Pawn.Color.WHITE, Pawn.Direction.TOP);
            }
        }

        return board;
    }

    public boolean fieldOutOfBoard(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        return x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT;
    }

    /**
     * Check if position exists on the board and is empty
     */
    public boolean fieldEmpty(Position pos) {
        return pos != null && !fieldOutOfBoard(pos) && board[pos.getY()][pos.getX()] == null;
    }

    /**
     * Get pawn position on the board
     *
     * @return If pawn is on the board return its position, otherwise null
     */
    public Position getPawnPosition(Pawn pawn) {
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
     */
    public void removePawn(Pawn pawn) {
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
    public Pawn getPawn(Position pos) {
        if (pos == null || fieldOutOfBoard(pos)) {
            return null;
        }

        return board[pos.getY()][pos.getX()];
    }

    /**
     * Move pawn at new position if exists and is on the board
     */
    public void movePawn(Pawn pawn, Position newPosition) {
        Position currentPosition = getPawnPosition(pawn);

        if (currentPosition != null && fieldEmpty(newPosition)) {
            board[newPosition.getY()][newPosition.getX()] = pawn;
            board[currentPosition.getY()][currentPosition.getX()] = null;
        }
    }

}
