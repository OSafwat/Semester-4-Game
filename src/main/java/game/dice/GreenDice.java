package game.dice;
import game.engine.enums.RealmColor;
public class GreenDice extends Dice {
    private int realValue;
    private int whiteDiceVal;
    public void setRealValue(int num){
        this.realValue= num;
    }
    public void setWhiteval(int whiteVal){
        whiteDiceVal = whiteVal;
    }
    public int getRealValue(){
        return this.realValue;
    }
    public GreenDice(int num){
        super(num);
    }
    public RealmColor getRealm(){
        return RealmColor.GREEN;
    }
    public GreenDice(){
        super();
    } 
}

