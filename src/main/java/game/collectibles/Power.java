package game.collectibles;

import game.engine.enums.RewardStates;

public class Power extends Reward {
    RewardStates status;
    public Power(RewardStates status) {
        this.status = status;
    }
}
