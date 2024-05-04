package game.dice;
import game.engine.enums.RealmColor;
public class GreenDice extends Dice {
    int realValue;
    public void setRealValue(int num){
        this.realValue= num;
    }
    public GreenDice(int realvalue){
        this.realValue=realvalue;
    }
    public RealmColor getRealm(){
        return RealmColor.GREEN;
    }
    public GreenDice(){
        super();
    } 
}

