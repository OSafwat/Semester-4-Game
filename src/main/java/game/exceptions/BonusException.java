package game.exceptions;

import game.engine.enums.RealmColor;

public class BonusException extends Exception{
    RealmColor color1;
    RealmColor color2;
    public BonusException(RealmColor color) {
        color1 = color;
        color2 = RealmColor.PARENT;
    }
    public BonusException(RealmColor color1, RealmColor color2) {this.color1 = color1; this.color2 = color2;}
    public RealmColor getRealmColor1(){
        return color1;
    }

    public RealmColor getRealmColor2(){
        return color2;
    }
}
