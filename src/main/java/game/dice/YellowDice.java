package game.dice;

import game.engine.enums.RealmColor;

public class YellowDice extends Dice{
    public YellowDice(int num){
        super(num);
    }
    @Override
    public RealmColor getRealm(){
        return RealmColor.YELLOW;
    }
    public YellowDice(){
        super();
    } 

}
