package ui;

import logic.Field;
import logic.GameController;
import logic.Move;
import logic.Player;
import logic.Position;
import record.Recorder;

import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PlayerController {

    private int fieldWidth;
    private int fieldHeight;
    private GameController game;
    private Position lastClickedPawnPosition;
    private List<Move> moves;
    private Move obligatoryMove;
    private Recorder recorder;

    public PlayerController(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        game = GameController.startNewGame(Player.WHITE);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy [HH.mm.ss]");
        recorder = new Recorder(Path.of(String.format("checkers-%s.json", format.format(new Date()))));

        recorder.capture(game.getFields(), game.whoseTurn());
    }

    /**
     * Get active move
     *
     * @return active move or null if there is no active move
     */
    public Move getActive() {
        if (lastClickedPawnPosition == null) {
            return null;
        }

        if (obligatoryMove != null) {
            return obligatoryMove;
        }

        return moves.stream()
            .filter(move -> move.getStartPosition().equals(lastClickedPawnPosition))
            .findFirst()
            .orElse(null);
    }

    public Player whoseTurn() {
        return game.whoseTurn();
    }

    /**
     * @return list of available moves
     */
    public List<Move> getAvailableMoves() {
        if (obligatoryMove != null) {
            List<Move> list = new ArrayList<>();
            list.add(obligatoryMove);
            return list;
        }

        return game.getMoves();
    }

    public boolean gameEnded() {
        return getAvailableMoves().isEmpty();
    }

    public void handleClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Position pos = getPositionOnBoard(x, y);

        moves = game.getMoves();

        moves.stream()
            .filter(move -> move.getStartPosition().equals(pos))
            .findFirst()
            .ifPresentOrElse(
                move -> lastClickedPawnPosition = move.getStartPosition(),
                () -> handleAction(pos));
    }

    /**
     * Move or jump pawn
     */
    private void handleAction(Position position) {
        moves.stream()
            .filter(move -> move.getStartPosition().equals(lastClickedPawnPosition))
            .findFirst()
            .ifPresent(move -> {
                if (move.getAvailablePositions().contains(position)) {
                    Player currentPlayer = game.whoseTurn();
                    if (move.captureMoves()) {
                        Move nextMove = game.jump(lastClickedPawnPosition, position);
                        if (nextMove.captureMoves()) {
                            obligatoryMove = nextMove;
                            lastClickedPawnPosition = position;
                        } else {
                            obligatoryMove = null;
                            lastClickedPawnPosition = null;
                        }
                    } else {
                        game.move(lastClickedPawnPosition, position);
                        obligatoryMove = null;
                        lastClickedPawnPosition = null;
                    }
                    recorder.capture(game.getFields(), currentPlayer);
                    recorder.save();
                }
            });
    }

    /**
     * get chessboard position
     *
     * @return calculated position
     */
    private Position getPositionOnBoard(int x, int y) {
        int row = y / fieldHeight;
        int column = x / fieldWidth;
        return new Position(column, row);
    }

    public Field[][] getFields() {
        return game.getFields();
    }

}
