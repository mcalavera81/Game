package poc.domain.game;

import java.util.Map;
import java.util.Set;

public class GameSnapshot {

    public GameSnapshot(Map<BoardPosition, Set<GameElement>> elements, BoardPosition characterPosition) {
        this.elements = elements;
        this.characterPosition = characterPosition;
    }

    private final Map<BoardPosition, Set<GameElement>> elements;
    private final BoardPosition characterPosition;

    public Set<GameElement> elementsAt(BoardPosition position){
        return elements.get(position);
    }

    public BoardPosition characterPosition(){return characterPosition;}

}
