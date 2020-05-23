package logic;

import java.util.ArrayList;
import java.util.List;

public class Pawn {

    private final Color color;
    private final Direction direction;

    public Pawn(Color color, Direction direction) {
        this.color = color;
        this.direction = direction;
    }

    protected Pawn(Color color) {
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    enum Color {
        BLACK, WHITE
    }

    enum Direction {
        TOP, BOTTOM
    }

}
