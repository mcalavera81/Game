package poc.domain.fight;

import poc.domain.character.Character;
import poc.domain.character.Opponent;

public interface FightService {
    FightResult fightToDeath(Character character, Opponent opponent);
}
