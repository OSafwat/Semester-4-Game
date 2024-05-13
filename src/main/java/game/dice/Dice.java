package game.dice;

import game.engine.enums.RealmColor;

public class Dice implements Comparable{
    private int num;
    public void rollDice(){
        this.num= (int) (Math.random() * 6 + 1);
    }

    public int compareTo(Object dice){
        Dice die = (Dice) dice;
        if (die.getValue()== this.getValue() && die.getRealm() == this.getRealm())
            return 0;
        else if (die.getRealm() != getRealm())
        {
            return getRealm().compareTo(die.getRealm());
        }
        else
            return getValue() - die.getValue();
    }

    public void setValue(int num){
        this.num= num;
    }

    public Dice(int num){
        this.num= num;
    }
    public Dice(){
        rollDice();
    }
    public int getValue(){
        return this.num;
    }

    /*
     * the following is a dummy enumeration as i needed to place a get realm in the scoresheet class to dice generally
     * however would get a compile error if i called it on the dice class when it doesnt have a getRealm method so i added this
     * also didnt place a random color even though it should change during runtime polymorphism so we dont have a hard life during
     * any debugging or error fixations
     */
    public RealmColor getRealm(){
        return RealmColor.PARENT;
    }

    public String toString() {
        return getRealm() + " " + getValue();
    }

}
