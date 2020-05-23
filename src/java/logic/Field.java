package logic;

public class Field {

    private final State state;
    private final boolean canMove;

    public Field(State state, boolean canMove) {
        this.state = state;
        this.canMove = canMove;
    }

    public State getState() {
        return state;
    }

    public boolean canMove() {
        return canMove;
    }

    enum State {
        EMPTY, BLACK_PAWN, WHITE_PAWN, BLACK_KING, WHITE_KING
    }
}
