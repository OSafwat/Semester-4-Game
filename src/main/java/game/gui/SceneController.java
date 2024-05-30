package game.gui;

import game.gui.scenes.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class SceneController {
    public RedScene redScene;
    public GreenScene greenScene;
    public BlueScene blueScene;
    public MagentaScene magentaScene;
    public YellowScene yellowScene;
    public BoardScene boardScene;
    public MainMenuScene mainMenuScene;

    public SceneController () {
        redScene = new RedScene();
        greenScene = new GreenScene();
        blueScene = new BlueScene();
        magentaScene = new MagentaScene();
        yellowScene = new YellowScene();
        boardScene = new BoardScene();
        mainMenuScene = new MainMenuScene();
    }

    public ImageView getRedDice () {
        return boardScene.getRedDice();
    }

    public ImageView getGreenDice () {
        return boardScene.getGreenDice();
    }

    public ImageView getBlueDice () {
        return boardScene.getBlueDie();
    }

    public ImageView getMagentaDice () {
        return boardScene.getMagentaDice();
    }

    public ImageView getYellowDice () {
        return boardScene.getYellowDice();
    }

    public ImageView getArcaneDice() {
        return boardScene.getArcaneDice();
    }

    public ImageView getPhoenix() {
        return magentaScene.getPhoenix();
    }

    public Button getOptionsButton() {
        return mainMenuScene.getOptionsButton();
    }

    public Button getStartGameButton() {
        return mainMenuScene.getStartGameButton();
    }

    public Button getPvAIButton() {
        return mainMenuScene.getPvAIButton();
    }

    public Button getPvPButton() {
        return mainMenuScene.getPvPButton();
    }

    public Button getGoBackButton() {
        return mainMenuScene.getGoBackButton();
    }

    public Button getExitButton() {
        return mainMenuScene.getExitButton();
    }

    public void switchToMain() {
        mainMenuScene.switchToMain();
    }

    public void switchFromMain() {
        mainMenuScene.switchFromMain();
    }

    public ImageView getDragon1() {
        return redScene.getDragon1();
    }

    public ImageView getDragon2() {
        return redScene.getDragon2();
    }

    public ImageView getDragon3() {
        return redScene.getDragon3();
    }

    public ImageView getDragon4() {
        return redScene.getDragon4();
    }

    public ImageView getRealmGoBackButton() {
        return redScene.getGoBackButton();
    }
}
