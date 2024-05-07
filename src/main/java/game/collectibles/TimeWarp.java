package game.collectibles;

import java.sql.Time;

import game.engine.enums.RewardStates;

public class TimeWarp extends Power{
       RewardStates status;
       public TimeWarp(){
              super(RewardStates.UNACQUIRED);
       }
       public TimeWarp(RewardStates status){
              super(status);
       }
}
