package game.creatures;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.Move;
import java.util.ArrayList;
import game.exceptions.BonusException;
import game.exceptions.BonusTwoException;
import game.exceptions.InvalidMoveException;

public abstract class Creature {
    public int score;
    public ArrayList<TimeWarp> timeWarps;
    public ArrayList<ArcaneBoost> arcaneBoosts;
    public int getScore(){
        return this.score;  //return numerical score value for each realm
    }  
    public abstract int getElementalCrest();    //return number of elemental crests for each realm will be 0 or 1 
    public abstract String getScoreSheet();   //return your part from the score sheet as a string while handling its update after each move or change
    public abstract boolean checkMove(Dice dice) throws InvalidMoveException; 
    public abstract boolean makeMove(Dice dice) throws BonusException  , BonusTwoException, InvalidMoveException ;  // the bonusTwoException was added for the gaia class otherwise you wont need it so please just throw it on your side 
    public abstract ArrayList<TimeWarp> getAllTimeWarps();
    public abstract ArrayList<ArcaneBoost> getAllArcaneBoosts();
    public abstract Move[] getAllPossibleMoves();
}
