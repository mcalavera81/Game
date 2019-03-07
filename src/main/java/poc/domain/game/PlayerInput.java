package poc.domain.game;

import poc.domain.game.Game.GameMode;

public class PlayerInput {

    private String characterName;
    private GameMode gameMode;

    public PlayerInput(String characterName, GameMode gameMode) {
        this.characterName = characterName;
        this.gameMode = gameMode;
    }

    public String characterName() {
        return characterName;
    }

    public GameMode gameMode() { return gameMode;}
}
