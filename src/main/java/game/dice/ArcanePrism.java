package game.dice;

public class ArcanePrism extends Dice{
    int num;
    public void rollDice(){
        this.num= (int) Math.random()*6+1;
    }
    public int getValue(){
        return this.num;
    }
}
