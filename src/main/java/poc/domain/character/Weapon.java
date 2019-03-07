package poc.domain.character;

import poc.utils.IntUtils;

public class Weapon {

    private static final int MIN_POINTS = 6;
    private static final int MAX_POINTS = 10;


    private int damage;
    private boolean jitter;

    public static Weapon newWithFixedDamage(int damage){
        return new Weapon(damage,false);
    }

    public static Weapon newWithVariableDamage(int damage){
        return new Weapon(damage,true);
    }

    private Weapon(int damage) {
        this(damage,true);
    }

    private Weapon(int damage, boolean jitter) {
        this.damage = damage;
        this.jitter = jitter;
    }

    public int damage() {
        if (jitter) {
            return IntUtils.randomInt(damage - 2, damage + 1);
        } else {
            return damage;
        }

    }

    public static Weapon newRandom(){
        return new Weapon(IntUtils.randomInt(MIN_POINTS, MAX_POINTS));
    }

    public static Weapon newLethalWeapon(){
        return new Weapon(9);
    }

    public static Weapon newWeakWeapon(){
        return new Weapon(6);
    }

    @Override
    public String toString() { return "Weapon{damage=" + damage +'}';}
}
