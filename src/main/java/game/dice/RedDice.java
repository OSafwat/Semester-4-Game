package game.dice;

import game.engine.enums.RealmColor;

public class RedDice extends Dice{
    int diceValue;

    public RedDice () {
        super();
    }

    public RedDice (int diceValue) {
        this.diceValue = diceValue;
    }
    public void rollDice(){
        this.diceValue= (int) (Math.random() * 6 +1);
    }
    public int getValue(){
        return this.diceValue;
    }
    public RealmColor getRealm(){
        return RealmColor.RED;
    }


} 
