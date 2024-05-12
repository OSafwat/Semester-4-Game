package game.engine;
import java.util.ArrayList;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.enums.*;

public class Player {
    String name;
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    //private ElementalCrest elementalCrest;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    Move[] allPossibleMoves ;
    ArrayList<Dice> playedDice;


    public void setName(String name){
        this.name= name;
    }
    public Player(PlayerStatus status){
        this.scoreSheet= new ScoreSheet();
        this.playerStatus= status;
        this.arcaneBoosts=scoreSheet.getAllArcaneBoosts();
        this.timeWarps=scoreSheet.getAllTimeWarps();
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
        gameScore.updateScores(this.scoreSheet.getScores(),this.scoreSheet.getElementalCrests() );
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



}
