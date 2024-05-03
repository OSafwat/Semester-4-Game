package game.dice;

import game.engine.enums.RealmColor;

public class RedDice extends Dice{

    public RedDice () {
        super();
    }
    public RedDice (int diceValue) {
        super(diceValue);
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
