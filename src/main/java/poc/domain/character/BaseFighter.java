package poc.domain.character;

import poc.domain.fight.DamageDealer;
import poc.domain.fight.Fighter;
import poc.domain.game.GameElement;

import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseFighter implements Fighter, GameElement {


    private Weapon weapon;
    private ArmorClass armorClass;
    private DamageDealer damageDealer;
    private String id;
    private Health health;

    public BaseFighter(Health health,
                        Weapon weapon,
                       ArmorClass armorClass,
                       DamageDealer damageDealer){

        this.id = randomStr();
        this.health = health;
        this.weapon = weapon;
        this.armorClass = armorClass;
        this.damageDealer = damageDealer;
    }



    public void attack(Fighter opponent) {
        if(!opponent.isAlive()) return;

        damageDealer.applyDamage(this, opponent);
    }

    @Override
    public String toString() {
        return "BaseFighter{" +
                "weapon=" + weapon +
                ", armorClass=" + armorClass +
                ", id='" + id + '\'' +
                ", health=" + health +
                '}';
    }

    @Override
    public boolean isAlive() {
        return health.isAlive();
    }

    @Override
    public Weapon weapon() {
        return weapon;
    }

    @Override
    public Health health() {
        return health;
    }

    @Override
    public int experience() {
        return 0;
    }

    @Override
    public ArmorClass armorClass() {
        return armorClass;
    }

    private String randomStr(){
        byte[] array = new byte[16];
        ThreadLocalRandom.current().nextBytes(array);
        return Base64.getUrlEncoder().encodeToString(array);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseFighter that = (BaseFighter) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
