package record;

import java.util.List;

class Model {
    private final boolean gameFinished;
    private final List<MoveData> moves;

    Model(boolean gameFinished, List<MoveData> moves) {
        this.gameFinished = gameFinished;
        this.moves = moves;
    }

    boolean isGameFinished() {
        return gameFinished;
    }

    List<MoveData> getMoves() {
        return moves;
    }
}
