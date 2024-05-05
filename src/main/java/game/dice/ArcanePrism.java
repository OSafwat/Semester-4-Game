package game.dice;

import game.engine.enums.RealmColor;

public class ArcanePrism extends Dice{
    public ArcanePrism(int num){
        super(num);
    }
    public RealmColor getRealm(){
        return RealmColor.WHITE;
    }
    public ArcanePrism(){
        super();
    } 

}
