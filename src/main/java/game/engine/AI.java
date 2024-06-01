package game.engine;

import java.util.ArrayList;
import java.util.List;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.enums.RealmColor;

/*IDEAS:-
 *  - EVALUATION:-
 *      - make calculated guesses for all the parameters then fuck around with them to optimize them
 *      - make a threshold for using a time warp and an arcane boost thats negatively linear (high at the start 0 at the end)
 *      - time warp threshold parameter should be guessed by getting the average best sum of score of every single turn
 *      of every round and if the best sum is lower than it then you use it
 *      - arcane boost threshold parametr should be the average best score and if the best score is higher then use it
 *      - do a dfs sum to get the score of every turn and then pick the highest score and on the last turn maximize
 *      the difference in the scores not just your own score (this naturally includes a few stratgeies like prioritizing
 *      making more moves and having more options) and dont forget to calculate the board after you use any bonus you may get
 *      - add a dynamic score for every time warp and arcane boost you acquire depending on the turn and round youre in (for 
 *      time warps they should be negatively linear and for arcane boosts they should be linear)
 *      - score system for every realm as if it was just the score they provide the red realm would never be attacked which isnt ideal
 *      = for red it'll be diff parameters depending on how close the move is from completing a column and which column is it and
 *      how many dragons have been killed so far so that the highest scoring column has the highest score and they should sum up
 *      to the score of the column (no need to check for rows as that should be included with the arcane boost/time warp/bonuses
 *      part of the evaluation so dont do it and double calculate basically) if it's the first time we're attacking the column it
 *      should be like a parameter that you get from experimentation with the average score of attacking the other realms for the
 *      first time (maybe make it slightly higher as it's important to get scores from all realms and red is the hardest one for
 *      that and other scores grow way quicker like blue and green) 
 *      = for green and blue it's the difference between the original score and the new one (so that you dont get 56 or smth
 *      and inflate the whole evaluation)
 *      = for magenta it should be diff scores depending on the current dice and the one before it so maybe have a hashmap
 *      of int and int for both of the dice values and have diff parameters that again you just kinda calculate for all 36
 *      combinations so that we prioritize choosing dice closer to the last one like 1 then 2 and not like 2 then 5
 *      = for yellow just return the score
 *      - for all of them reward getting an elemental crest (like for yellow add a number that you keep incrementing the closer
 *      you get to the elemental crest) and after you get an elemental crest from a realm make that counter negative and 
 *      try not to make moves there
 *      - for all of them add a score if you get an arcane boost and time warp and if it's the very last turn then make
 *      the score for the timewarp 0 and make the score for the time warp decrease slightly as you progress bc your
 *      options become more limited
 *      - calculate the average score of an elemental crest at the end of lots of simulated games and make that a final score of 
 *      any elemental crest you get as youre going through the game instead of calculating it as you're going through the game
 *      to prioritize getting elemtnal crests (DONT FORGET TO REMOVE THE ELEMENTAL CREST CALCULATION FROM THE EVALUATION THING 
 *      SO YOU DONT DOUBLE CALCULATE)
 *      
 * 
 * 
 * 
 * 
 *  - GENERATING MOVES:-
 *      - will alpha beta be useful given that the number of moves isnt that high?
 *      - try to do a depth 6 and calculate how many operations that is and try to test how long it takes
 *      and if it takes a while then implement alpha beta and test again
 *      - make a new makemove method bc you need a diff implementation when you get a bonus
        - when you get a bonus choose 6 for lion/magenta and it shouldn't matter for blue 
        and for green and red try all options and pick the best one (MAKE SURE YOU'RE NOT ADDING ALL THE POSSIBLE SCORES
        TO YOUR SCORE AND JUST THE BEST MOVE)
        - 
 *      
 * 
 * 
 * 
 *         - make the ai take input the default config file and use it as such and then later on change it to take any configs
 * 
 */

public class AI extends Player implements Cloneable {
    
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    private Move[] allPossibleMoves;
    private ArrayList<Dice> playedDice;
    private ArrayList<Dice> usedArcaneDice;

    public AI(PlayerStatus status){
        super(status);
    }
    
    public Move[] getAllPossibleMoves(){
        ArrayList<Move> allMoves= new ArrayList<>();
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.RED).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.GREEN).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.BLUE).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.MAGENTA).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.YELLOW).getAllPossibleMoves());
        Move[] res = new Move[allMoves.size()];
        for (int i = 0; i < allMoves.size(); i++) {
            res[i] = allMoves.get(i);
        }
        allPossibleMoves = res;
        return res;
    }

    public void selectDice (Dice dice) {
        playedDice.add(dice);
    }

    public ArrayList<Dice> getPlayedDice () {
        ArrayList<Dice> playedDice = new ArrayList<>(this.playedDice);
        this.playedDice.clear();
        return playedDice;
    }

    public PlayerStatus getPlayerStatus(){
        return this.playerStatus;
    }
    public ScoreSheet getScoreSheet(){
        return this.scoreSheet;
    }
    public void switchStatus(){
        if (this.playerStatus == PlayerStatus.ACTIVE)
            this.playerStatus = PlayerStatus.PASSIVE;
        else
            this.playerStatus = PlayerStatus.ACTIVE;
    }
    public void updateGameScore(){
        gameScore.updateScores(this.scoreSheet.getScores(),this.scoreSheet.getElementalCrests());
    }
    public GameScore getGameScore(){
        return this.gameScore;
    }
    public ArrayList<TimeWarp> getTimeWarps(){
        return this.timeWarps;
    }
    public ArrayList<ArcaneBoost> getArcaneBoosts(){
        return this.arcaneBoosts;
    }
    public void updateAllPossibleMoves(){
        this.allPossibleMoves = getAllPossibleMoves();
    }

    public ArrayList<Dice> getUsedArcaneDice() {
        return usedArcaneDice;
    }
    public void resetUsedArcaneDice() {
        usedArcaneDice.clear();
    }
    public void addToUsedArcaneDice(Dice die) {
        usedArcaneDice.add(die);
    }




    //actual ai stuff

    public Move decideNextMove(Move[] moves) {//2nd lowest move

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (Move move : moves) {
            int score = evaluateMove(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    int evaluateMove(Move move) {
        int score=this.getGameScore().getTotalScore();





        return score;
    }
}
