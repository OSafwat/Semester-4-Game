package game.dice;

import game.engine.enums.RealmColor;

public class RedDice extends Dice{
    int num;
    public void rollDice(){
        this.num= (int) Math.random()*6+1;
    }
    public int getValue(){
        return this.num;
    }
    public RealmColor getRealm(){
        return RealmColor.RED;
    }  
} 
