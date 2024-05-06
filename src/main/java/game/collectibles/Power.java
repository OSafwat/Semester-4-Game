package game.collectibles;

import game.engine.enums.RewardStates;

public class Power extends Reward {
    RewardStates status;
    public Power(RewardStates status) {
        this.status = status;
    }
    public RewardStates getStatus() {
        return status;
    }

    public void setStatus(RewardStates status) {
        this.status = status;
    }
}
