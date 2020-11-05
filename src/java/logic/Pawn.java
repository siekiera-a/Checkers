package logic;

import java.util.ArrayList;
import java.util.List;

public class Pawn {

    private final Player player;
    private final Direction direction;

    Pawn(Player player, Direction direction) {
        this.player = player;
        this.direction = direction;
    }

    Pawn(Player player) {
        this.player = player;
        direction = Direction.TOP;
    }

    /***
     * Get list of positions where pawn can move
     * @param currentPosition current pawn position
     * @param direction direction in which pawn is moving
     * @return list of position where pawn can move
     */
    List<Position> movePositionsByDirection(Position currentPosition, Direction direction) {
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

    Player getPlayer() {
        return player;
    }

    Direction getDirection() {
        return direction;
    }

    enum Direction {
        TOP, BOTTOM
    }

}
