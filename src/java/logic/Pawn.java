package logic;

import java.util.ArrayList;
import java.util.List;

public class Pawn {

    private final Player player;
    private final Direction direction;

    public Pawn(Player player, Direction direction) {
        this.player = player;
        this.direction = direction;
    }

    protected Pawn(Player player) {
        this.player = player;
        direction = Direction.TOP;
    }

    protected List<Position> movePositionsByDirection(Position currentPosition, Direction direction) {
        List<Position> positions = new ArrayList<>();

        Position leftPos;
        Position rightPos;

        if (direction == Direction.TOP) {
            leftPos = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
            rightPos = new Position(currentPosition.getX() + 1, currentPosition.getY() - 1);
        } else {
            leftPos = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);
            rightPos = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);
        }

        positions.add(leftPos);
        positions.add(rightPos);

        return positions;
    }

    public List<Position> movePositions(Position currentPosition) {
        return movePositionsByDirection(currentPosition, direction);
    }

    public Player getPlayer() {
        return player;
    }

    enum Direction {
        TOP, BOTTOM
    }

}
