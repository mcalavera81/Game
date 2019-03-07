package poc.domain.game;

import poc.utils.Assert;

import java.util.concurrent.ThreadLocalRandom;

public class BoardDimensions {

    public final static int MIN_WIDTH=4;
    public final static int MIN_HEIGHT=4;
    public final static int MAX_WIDTH=8;
    public final static int MAX_HEIGHT=8;

    private final int height;
    private final int width;


    public BoardDimensions(int height, int width) {
        Assert.requiresTrue(height > 0, "height must be > 0");
        Assert.requiresTrue(width > 0, "width must be > 0");
        this.height = height;
        this.width = width;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }
    public int cells() {
        return width*height;
    }

    public int minX(){return 0;}
    public int maxX(){return height-1;}

    public int minY(){return 0;}
    public int maxY(){return width-1;}

    public BoardPosition randomPosition() {

        int row = ThreadLocalRandom.current().nextInt(0, height);
        int column = ThreadLocalRandom.current().nextInt(0, width);
        return new BoardPosition(row, column);
    }


    public void assertPosition(BoardPosition boardPosition) {
        if(boardPosition ==null || (boardPosition.x() > height || boardPosition.y() >= width)) throw new IllegalArgumentException("Invalid position");
    }
}
