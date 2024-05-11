package game.engine;
import game.creatures.*;
import game.dice.*;
import game.engine.enums.RealmColor;

public class Move implements Comparable{
    Creature creature;
    Dice dice;

    public Move(Dice dice, Creature creature){
        this.dice = dice;
        this.creature= creature;
    }
    public int compareTo(Object temp){
        Move move = (Move)temp;
       return compareTo(move.getDice());
    } 
    public int compareTo(Dice dice){
        if (this.getDice() instanceof RedDice && dice instanceof RedDice) {
            return this.dice.getValue()== dice.getValue() && dice.getRealm() == this.dice.getRealm() && ((RedDice) this.getDice()).getDragonNumber() == ((RedDice) dice).getDragonNumber() ? 0 : -1;
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
        Creature targetCreature = m.getCreature();
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
    public String toString(){
        return getDice().getRealm()+" "+getDice().getValue();
    }
}
