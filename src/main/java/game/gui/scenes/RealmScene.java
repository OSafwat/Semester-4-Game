package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class RealmScene {
    public Scene mainScene;
    public AnchorPane root;

    public void createScene() {};

    public Scene getScene() {
        return mainScene;
    }
}
