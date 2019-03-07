package poc.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import poc.domain.character.*;
import poc.domain.character.Character;
import poc.domain.fight.DefaultDamageDealer;
import poc.domain.fight.Fighter;
import poc.domain.game.BoardDimensions;

public class DefaultDamageDealerTestShould {


    private DefaultDamageDealer dd;
    private BoardDimensions dimensions;

    @Before
    public void setUp(){
        dd = new DefaultDamageDealer();
        dimensions = new BoardDimensions(BoardDimensions.MIN_HEIGHT, BoardDimensions.MIN_WIDTH);
    }

    @Test
    public void not_deal_damage_when_armor_class_meets_attack(){

        //Given
        int initialHP = 10;
        int points = 5;

        Character character = new Character("name",dimensions , new Health(initialHP), Weapon.newWithFixedDamage(points), ArmorClass.newWithFixedPoints(points), dd);
        Opponent opponent = new Opponent(new Health(initialHP), Weapon.newWithFixedDamage(points),ArmorClass.newWithFixedPoints(points),dd);

        assertHealthAfterAttack(initialHP, character, opponent);

    }


    @Test
    public void not_deal_damage_when_armor_class_beats_attack(){

        //Given
        int initialHP = 10;
        int damage = 5;
        int armorPoints = 6;


        Character character = new Character("name", dimensions, new Health(initialHP), Weapon.newWithFixedDamage(damage), ArmorClass.newWithFixedPoints(armorPoints), dd);
        Opponent opponent = new Opponent(new Health(initialHP), Weapon.newWithFixedDamage(damage),ArmorClass.newWithFixedPoints(armorPoints),dd);

        assertHealthAfterAttack(initialHP, character, opponent);


    }

    @Test
    public void deal_damage_when_attack_beats_armor_class(){

        //Given
        int initialHP = 10;
        int strengthPoints = 7;
        int armorPoints = 5;


        Character character = new Character("name",dimensions , new Health(initialHP), Weapon.newWithFixedDamage(strengthPoints), ArmorClass.newWithFixedPoints(armorPoints), dd);
        Opponent opponent = new Opponent(new Health(initialHP), Weapon.newWithFixedDamage(strengthPoints), ArmorClass.newWithFixedPoints(armorPoints),dd);


        assertHealthAfterAttack(initialHP-(strengthPoints-armorPoints), character, opponent);


    }

    @Test
    public void deal_max_damage_equals_to_health_points(){

        //Given
        int initialHP = 10;
        int damagePoints = 16;
        int armorPoints = 5;


        Character character = new Character("name",dimensions , new Health(initialHP), Weapon.newWithFixedDamage(damagePoints), ArmorClass.newWithFixedPoints(armorPoints), dd);
        Opponent opponent = new Opponent(new Health(initialHP), Weapon.newWithFixedDamage(damagePoints), ArmorClass.newWithFixedPoints(armorPoints),dd);

        assertHealthAfterAttack(0, character, opponent);

        Assertions.assertThat(opponent.health().isAlive()).isFalse();
    }



    private void assertHealthAfterAttack(int finalHealthPoints, Fighter attacker, Fighter defender) {
        //When
        dd.applyDamage(attacker, defender);

        //Then
        Assertions.assertThat(defender.health().healthPoints()).isEqualTo(finalHealthPoints);
    }

}