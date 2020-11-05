package record;

import logic.Field;
import logic.Player;

public class Model {

    private final long timestamp;
    private final Field[][] board;
    private final Player player;

    /**
     * @param timestamp time when the move was made
     * @param board board state
     * @param player player who made the move
     */
    Model(long timestamp, Field[][] board, Player player) {
        this.timestamp = timestamp;
        this.board = board;
        this.player = player;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Field[][] getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }
}
