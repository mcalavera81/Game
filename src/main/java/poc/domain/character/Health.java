package poc.domain.character;

import poc.utils.Assert;

import static poc.utils.IntUtils.randomInt;

public class Health {

    private static final int MIN_POINTS = 5;
    private static final int MAX_POINTS = 20;

    private int healthPoints;


    public Health(int healthPoints) {
        Assert.requiresTrue(healthPoints>=0,"Health Points must be a positive number");
        this.healthPoints = healthPoints;
    }

    public int healthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        Assert.requiresTrue(healthPoints>=0,"Health Points must be a positive number");
        this.healthPoints = healthPoints;
    }

    public boolean isAlive(){
        return healthPoints > 0;
    }

    public static Health newRandom(){
        return new Health(randomInt(MIN_POINTS, MAX_POINTS));
    }


    @Override
    public String toString() {
        return "Health{healthPoints=" + healthPoints + '}';
    }
}
