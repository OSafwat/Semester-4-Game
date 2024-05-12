package game.engine.enums;
import java.util.HashMap;

public enum RealmColor {
        RED, GREEN, BLUE, MAGENTA, YELLOW, WHITE, PARENT;

        public int compare(Object o) {
                return ordinal() - ((RealmColor)o).ordinal();
        }
}
