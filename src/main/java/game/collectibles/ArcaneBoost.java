package game.collectibles;

import game.engine.enums.RewardStates;

public class ArcaneBoost extends Power{
    public ArcaneBoost(){
        super(RewardStates.UNACQUIRED);
    }
    public ArcaneBoost(RewardStates status){
        super(status);
    }
}
