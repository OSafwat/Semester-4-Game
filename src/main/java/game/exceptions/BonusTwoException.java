package game.exceptions;

import game.engine.enums.RealmColor;


public class BonusTwoException {
    RealmColor color1;
    RealmColor color2;
    public BonusTwoException(RealmColor color1, RealmColor color2) {
        this.color1 =color1;
        this.color2 =color2;
    }

    public RealmColor [] getBothRealmColors(){
        RealmColor [] temp = new RealmColor[2];
        temp[0]=this.color1 ;
        temp[2]=this.color2;
        return temp;
    }
}
