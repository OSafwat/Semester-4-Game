package game.dice;
import game.engine.enums.RealmColor;
public class GreenDice extends Dice {
    public GreenDice(int num){
        super(num);
    }
    public GreenDice(int num, boolean whiteAdded) {
        this(num);
    }
    public RealmColor getRealm(){
        return RealmColor.GREEN;
    }
    public GreenDice(){
        super();
    } 
}

