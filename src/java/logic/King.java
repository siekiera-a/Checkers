package logic;

import java.util.ArrayList;
import java.util.List;

public class King extends Pawn {

    public King(Player player) {
        super(player);
    }

    @Override
    public List<Position> movePositions(Position currentPosition) {
        List<Position> positions = new ArrayList<>();
        positions.addAll(movePositionsByDirection(currentPosition, Direction.TOP));
        positions.addAll(movePositionsByDirection(currentPosition, Direction.BOTTOM));
        return positions;
    }
}
