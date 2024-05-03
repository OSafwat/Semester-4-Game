package game.dice;

import game.engine.enums.RealmColor;

public class RedDice extends Dice{
    public RedDice(int num){
        super(num);
    }
    public RealmColor getRealm(){
        return RealmColor.RED;
    } 
    public RedDice(){
        super();
    }  
} 
