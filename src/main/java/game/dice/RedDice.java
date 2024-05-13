package game.dice;

import game.creatures.Dragon;
import game.engine.enums.RealmColor;

public class RedDice extends Dice{
    int dragonNumber;
    public RedDice(int num){
        super(num);
        dragonNumber = -1;
    }
    public RedDice(int num, int dragonNumber) {
        super(num);
        this.dragonNumber = dragonNumber;
    }
    public RealmColor getRealm(){return RealmColor.RED;}
    public void selectsDragon(int dragonNumber) {
        this.dragonNumber = dragonNumber-1;
    }
    public int getDragonNumber() {return dragonNumber;}
    public RedDice(){
        super();
        dragonNumber = -1;
    }
}
