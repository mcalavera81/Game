package poc.application;

import poc.domain.exploration.Direction;
import poc.domain.exploration.ExplorationResult;
import poc.domain.fight.DefaultDamageDealer;
import poc.domain.fight.DefaultExperienceDealer;
import poc.domain.fight.DefaultFightService;
import poc.domain.fight.FightResult;
import poc.domain.game.Game;
import poc.domain.game.Game.GameMode;
import poc.domain.game.GameRepository;
import poc.domain.game.GameSettings;
import poc.domain.game.PlayerInput;

import java.util.UUID;

public class DefaultGameService implements GameService {

    private GameRepository gameRepo;

    public DefaultGameService(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    @Override
    public Game newGame(String characterName, GameMode mode) {
        PlayerInput input = new PlayerInput(characterName, mode);
        GameSettings settings = new GameSettings(
                input,
                new DefaultDamageDealer(),
                new DefaultFightService(new DefaultExperienceDealer()));
        Game game = Game.newGame(settings);
        gameRepo.save(game);
        return game;
    }

    @Override
    public ExplorationResult characterExplores(UUID gameId, Direction direction) {
        Game game = gameRepo.findOne(gameId);
        boolean hasMoved = game.moveCharacter(direction);
        gameRepo.save(game);
        return new ExplorationResult(game.characterCellContents(), direction, hasMoved);
    }

    @Override
    public FightResult characterFights(UUID gameId) {
        Game game = gameRepo.findOne(gameId);
        FightResult fightResult = game.characterFights();
        gameRepo.save(game);
        return fightResult;
    }

}
