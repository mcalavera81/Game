package poc.domain.fight;

import poc.domain.character.Opponent;

public class FightResult {

    private  boolean fightHappened;
    private Opponent opponent;
    private boolean characterDead;


    public FightResult(boolean fightHappened, Opponent opponent, boolean characterDead) {
        this.fightHappened = fightHappened;
        this.opponent = opponent;
        this.characterDead = characterDead;
    }

    public static FightResult outcomeWhenCharacterDead(){
        return new FightResult(false, null, true);
    }

    public static FightResult outcomeWhenNoOpponents(){
        return new FightResult(false, null, false);
    }

    boolean hasFightHappened(){
        return fightHappened;
    }

    public boolean fightHappened() {
        return fightHappened;
    }

    public Opponent opponent() {
        return opponent;
    }

    public boolean isCharacterDead() {
        return characterDead;
    }

    public boolean isOpponentDead() {
        return !characterDead;
    }

}
