package poc.domain.game;

import org.junit.Test;
import poc.domain.fight.DefaultDamageDealer;
import poc.domain.fight.DefaultExperienceDealer;
import poc.domain.fight.DefaultFightService;

public class GameTestShould {


    @Test
    public void create_new_game(){
        PlayerInput input = new PlayerInput("characterName", Game.GameMode.EASY);
        GameSettings settings = new GameSettings(
                input,
                new DefaultDamageDealer(),
                new DefaultFightService(new DefaultExperienceDealer()));
        Game.newGame(settings);


    }



}