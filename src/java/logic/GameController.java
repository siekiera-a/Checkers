package logic;

import java.util.List;
import java.util.stream.Collectors;

public class GameController {

    private Chessboard chessboard;

    private GameController() {
        chessboard = Chessboard.createChessboard();
    }

    public static GameController startNewGame() {
        return new GameController();
    }

    /**
     * remove positions that are of the board
     */
    private List<Position> trimmedPositions(Pawn pawn) {
        Position pawnPosition = chessboard.getPawnPosition(pawn);
        return pawn.movePositions(pawnPosition)
            .stream()
            .filter(pos -> !chessboard.fieldOutOfBoard(pos))
            .collect(Collectors.toList());
    }

    /**
     * check if enemy is on given position
     */
    private boolean isEnemy(Position enemyPos, Pawn pawn) {
        Pawn enemy = chessboard.getPawn(enemyPos);
        return enemy != null && enemy.getColor() != pawn.getColor();
    }

    /**
     * check if on next diagonal field is enemy and behind enemy is empty field
     *
     * @return list of capture moves
     */
    private List<Position> capturePositions(Pawn pawn) {
        Position currentPosition = chessboard.getPawnPosition(pawn);
        return trimmedPositions(pawn)
            .stream()
            .filter(pos -> isEnemy(pos, pawn))
            .map(pos -> {  // next diagonal field
                int rowVector = pos.getY() - currentPosition.getY();
                int columnVector = pos.getX() - currentPosition.getX();
                return new Position(columnVector, rowVector);
            })
            .filter(chessboard::fieldEmpty)
            .collect(Collectors.toList());
    }

    /**
     * If have to capture, only capture moves are available
     *
     * @return available moves
     */
    private Move availableMoves(Pawn pawn) {
        Position pawnPosition = chessboard.getPawnPosition(pawn);
        List<Position> capturePositions = capturePositions(pawn);
        if (capturePositions.isEmpty()) {
            List<Position> availablePositions = trimmedPositions(pawn)
                .stream()
                .filter(chessboard::fieldEmpty)
                .collect(Collectors.toList());
            return new Move(pawnPosition, availablePositions);
        }
        return new Move(pawnPosition, capturePositions);
    }

}
