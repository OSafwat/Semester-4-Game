package game.engine;
import game.collectibles.ElementalCrest;
import game.collectibles.TimeWarp;
import game.engine.enums.*;

public class Player {
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    private ElementalCrest elementalCrest;
    private TimeWarp timeWarp;

    public Player(PlayerStatus status){
        this.playerStatus= status;
    }
    public PlayerStatus getPlayerStatus(){
        return this.playerStatus;
    }
    public void changeStatus(){
        if (this.playerStatus == PlayerStatus.ACTIVE)
            this.playerStatus = PlayerStatus.PASSIVE;
        else 
            this.playerStatus = PlayerStatus.ACTIVE; 
    }
    public void updateGameScore(){

        gameScore.updateScores(this.scoreSheet.getScores(), );
    }

    
}
