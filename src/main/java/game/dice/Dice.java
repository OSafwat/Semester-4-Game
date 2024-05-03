package game.dice;

public class Dice {
    private int num;
    public void rollDice(){
        this.num= (int) Math.random()*6+1;
    }

    public void setValue(int num){
        this.num= num;
    }

    public Dice(int num){
        this.num= num;
    }
    public int getValue(){
        return this.num;
    }
        

}
