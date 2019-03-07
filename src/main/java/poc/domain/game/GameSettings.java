package poc.domain.game;

import poc.domain.fight.*;

public class GameSettings {
    public GameSettings(
            PlayerInput playerInput,
            DamageDealer damageDealer, FightService fightService) {
        this.playerInput = playerInput;
        this.damageDealer = damageDealer;
        this.fightService = fightService;
    }

    public static GameSettings newSettings(PlayerInput playerInput){
        return new GameSettings(
                playerInput,
                new DefaultDamageDealer(),
                new DefaultFightService(new DefaultExperienceDealer()));
    }

    private PlayerInput playerInput;

    public PlayerInput playerInput() {
        return playerInput;
    }
    public DamageDealer damageDealer(){return damageDealer;}
    public FightService fightService() { return fightService;}

    private DamageDealer damageDealer;
    private FightService fightService;

}
