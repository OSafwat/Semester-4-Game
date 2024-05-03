package game.dice;

import game.engine.enums.RealmColor;

public class MagentaDice extends Dice{
    int num;
    public RealmColor getRealm(){
        return RealmColor.MAGENTA;
    }
    public void rollDice(){
        this.num= (int) Math.random()*6+1;
    }
    public int getValue(){
        return this.num;
    }
}
