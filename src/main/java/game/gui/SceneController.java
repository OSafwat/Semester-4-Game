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

    public ImageView getRedRealmGoBackButton() {
        return redScene.getGoBackButton();
    }

    public ImageView getGreenRealmGoBackButton() {
        return greenScene.getGoBackButton();
    }

    public ImageView getBlueRealmGoBackButton() {
        return blueScene.getGoBackButton();
    }

    public ImageView getMagentaRealmGoBackButton() {
        return magentaScene.getGoBackButton();
    }

    public ImageView getYellowRealmGoBackButton() {
        return yellowScene.getGoBackButton();
    }

    public void initDragons(String[] paths) {
        redScene.initializeDragons(paths);
    }

    public ImageView getFace() {
        return redScene.getDragonFace();
    }
    public ImageView getWings() {
        return redScene.getDragonWings();
    }

    public ImageView getTail() {
        return redScene.getDragonTail();
    }

    public ImageView getHeart() {
        return redScene.getDragonHeart();
    }

    public void closeDragonPartSelectionMenu() {
        redScene.closeDragonPartSelectionMenu();
    }
    public ImageView getLion() {
        return yellowScene.getLion();
    }

    public void initGaiaGuardians(int count) {
        // images/GreenRealmImages/Terra's_Heartland_1.png
        String path = "/images/GreenRealmImages/Terra's_Heartland_" + count + ".png";
        if (count == 0) {
            path = null;
        }
        System.out.println(path);
        greenScene.changeGreenSceneView(path);
    }

    public ImageView getGaiaGuardian() {
        return greenScene.getGuardian();
    }

    public void initLions(int count) {
        String path;
        if (count == 0)
            path = null;
        else
            path = "/images/YellowRealmImages/SolarLions.png";
        yellowScene.changeYellowSceneView(path);
    }

    public void initPhoenix(int count) {
        String path;
        if (count == 0)
            path = null;
        else
            path = "/images/MagentaRealmImages/MajesticPhoenix.png";
        magentaScene.changeMagentaSceneView(path);
    }

    public ImageView getPlayer1TimeWarpButton() {
        return boardScene.getPlayer1TimeWarpButton();
    }

    public ImageView getPlayer1ArcaneBoostButton() {
        return boardScene.getPlayer1ArcaneBoostButton();
    }

    public ImageView getPlayer2TimeWarpButton() {
        return boardScene.getPlayer2TimeWarpButton();
    }

    public ImageView getPlayer2ArcaneBoostButton() {
        return boardScene.getPlayer2ArcaneBoostButton();
    }

    public void changeDragons(String[] paths) {
        redScene.changeRedSceneView(paths);
    }
}
