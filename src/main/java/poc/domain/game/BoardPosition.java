package poc.domain.game;

import poc.domain.exploration.Direction;
import poc.utils.Assert;

import java.util.Objects;

public class BoardPosition {
    private int x;
    private int y;

    public static BoardPosition newOrigin() {
        return new BoardPosition(0, 0);
    }

    public BoardPosition(int x, int y) {
        Assert.requiresTrue(x >= 0, "x coordinate must be >=0");
        Assert.requiresTrue(y >= 0, "y coordinate must be >=0");
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public BoardPosition create(Direction direction, BoardDimensions dims) {

        BoardPosition newBoardPosition = null;

        switch (direction) {
            case SOUTH:
                newBoardPosition = new BoardPosition(Math.max(dims.minX(), x - 1), y);
                break;
            case NORTH:
                newBoardPosition = new BoardPosition(Math.min(dims.maxX(), x + 1), y);
                break;
            case WEST:
                newBoardPosition = new BoardPosition(x, Math.max(dims.minY(), y - 1));
                break;
            case EAST:
                newBoardPosition = new BoardPosition(x, Math.min(dims.maxY(), y + 1));
                break;
            case STAY_PUT:
                newBoardPosition = this;
                break;
            default:
                throw new RuntimeException("Invalid direction");
        }
        return newBoardPosition;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardPosition that = (BoardPosition) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "BoardPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
