package poc.domain.character;

import poc.domain.exploration.Direction;
import poc.domain.fight.DamageDealer;
import poc.domain.game.BoardDimensions;
import poc.domain.game.BoardPosition;
import poc.utils.Assert;

public class Character extends BaseFighter {


    private String name;
    private int experiencePoints;
    private BoardPosition position;
    private BoardDimensions dimensions;

    public String name() {
        return name;
    }

    public void increaseExperience(int increase){
        Assert.requiresTrue(increase >= 0, "Experience can only increase");
        this.experiencePoints = experiencePoints + increase;
    }

    public int experience() {
        return experiencePoints;
    }

    public Character(String name,
                     BoardDimensions dimensions,
                     Health health,
                     Weapon weapon,
                     ArmorClass armorClass,
                     DamageDealer damageDealer) {
        super(health, weapon, armorClass, damageDealer);

        Assert.requireText(name, "Character name missing");
        this.name = name;
        this.dimensions = dimensions;
        this.experiencePoints = 0;
        this.position = BoardPosition.newOrigin();
    }

    public boolean move(Direction direction) {
        BoardPosition newPosition = position.create(direction, dimensions);
        boolean hasMoved = !newPosition.equals(position);
        position = newPosition;
        return hasMoved;
    }

    public BoardPosition position() {
        return position;
    }

    @Override
    public boolean isOpponent() {
        return false;
    }


}
