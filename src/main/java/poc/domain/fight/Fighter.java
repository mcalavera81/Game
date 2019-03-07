package poc.domain.fight;

import poc.domain.character.ArmorClass;
import poc.domain.character.Health;
import poc.domain.character.Weapon;

public interface Fighter {

    Weapon weapon();
    Health health();
    int experience();
    ArmorClass armorClass();

    void attack(Fighter opponent);

    boolean isAlive();
}
