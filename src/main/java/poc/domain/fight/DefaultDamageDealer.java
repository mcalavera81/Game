package poc.domain.fight;


public class DefaultDamageDealer implements DamageDealer {

    @Override
    public void applyDamage(Fighter attacker, Fighter defender) {

        int damage= Math.min(defender.health().healthPoints(),
                Math.max(0, attacker.weapon().damage() - defender.armorClass().points()));

        defender.health().setHealthPoints(defender.health().healthPoints()-damage);

    }
}