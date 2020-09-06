package record;

import logic.Field;
import logic.Player;

public class Model {

    private final long timestamp;
    private final Field[][] board;
    private final Player player;

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
