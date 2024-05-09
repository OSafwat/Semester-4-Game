package game.dice;

import game.creatures.Dragon;
import game.engine.enums.RealmColor;

public class RedDice extends Dice{
    int dragonNumber;
    public RedDice(int num){
        super(num);
    }
    public RealmColor getRealm(){return RealmColor.RED;}
    public void selectsDragon(int dragonNumber) {
        this.dragonNumber = dragonNumber;
    }
    public int getDragonNumber() {return dragonNumber;}
    public RedDice(){
        super();
    }
}
