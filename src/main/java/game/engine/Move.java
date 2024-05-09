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
    public int compareTo(Object temp){
        Dice dice = (Dice) temp;
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
    Dice getDice(){
        return this.dice;
    }
}
