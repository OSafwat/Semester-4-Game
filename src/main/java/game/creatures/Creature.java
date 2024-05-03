package game.creatures;

public abstract class Creature {
    public abstract int getScore();   //return numerical score value for each realm
    public abstract int getElementalCrest();    //return number of elemental crests for each realm will be 0 or 1 
    public abstract String getScoreSheet();   //return your part from the score sheet as a string while handling its update after each move or change
     


}
