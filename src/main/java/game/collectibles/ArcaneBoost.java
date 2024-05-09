package game.collectibles;

import game.engine.enums.RewardStates;

public class ArcaneBoost extends Power{
    public ArcaneBoost(RewardStates status){
        super(status);
    }
    
    public ArcaneBoost(){
        super(RewardStates.UNACQUIRED);
 }
}
