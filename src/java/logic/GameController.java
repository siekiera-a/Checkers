package logic;

import java.util.List;
import java.util.stream.Collectors;

public class GameController {

    private Chessboard chessboard;
    private Player player;

    private GameController(Player startingPlayer) {
        player = startingPlayer;
        chessboard = Chessboard.createChessboard();
    }

    public static GameController startNewGame(Player startingPlayer) {
        return new GameController(startingPlayer);
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
        return enemy != null && enemy.getPlayer() != pawn.getPlayer();
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
                int[] vector = getVector(currentPosition, pos);
                return new Position(pos.getX() + vector[0], pos.getY() + vector[1]);
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

    private void switchPlayer() {
        player = (player == Player.BLACK) ? Player.WHITE : Player.BLACK;
    }

    /***
     * Move pawn to new position if possible
     * @param currentPosition current pawn position
     * @param newPosition new pawn position
     * @return true if moved, otherwise false
     */
    public boolean move(Position currentPosition, Position newPosition) {
        Pawn pawn = chessboard.getPawn(currentPosition);
        if (pawn != null) {
            Move move = availableMoves(pawn);
            if (move.getAvailablePositions().contains(newPosition) && !move.captureMoves()) {
                chessboard.movePawn(pawn, newPosition);
                promoteToKingIfPossible(pawn);
                switchPlayer();
                return true;
            }
        }
        return false;
    }

    /**
     * Promote to king if pawn moved to opponent edge of the chessboard
     *
     * @return true if promoted, false otherwise
     */
    private boolean promoteToKingIfPossible(Pawn pawn) {
        if (!(pawn instanceof King)) {
            Position pawnPosition = chessboard.getPawnPosition(pawn);
            boolean promote = false;
            if (pawn.getDirection() == Pawn.Direction.TOP) {
                if (pawnPosition.getY() == 0) {
                    promote = true;
                }
            } else {
                if (pawnPosition.getY() == 7) {
                    promote = true;
                }
            }
            if (promote) {
                chessboard.promoteToKing(pawn);
                return true;
            }
        }
        return false;
    }

    /***
     * Jump over enemy if possible
     * @param currentPosition current pawn position
     * @param newPosition new pawn position
     * @return List of available moves or null if jump failed
     */
    public Move jump(Position currentPosition, Position newPosition) {
        Pawn pawn = chessboard.getPawn(currentPosition);
        if (pawn != null) {
            Move move = availableMoves(pawn);
            if (move.captureMoves() && move.getAvailablePositions().contains(newPosition)) {
                int[] vector = getVector(currentPosition, newPosition);
                int enemyX = currentPosition.getX() + vector[0] / 2;
                int enemyY = currentPosition.getY() + vector[1] / 2;

                Position enemyPosition = new Position(enemyX, enemyY);

                chessboard.removePawn(chessboard.getPawn(enemyPosition));
                chessboard.movePawn(pawn, newPosition);

                if (promoteToKingIfPossible(pawn)) {
                    pawn = chessboard.getPawn(newPosition);
                }

                Move nextMove = availableMoves(pawn);

                if (!nextMove.captureMoves()) {
                    switchPlayer();
                }

                return nextMove;
            }
        }
        return null;
    }

    /***
     * calc vector between from and to position
     * @return vector [x, y]
     */
    private int[] getVector(Position from, Position to) {
        int[] vector = new int[2];
        vector[0] = to.getX() - from.getX();
        vector[1] = to.getY() - from.getY();
        return vector;
    }

    public Field[][] getFields() {
        return chessboard.getFields();
    }

    /**
     * Get available moves for current player
     *
     * @return list of moves
     */
    public List<Move> getMoves() {
        List<Pawn> playerPawns = chessboard.getPlayerPawns(player);
        List<Move> playerMoves = playerPawns.stream()
            .map(this::availableMoves)
            .filter(moves -> !moves.getAvailablePositions().isEmpty())
            .collect(Collectors.toList());

        // some pawn must capture
        if (playerMoves.stream().anyMatch(Move::captureMoves)) {
            return playerMoves.stream()
                .filter(Move::captureMoves)
                .collect(Collectors.toList());
        }

        return playerMoves;
    }

    public Player whoseTurn() {
        return player;
    }

}
