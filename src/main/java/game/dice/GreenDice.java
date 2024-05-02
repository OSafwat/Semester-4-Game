package game.dice;

import game.engine.enums.RealmColor;

public class GreenDice extends Dice {
    int num;
    public RealmColor getRealm(){
        return RealmColor.GREEN;
    }
    public void rollDice(){
        this.num= (int) Math.random()*6+1;
    }
    public int getValue(){
        return this.num;
    }
}
