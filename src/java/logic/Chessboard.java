package logic;

public class Chessboard {

    public final static int BOARD_WIDTH = 8;
    public final static int BOARD_HEIGHT = 8;
    private Pawn[][] board;

    private Chessboard() {
        board = createEmptyBoard();
    }

    public static Chessboard createChessboard() {
        return new Chessboard();
    }

    // left top corner field is empty
    private Pawn[][] createEmptyBoard() {
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

}
