package game.engine;
import java.util.ArrayList;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.enums.*;

public class Player implements Cloneable {
    private String name;
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    private Move[] allPossibleMoves;
    private ArrayList<Dice> playedDice;
    private ArrayList<Dice> usedArcaneDice;


    public void setName(String name){
        this.name= name;
    }
    public Player(PlayerStatus status){
        this.scoreSheet= new ScoreSheet();
        this.playerStatus= status;
        this.arcaneBoosts=scoreSheet.getAllArcaneBoosts();
        this.timeWarps=scoreSheet.getAllTimeWarps();
        this.usedArcaneDice = new ArrayList<>();
        allPossibleMoves = getAllPossibleMoves();
        gameScore = new GameScore();
        playedDice = new ArrayList<>();
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

    public String getName(){
        return this.name;
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


    //ai
    @Override
    public Player clone() {
        try {
            return (Player) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // cant happen
        }
    }
}
