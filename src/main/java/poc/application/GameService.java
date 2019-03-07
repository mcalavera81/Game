package poc.application;

import poc.domain.exploration.Direction;
import poc.domain.exploration.ExplorationResult;
import poc.domain.fight.FightResult;
import poc.domain.game.Game;
import poc.domain.game.Game.GameMode;

import java.util.UUID;

public interface GameService {

    Game newGame(String characterName, GameMode mode);
    ExplorationResult characterExplores(UUID gameId, Direction direction);
    FightResult characterFights(UUID gameId);


}
