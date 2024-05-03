package game.dice;

public class Dice {
    private int num;
    public void rollDice(){
        this.num= (int) (Math.random() * 6 + 1);
    }
    public int getValue(){
        return this.num;
    }
        

}
