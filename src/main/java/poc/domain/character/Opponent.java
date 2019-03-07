package poc.domain.character;

import poc.domain.fight.DamageDealer;
import poc.domain.fight.DefaultDamageDealer;

public class Opponent extends BaseFighter {

    @Override
    public boolean isOpponent() {
        return true;
    }

    public Opponent(Health health,
                    Weapon weapon,
                    ArmorClass armorClass,
                    DamageDealer damageDealer){

        super(health, weapon, armorClass, damageDealer);

    }



    public static  Opponent newRandom(){
        return new Opponent(Health.newRandom(),Weapon.newRandom(),ArmorClass.newRandom(), new DefaultDamageDealer());
    }
}
