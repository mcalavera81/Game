package poc.domain.character;

import poc.utils.IntUtils;

public class ArmorClass {
    private static final int MIN_POINTS = 1;
    private static final int MAX_POINTS = 5;


    private int points;
    private boolean jitter;

    public static ArmorClass newWithFixedPoints(int points){
        return new ArmorClass(points,false);
    }

    public static ArmorClass newWithVariablePoints(int points){
        return new ArmorClass (points,true);
    }

    private ArmorClass(int points) {
        this(points,true);
    }

    private ArmorClass(int points, boolean jitter) {
        this.points = points;
        this.jitter = jitter;
    }

    public int points() {
        if (jitter) {
            return IntUtils.randomInt(points - 3, points + 1);
        } else {
            return points;
        }

    }

    public static ArmorClass newRandom(){
        return new ArmorClass(IntUtils.randomInt(MIN_POINTS, MAX_POINTS));
    }

    public static ArmorClass newRuggedWeapon(){
        return new ArmorClass(4);
    }

    public static ArmorClass newWeakWeapon(){
        return new ArmorClass(2);
    }

    @Override
    public String toString() {
        return "ArmorClass{points=" + points + '}';
    }
}
