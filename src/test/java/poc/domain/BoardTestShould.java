package poc.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import poc.domain.character.Opponent;
import poc.domain.game.Board;
import poc.domain.game.BoardPosition;
import poc.domain.game.GameElement;

import java.util.Set;

public class BoardTestShould {


    private BoardPosition origin;

    @Before
    public void setUp(){
        origin = new BoardPosition(0, 0);

    }

    @Test
    public void create_new_terrain() {
        Board board = Board.newBoard();

        Assertions.assertThat(board).isNotNull();
        Board.BoardCell entry = board.inspect(origin);
        Assertions.assertThat(entry.getElements()).isEmpty();

    }



    @Test
    public void add_new_game_element() {
        Board board = Board.newBoard();

        board.addElement(origin, Opponent.newRandom());
        Board.BoardCell entry = board.inspect(origin);
        Set<GameElement> elements = entry.getElements();
        Assertions.assertThat(elements.toArray()[0]).isExactlyInstanceOf(Opponent.class);
    }

    @Test
    public void remove_game_element() {
        Board board = Board.newBoard();

        Opponent opponentToRemove = Opponent.newRandom();
        Opponent stayingOpponent = Opponent.newRandom();
        board.addElement(origin, opponentToRemove);
        board.addElement(origin, stayingOpponent);
        Board.BoardCell entry = board.inspect(origin);
        Assertions.assertThat(entry.getElements().size()).isEqualTo(2);

        Assertions.assertThat(board.removeElement(origin, opponentToRemove)).isTrue();
        entry = board.inspect(origin);
        Set<GameElement> elements = entry.getElements();
        Assertions.assertThat( ((GameElement)elements.toArray()[0]).id()).isEqualTo(stayingOpponent.id());


    }
}