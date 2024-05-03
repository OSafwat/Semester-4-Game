package game.dice;

import game.engine.enums.RealmColor;

public class BlueDice extends Dice{
    public BlueDice(int num){
        super(num);
    }
    public RealmColor getRealm(){
        return RealmColor.BLUE;
    }
    public BlueDice(){
        super();
    } 
}
