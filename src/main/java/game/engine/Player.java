package game.engine;
import java.util.ArrayList;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.creatures.Hydra;
import game.engine.enums.*;

public class Player {
    String name;
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    //private ElementalCrest elementalCrest;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    ArrayList <Move> allPossiblMoves ;



    public Player(PlayerStatus status, String name){
        this.playerStatus= status;
        this.name= name;
        this.arcaneBoosts=scoreSheet.getAllArcaneBoosts();
        this.timeWarps=scoreSheet.getAllTimeWarps();
        allPossiblMoves = getAllPossibleMoves();
    }

    public ArrayList<Move> getAllPossibleMoves(){
        ArrayList<Move> allMoves= new ArrayList<>();
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.RED).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.GREEN).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.BLUE).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.MAGENTA).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.YELLOW).getAllPossibleMoves());
        return allMoves;
    }

    public String getName(){
        return this.name;   
    }

    public PlayerStatus getPlayerStatus(){
        return this.playerStatus;
    }
    public ScoreSheet getScoresheet(){
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

    public Move [] getAllPossiblMoves(){
        return (Move[])this.allPossiblMoves.toArray();
    }
    


}
