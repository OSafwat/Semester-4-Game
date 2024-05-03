package game.creatures;

import game.dice.Dice;
import game.engine.Move;

public abstract class Creature {
    private int score;
    public abstract int getScore();   //return numerical score value for each realm
    public abstract int getElementalCrest();    //return number of elemental crests for each realm will be 0 or 1 
    public abstract String getScoreSheet();   //return your part from the score sheet as a string while handling its update after each move or change
    public abstract boolean checkMove(Dice dice); 
    public abstract boolean makeMove(Dice dice);
    public abstract Move[] getAllPossibleMoves( Dice dice);
}
