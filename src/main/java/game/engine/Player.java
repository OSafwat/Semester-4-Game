package game.engine;
import game.collectibles.ElementalCrest;
import game.collectibles.TimeWarp;
import game.engine.enums.*;

public class Player {
    PlayerStatus playerStatus;
    GameScore gamescore;
    ScoreSheet scoresheet;
    ElementalCrest elementalCrest;
    TimeWarp timeWarp;

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

}
