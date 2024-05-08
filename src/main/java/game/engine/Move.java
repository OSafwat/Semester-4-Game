package game.engine;
import game.creatures.*;
import game.dice.*;
public class Move implements Comparable{
    Creature creature;
    Dice dice;

    public Move(Dice dice, Creature creature){
        this.dice = dice;
        this.creature= creature;
    }
    public int compareTo(Move move){
        if ( move.getCreature() == this.getCreature() && this.dice.getValue()== move.getDice().getValue() && move.getDice().getRealm() == this.dice.getRealm())  
            return 0;
        else return -1;
    } 
    // public boolean makeMove(Dice dice, Creature creature){
    //     creature.checkMove();
    // }

    Creature getCreature(){
        return creature;
    }
    void setCreature(Creature creature){
        this.creature = creature;
    }
    Dice getDice(){
        return this.dice;
    }
}
