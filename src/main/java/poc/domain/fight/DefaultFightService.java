package poc.domain.fight;

import poc.domain.character.Character;
import poc.domain.character.Opponent;

import java.util.function.BiFunction;

public class DefaultFightService implements FightService {


    public DefaultFightService(ExperienceDealer experienceDealer) {
        this.experienceDealer = experienceDealer;
    }

    private ExperienceDealer experienceDealer;

    public FightResult fightToDeath(Character character, Opponent opponent){
        Fighter attacker = character;
        Fighter defender= opponent;

        while(fightersAlive.apply(character, opponent)){
            attacker.attack(defender);

            attacker = switchRole(attacker, character, opponent);
            defender = switchRole(defender, character, opponent);
        }

        FightResult fightResult = new FightResult(true, opponent, !character.isAlive());
        character.increaseExperience(experienceDealer.deal(fightResult));

        return fightResult;
    }

    private Fighter switchRole(Fighter fighter, Character character, Opponent opponent) {
        return fighter.equals(character)
                ? opponent
                : character;
    }

    BiFunction<Character, Opponent, Boolean> fightersAlive= (c, d)-> c.isAlive() && d.isAlive();

}
