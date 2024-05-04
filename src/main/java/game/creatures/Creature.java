package game.creatures;

import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.Move;
import game.exceptions.BonusException;

public abstract class Creature {
    private int score;
    public int getScore(){
        return this.score;  //return numerical score value for each realm
    }  
    public abstract int getElementalCrest();    //return number of elemental crests for each realm will be 0 or 1 
    public abstract String getScoreSheet();   //return your part from the score sheet as a string while handling its update after each move or change
    public abstract boolean checkMove(Dice dice); 
    public abstract boolean makeMove(Dice dice) throws BonusException ;
    public abstract TimeWarp [] getAllTimeWarps();
    public abstract ArcaneBoost [] getAllArcaneBoosts();
    public abstract Move[] getAllPossibleMoves();
}
