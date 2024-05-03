package game.engine;
import game.creatures.*;
import game.dice.*;
public class Move {
    Creature creature;
    Dice dice;

    public Move(Dice dice, Creature creature){
        this.dice = dice;
        this.creature= creature;
    }

    // public boolean makeMove(Dice dice, Creature creature){
    //     creature.checkMove();
    // }

    Creature getCreature(){
        return creature;
    }

    Dice getDice(){
        return dice;
    }
}
