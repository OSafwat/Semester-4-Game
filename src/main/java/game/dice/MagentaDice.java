package game.dice;

import game.engine.enums.RealmColor;

public class MagentaDice extends Dice{
    public MagentaDice(int num){
        super(num);
    }
    public RealmColor getRealm(){
        return RealmColor.MAGENTA;
    }
}
