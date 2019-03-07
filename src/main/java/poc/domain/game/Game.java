package poc.domain.game;

import poc.domain.character.*;
import poc.domain.character.Character;
import poc.domain.exploration.Direction;
import poc.domain.fight.FightResult;
import poc.domain.fight.FightService;
import poc.domain.game.Board.BoardCell;
import poc.utils.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

public class Game {

    public static Game newGame(GameSettings settings) {
        Game game = new Game(settings);
        game.setUpCharacter();
        game.createRandomOpponents();

        return game;
    }

    public boolean moveCharacter(Direction direction) {
        return character.move(direction);
    }

    public Character character(){ return character;}

    public FightResult characterFights() {
        if (!character.isAlive()) {
            return FightResult.outcomeWhenCharacterDead();
        }

        BoardCell cell = board.inspect(character.position());
        if (!cell.hasAnyOpponents()) return FightResult.outcomeWhenNoOpponents();

        Opponent opponent = cell.opponents().stream().findAny().get();
        FightResult fightResult = fightService.fightToDeath(character, opponent);

        if (fightResult.isOpponentDead()) {
            cell.removeDead(opponent);
            opponents.remove(opponent);
        }

        return fightResult;
    }


    public BoardCell characterCellContents() {
        return board.inspect(character.position());
    }

    public int numberOpponents() {
        return opponents.size();
    }

    private Board board;
    private Character character;

    private FightService fightService;
    private GameSettings settings;

    private final UUID id = randomUUID();

    private Set<Opponent> opponents = new HashSet<>();

    private Game(GameSettings settings) {
        this.settings = settings;
        this.fightService = settings.fightService();
        this.board = Board.newBoard();
    }

    public UUID id() {
        return id;
    }


    public Board board() {
        return board;
    }

    private void setUpCharacter() {

        String characterName = settings.playerInput().characterName();

        Assert.requireText(characterName, "Character name cannot be empty");
        switch (settings.playerInput().gameMode()) {
            case EASY:
                this.character = new Character(
                        characterName,
                        board.dimensions(),
                        new Health(30),
                        Weapon.newLethalWeapon(),
                        ArmorClass.newRuggedWeapon(), settings.damageDealer());
                break;
            case HARD:
                this.character = new Character(
                        characterName,
                        board.dimensions(),
                        new Health(20),
                        Weapon.newWeakWeapon(),
                        ArmorClass.newWeakWeapon(), settings.damageDealer());
                break;
             default:
                 throw new RuntimeException("Invalid Game Mode");
        }

    }

    private void createRandomOpponents() {


        IntStream.range(0, board.randomNumberOpponents()).mapToObj(value -> {
            return Opponent.newRandom();
        }).forEach(opponent -> {
            BoardPosition randomLocation = board.randomPosition();
            board.addElement(randomLocation, opponent);
            this.opponents.add(opponent);
        });


    }

    @Override
    public String toString() {

        return format("game(id=%s) with %s opponents. Dimensions: height=%s, width=%s",
                id(),
                numberOpponents(),
                board().dimensions().height(),
                board().dimensions().width());
    }


    public boolean isOver(){
        return !character.isAlive();
    }

    public enum GameMode {
        EASY, HARD;
    }
}
