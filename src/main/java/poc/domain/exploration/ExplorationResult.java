package poc.domain.exploration;

import poc.domain.character.Opponent;
import poc.domain.game.Board.BoardCell;
import poc.domain.game.BoardPosition;

import java.util.Set;

public class ExplorationResult {

    private boolean hasMoved;
    private Direction direction;
    private BoardCell boardCell;


    public ExplorationResult(BoardCell boardCell, Direction direction, boolean hasMoved) {
        this.boardCell = boardCell;
        this.direction = direction;
        this.hasMoved = hasMoved;
    }

    public BoardPosition position() {
        return boardCell.position();
    }

    public Set<Opponent> opponents(){
        return boardCell.opponents();
    }

    public boolean hasAnyOpponents(){
        return  boardCell.hasAnyOpponents();
    }
    public Direction direction() {
        return direction;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
