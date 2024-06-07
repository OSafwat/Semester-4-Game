package game.engine;
import game.creatures.*;
import game.dice.*;

@SuppressWarnings("rawtypes")
public class Move implements Comparable{
    Creature creature;
    Dice dice;

    public Move(Dice dice, Creature creature){
        this.dice = dice;
        this.creature= creature;
    }
    public int compareTo(Object temp){
        Move currMove = (Move)temp;
        Move thisMove = this;
        if (thisMove.getDice().getRealm() != currMove.getDice().getRealm()) {
            return thisMove.getDice().getRealm().compare(currMove.getDice().getRealm());
        }
        else {
            return compareTo(currMove.getDice());
        }
    } 
    public int compareTo(Dice dice){
        if (this.getDice() instanceof RedDice && dice instanceof RedDice) {
            RedDice currDice = (RedDice)dice;
            RedDice thisDice = (RedDice)getDice();
            if (currDice.getDragonNumber() == -1 || thisDice.getDragonNumber() == -1) 
            {
                if (thisDice.getValue() == currDice.getValue() && currDice.getRealm() == thisDice.getRealm())  
                    return 0;
            }
            return thisDice.getValue()== currDice.getValue() && currDice.getRealm() == thisDice.getRealm() && thisDice.getDragonNumber() == currDice.getDragonNumber() ? 0 : -1;
        }
        if (  this.dice.getValue()== dice.getValue() && dice.getRealm() == this.dice.getRealm())  
            return 0;
        else return -1;
    }
    // public boolean makeMove(Dice dice, Creature creature){
    //     creature.checkMove();
    // }


    @Override
    public boolean equals(Object obj) {
        Move m = (Move) obj;
        return m.creature.equals(creature) && m.dice.equals(dice);
    }

    Creature getCreature(){
        return creature;
    }
    void setCreature(Creature creature){
        this.creature = creature;
    }
    public Dice getDice(){
        return this.dice;
    }
    public String toString(){
        return getDice().getRealm()+" "+getDice().getValue();
    }
}
