package game.dice;
import game.engine.enums.RealmColor;
import game.creatures.*;
import game.creatures.ExtraClasses.Guardians;
public class GreenDice extends Dice {
  int num;
  ArcanePrism whitedie;
    public RealmColor getRealm(){
        return RealmColor.GREEN;
    }
    public void rollDice(){
        this.num= (int) Math.random()*6+1;
    }
    public int getValue(){
        int c = whitedie.getValue();
        return this.num+c;
  

    }

}

