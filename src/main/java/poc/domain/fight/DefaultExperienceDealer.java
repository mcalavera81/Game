package poc.domain.fight;

public class DefaultExperienceDealer implements ExperienceDealer {

    @Override
    public int deal(FightResult result) {

        if (result.hasFightHappened() && result.isOpponentDead()){
            return 10;
        }else{
            return 0;
        }

    }

}
