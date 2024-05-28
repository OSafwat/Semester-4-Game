package game.gui;

import game.gui.scenes.*;
import javafx.scene.image.ImageView;

public class SceneController {
    public RedScene redScene;
    public GreenScene greenScene;
    BlueScene blueScene;
    MagentaScene magentaScene;
    YellowScene yellowScene;
    public BoardScene boardScene;

    public SceneController () {
        redScene = new RedScene();
        greenScene = new GreenScene();
        blueScene = new BlueScene();
        magentaScene = new MagentaScene();
        yellowScene = new YellowScene();
        boardScene = new BoardScene();
    }

    public ImageView getRedDice () {
        return boardScene.getRedDice();
    }
}
