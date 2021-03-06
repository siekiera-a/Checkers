package logic;

import java.util.List;

public class Move {

    private final Position startPosition;
    private final List<Position> availablePositions;

    public Move(Position startPosition, List<Position> availablePositions) {
        this.startPosition = startPosition;
        this.availablePositions = availablePositions;
    }

    /***
     * check if there are capture moves
     * @return true if capture move are available, otherwise false
     */
    public boolean captureMoves() {
        return availablePositions.stream()
            .anyMatch(pos -> Math.abs(pos.getX() - startPosition.getX()) > 1);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public List<Position> getAvailablePositions() {
        return availablePositions;
    }
}
