package game.exceptions;

import game.engine.enums.RealmColor;

public class BonusException extends Exception{
    RealmColor color;
    public BonusException(RealmColor color) {
        this.color = color;
    }
    public RealmColor getRealmColor(){
        return this.color;
    }
    
}
