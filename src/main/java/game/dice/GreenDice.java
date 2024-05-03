package game.dice;
import game.engine.enums.RealmColor;
public class GreenDice extends Dice {
    public GreenDice(int num){
        super(num);
    }
    public RealmColor getRealm(){
        return RealmColor.GREEN;
    }
}

