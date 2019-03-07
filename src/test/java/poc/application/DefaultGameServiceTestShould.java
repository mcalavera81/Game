package poc.application;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import poc.domain.character.Character;
import poc.domain.character.Opponent;
import poc.domain.exploration.Direction;
import poc.domain.exploration.ExplorationResult;
import poc.domain.fight.FightResult;
import poc.domain.game.BoardDimensions;
import poc.domain.game.BoardPosition;
import poc.domain.game.Game;
import poc.domain.game.GameRepository;
import poc.infrastructure.DefaultGameRepository;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;


public class DefaultGameServiceTestShould {


    private GameService gameService;

    @Before
    public void setUp(){
        GameRepository gameRepo = new DefaultGameRepository();
        gameService = new DefaultGameService(gameRepo);
    }

    @Test
    public void create_game_including_opponents_and_main_character(){
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);
        assertThat(game).isNotNull();
        BoardPosition characterPosition = game.characterCellContents().position();
        assertThat(characterPosition).isEqualTo(new BoardPosition(0,0));
    }

    @Test
    public void allow_character_to_explore_north_south(){
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);

        ExplorationResult exploration =null;

        exploration = gameService.characterExplores(game.id(), Direction.NORTH);
        assertExploration(exploration, new BoardPosition(1, 0), Direction.NORTH, true);

        exploration = gameService.characterExplores(game.id(), Direction.SOUTH);
        assertExploration(exploration, new BoardPosition(0, 0), Direction.SOUTH, true);

    }

    @Test
    public void allow_character_to_explore_east_west(){
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);

        ExplorationResult exploration =null;
        exploration = gameService.characterExplores(game.id(), Direction.EAST);
        assertExploration(exploration, new BoardPosition(0,1), Direction.EAST, true);


        exploration = gameService.characterExplores(game.id(), Direction.WEST);
        assertExploration(exploration, new BoardPosition(0,0), Direction.WEST, true);

    }

    @Test
    public void allow_character_to_explore_west_all_the_way(){
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);


        ExplorationResult exploration =null;

        Direction east = Direction.EAST;

        exploration = moveAllTheWay(game, east);

        BoardPosition characterPosition = exploration.position();
        BoardDimensions boardDims = game.board().dimensions();

        assertThat(characterPosition.x()).isEqualTo(boardDims.minX());
        assertThat(characterPosition.y()).isEqualTo(boardDims.maxY());

    }

    @Test
    public void allow_character_to_explore_north_all_the_way(){
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);


        ExplorationResult exploration =null;
        Direction north = Direction.NORTH;

        exploration = moveAllTheWay(game, north);

        BoardPosition characterPosition = exploration.position();
        BoardDimensions boardDims = game.board().dimensions();


        assertThat(characterPosition.x()).isEqualTo(boardDims.maxX());
        assertThat(characterPosition.y()).isEqualTo(boardDims.minY());

    }


    @Test
    public void allow_character_to_explore_north_east_all_the_way(){
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);


        ExplorationResult exploration =null;
        Direction north = Direction.NORTH;

        exploration = moveAllTheWay(game, north);

        Direction east = Direction.EAST;
        exploration = moveAllTheWay(game, east);


        BoardPosition characterPosition = exploration.position();
        BoardDimensions boardDims = game.board().dimensions();

        assertThat(characterPosition.x()).isEqualTo(boardDims.maxX());
        assertThat(characterPosition.y()).isEqualTo(boardDims.maxY());

    }


    @Test
    public void allow_character_to_find_all_opponents() {
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);

        ExplorationResult exploration =null;
        Direction direction = Direction.NORTH;


        AtomicInteger opponentsCounter= new AtomicInteger(0);
        do{
            exploration = moveAllTheWay(game, direction, explorationResult -> {
                Set<Opponent> opponents = explorationResult.opponents();
                opponentsCounter.addAndGet(opponents.size());
            });
            exploration = gameService.characterExplores(game.id(), Direction.EAST);

            direction = direction==Direction.NORTH?Direction.SOUTH:Direction.NORTH;
        }while(exploration.hasMoved());


        assertThat(opponentsCounter.get()).isEqualTo(game.numberOpponents());

    }

    @Test
    public void allow_character_to_fight_opponents() {
        Game game = gameService.newGame("CharacterName", Game.GameMode.EASY);
        System.out.println(game.numberOpponents());
        ExplorationResult exploration = findOpponent(game);
        Assertions.assertThat(exploration.hasAnyOpponents()).isTrue();

        FightResult fightResult = gameService.characterFights(game.id());

        if(fightResult.isCharacterDead()){
            Assertions.assertThat(fightResult.opponent().isAlive()).isTrue();
            System.out.println("Winner: opponent");
        }else{
            Assertions.assertThat(fightResult.isCharacterDead()).isFalse();
            System.out.println("Winner: character");
            Character character = game.character();

            Assertions.assertThat(character.experience()).isEqualTo(10);

        }
        System.out.println(game.numberOpponents());


    }

    private ExplorationResult findOpponent(Game game) {
        ExplorationResult exploration=gameService.characterExplores(game.id(),Direction.STAY_PUT);
        Direction direction = Direction.NORTH;

        while(!exploration.hasAnyOpponents()){
            exploration = gameService.characterExplores(game.id(), direction);
            if(! exploration.hasAnyOpponents() && !exploration.hasMoved()){
                exploration = gameService.characterExplores(game.id(), Direction.EAST);
                direction = direction==Direction.NORTH?Direction.SOUTH:Direction.NORTH;
            }
        }
        return exploration;
    }

    private ExplorationResult moveAllTheWay(Game game, Direction direction) {
        return moveAllTheWay(game, direction, null);
    }


    private ExplorationResult moveAllTheWay(Game game, Direction direction, Consumer<ExplorationResult> consumer) {
        ExplorationResult exploration=gameService.characterExplores(game.id(),Direction.STAY_PUT);
        boolean hasMoved =true;
        while(hasMoved){
            if (consumer!=null) consumer.accept(exploration);
            exploration = gameService.characterExplores(game.id(), direction);
            hasMoved = exploration.hasMoved();
        }
        return exploration;
    }

    private void assertExploration(ExplorationResult exploration, BoardPosition expected, Direction north, boolean hasMoved) {
        assertThat(exploration.hasMoved()).isEqualTo(hasMoved);
        assertThat(exploration.direction()).isEqualTo(north);
        assertThat(exploration.position()).isEqualTo(expected);
    }
}