package game.engine;
import game.collectibles.ElementalCrest;
import game.collectibles.TimeWarp;
import game.engine.enums.*;

public class Player {
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    private ElementalCrest elementalCrest;
    private TimeWarp [] timeWarp;
    Move  allPossiblMoves [];


    public Player(PlayerStatus status){
        this.playerStatus= status;
        //move starting should be inserted here
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
    public TimeWarp [] getTimeWarps(){
        return this.timeWarp;
    }
    public Move [] getAllPossiblMoves(){
        return this.allPossiblMoves;
    }
    

    public void makeMove(Move move){
        move.getCreature().makeMove(move.getDice());
    }
}
