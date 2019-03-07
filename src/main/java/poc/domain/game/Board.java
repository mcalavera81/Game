package poc.domain.game;

import poc.domain.character.Opponent;
import poc.utils.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final BoardDimensions dimensions;
    private BoardCell[][] matrix;


    public static Board newBoard() {
        int height = ThreadLocalRandom.current().nextInt(BoardDimensions.MIN_HEIGHT, BoardDimensions.MAX_HEIGHT+1);
        int width = ThreadLocalRandom.current().nextInt(BoardDimensions.MIN_WIDTH, BoardDimensions.MAX_WIDTH +1);
        return newBoard(new BoardDimensions(height, width));
    }
    public static Board newBoard(BoardDimensions dimensions){

        BoardCell[][] board = IntStream.range(0, dimensions.height())
                .mapToObj(x -> IntStream.range(0, dimensions.width())
                        .mapToObj(y -> new BoardCell(x, y))
                        .toArray(BoardCell[]::new))
                .toArray(BoardCell[][]::new);


        return new Board(board, dimensions);
    }

    public BoardPosition randomPosition() {
        return  dimensions.randomPosition();
    }

    private Board(BoardCell[][] matrix, BoardDimensions dimensions) {
        this.dimensions = dimensions;
        this.matrix = matrix;
    }


    public BoardCell inspect(BoardPosition boardPosition){
        dimensions.assertPosition(boardPosition);

        return matrix[boardPosition.x()][boardPosition.y()];
    }

    public BoardCell addElement(BoardPosition boardPosition, GameElement element){
        dimensions.assertPosition(boardPosition);

        BoardCell boardCell = matrix[boardPosition.x()][boardPosition.y()];
        boardCell.getElements().add(element);

        return new BoardCell(boardPosition, new HashSet<>(boardCell.elements));

    }

    public boolean removeElement(BoardPosition boardPosition, GameElement element){
        dimensions.assertPosition(boardPosition);

        BoardCell boardCell = matrix[boardPosition.x()][boardPosition.y()];
        Set<GameElement> elements = boardCell.getElements();
        return elements.remove(element);

    }

    public int cells() {
        return dimensions.cells();
    }

    public BoardDimensions dimensions(){ return dimensions;}

    private static float MIN_PERCENTAGE_OPPONENTS = 0.2f;

    private static float MAX_PERCENTAGE_OPPONENTS = 0.6f;

    public int randomNumberOpponents() {
        int totalCells = this.cells();
        return ThreadLocalRandom.current()
                .nextInt(Math.round(MIN_PERCENTAGE_OPPONENTS * totalCells),
                        Math.round(MAX_PERCENTAGE_OPPONENTS * totalCells));
    }




    public static class BoardCell {

        private BoardPosition position;
        private Set<GameElement> elements;
        public BoardCell(int x, int y){
            this.position = new BoardPosition(x,y);
            this.elements = new HashSet<>();
        }
        public BoardCell(BoardPosition position, Set<GameElement> elements){
            this.position = position;
            this.elements = elements;
        }
        public Set<GameElement> getElements() {
            return elements;
        }

        public Set<Opponent> opponents(){
            return getElements()
                    .stream()
                    .filter(GameElement::isOpponent)
                    .map(i->(Opponent)i)
                    .collect(Collectors.toSet());
        }

        public boolean hasAnyOpponents(){
            return getElements()
                    .stream()
                    .anyMatch(GameElement::isOpponent);
        }
        public BoardPosition position() { return position;}

        public boolean removeDead(Opponent opponent) {
            Assert.requireNotNull(opponent, "Dead opponent not provided");
            Assert.requiresTrue(!opponent.isAlive(), "Only dead opponents can be removed");
            return elements.remove(opponent);
        }

    }
}
