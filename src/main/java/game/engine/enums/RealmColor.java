package game.engine.enums;
import java.util.HashMap;

public enum RealmColor {
        WHITE, RED,GREEN,BLUE, MAGENTA, YELLOW, PARENT;
        
        public int compare(Object o) {
                HashMap<RealmColor, Integer> map = new HashMap<>();
                RealmColor[] values = RealmColor.values();
                for (int i = 1; i < 6; i++) {
                        map.put(values[i], i);
                }
                RealmColor thisRealmColor = this;
                RealmColor currRealmColor = (RealmColor)o;
                return map.get(thisRealmColor) - map.get(currRealmColor);
        }
}
