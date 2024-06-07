package game.gui;

import game.engine.Player;
import game.gui.scenes.*;
import game.gui.scenes.OptionsMenu.BlueConfigScene;
import game.gui.scenes.OptionsMenu.ConfigScene;
import game.gui.scenes.OptionsMenu.GreenConfigScene;
import game.gui.scenes.OptionsMenu.MagentaConfigScene;
import game.gui.scenes.OptionsMenu.OptionsScene;
import game.gui.scenes.OptionsMenu.RedConfigScene;
import game.gui.scenes.OptionsMenu.RoundRewardsConfigScene;
import game.gui.scenes.OptionsMenu.YellowConfigScene;
import game.gui.scenes.OptionsMenu.YellowMultipliersConfigScene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SceneController {
    @SuppressWarnings("exports")
    public RedScene redScene;
    @SuppressWarnings("exports")
    public GreenScene greenScene;
    @SuppressWarnings("exports")
    public BlueScene blueScene;
    @SuppressWarnings("exports")
    public MagentaScene magentaScene;
    @SuppressWarnings("exports")
    public YellowScene yellowScene;
    @SuppressWarnings("exports")
    public BoardScene boardScene;
    @SuppressWarnings("exports")
    public MainMenuScene mainMenuScene;
    @SuppressWarnings("exports")
    public OptionsScene optionsScene;
    @SuppressWarnings("exports")
    public ConfigScene configScene;
    @SuppressWarnings("exports")
    public RoundRewardsConfigScene roundRewardsConfigScene;
    @SuppressWarnings("exports")
    public RedConfigScene redConfigScene;
    @SuppressWarnings("exports")
    public GreenConfigScene greenConfigScene;
    @SuppressWarnings("exports")
    public BlueConfigScene blueConfigScene;
    @SuppressWarnings("exports")
    public MagentaConfigScene magentaConfigScene;
    @SuppressWarnings("exports")
    public YellowConfigScene yellowConfigScene;
    @SuppressWarnings("exports")
    public YellowMultipliersConfigScene yellowMultipliersConfigScene;
    @SuppressWarnings("exports")
    public EndScene endScene;

    public SceneController (@SuppressWarnings("exports") Player p1, @SuppressWarnings("exports") Player p2) {
        redScene = new RedScene();
        greenScene = new GreenScene();
        blueScene = new BlueScene();
        magentaScene = new MagentaScene();
        yellowScene = new YellowScene();
        boardScene = new BoardScene();
        mainMenuScene = new MainMenuScene();
        optionsScene = new OptionsScene();
        configScene = new ConfigScene();
        roundRewardsConfigScene = new RoundRewardsConfigScene();
        redConfigScene = new RedConfigScene();
        greenConfigScene = new GreenConfigScene();
        blueConfigScene = new BlueConfigScene();
        magentaConfigScene = new MagentaConfigScene();
        yellowConfigScene = new YellowConfigScene();
        yellowMultipliersConfigScene = new YellowMultipliersConfigScene();
        endScene = new EndScene(p1, p2);
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

    @SuppressWarnings("exports")
    public Button getOptionsButton() {
        return mainMenuScene.getOptionsButton();
    }

    @SuppressWarnings("exports")
    public Button getStartGameButton() {
        return mainMenuScene.getStartGameButton();
    }

    @SuppressWarnings("exports")
    public Button getPvAIButton() {
        return mainMenuScene.getPvAIButton();
    }

    @SuppressWarnings("exports")
    public Button getPvPButton() {
        return mainMenuScene.getPvPButton();
    }

    @SuppressWarnings("exports")
    public Button getGoBackButton() {
        return mainMenuScene.getGoBackButton();
    }

    @SuppressWarnings("exports")
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
    public ImageView getHydra() {return blueScene.getHydra();}

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

    public void initHydra(int hydraNumber, int aliveHeadCount) {
        String path;
        if (hydraNumber == 0)
            path = null;
        else
            path = "/images/BlueRealmImages/HydraSerpent" + hydraNumber + "/HydraSerpent" + aliveHeadCount + ".png";
        blueScene.changeBlueSceneView(path);
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

    @SuppressWarnings("exports")
    public Button getRollDiceButton() {
        return boardScene.getRollDiceButton();
    }

    public ImageView getTimeWarpButton() {
        return boardScene.getTimeWarp();
    }

    @SuppressWarnings("exports")
    public Button getReturnFromOptionsButton() {
        return optionsScene.getReturnFromOptionsButton();
    }

    @SuppressWarnings("exports")
    public Button getGameConfigurationButton() {
        return optionsScene.getGameConfigButton();
    }

    @SuppressWarnings("exports")
    public Button getRoundRewardsConfigButton() {
        return configScene.getRoundRewardsConfigButton();
    }

    @SuppressWarnings("exports")
    public TextField getNumberOFRoundsField() {
        return configScene.getNumberOFRoundsField();
    }

    @SuppressWarnings("exports")
    public TextField getNumberOfTurnsPerRoundField() {
        return configScene.getNumberOfTurnsPerRoundField();
    }

    @SuppressWarnings("exports")
    public Button getSaveRoundSettingsConfig() {
        return configScene.getSaveRoundSettingsConfig();
    }

    @SuppressWarnings("exports")
    public Button getReturnToOptionsButton() {
        return configScene.getReturnToOptionsButton();
    }

    @SuppressWarnings("exports")
    public Button getRedConfigButton() {
        return configScene.getRedConfigButton();
    }

    @SuppressWarnings("exports")
    public Button getGreenConfigButton() {
        return configScene.getGreenConfigButton();
    }

    @SuppressWarnings("exports")
    public Button getBlueConfigButton() {
        return configScene.getBlueConfigButton();
    }

    @SuppressWarnings("exports")
    public Button getMagentaConfigButton() {
        return configScene.getMagentaConfigButton();
    }

    @SuppressWarnings("exports")
    public Button getYellowConfigButton() {
        return configScene.getYellowConfigButton();
    }

    @SuppressWarnings("exports")
    public Button getYellowMultiplierConfigButton() {
        return configScene.getYellowMultiplierConfigButton();
    }

    // public Button[] getColorsConfigButton() {
    //     Button[] configButtons = {configScene.getRedConfigButton(), configScene.getGreenConfigButton(), configScene.getBlueConfigButton(), configScene.getMagentaConfigButton(),
    //         configScene.getYellowConfigButton(), configScene.getYellowMultiplierConfigButton()};

    //     return configButtons;
    // }

    @SuppressWarnings("exports")
    public Button[] getReturnToConfigSceneButtons() {
        Button[] saveButtons = {redConfigScene.getReturnToConfigSceneButton(), greenConfigScene.getReturnToConfigSceneButton(), blueConfigScene.getReturnToConfigSceneButton(), magentaConfigScene.getReturnToConfigSceneButton(),
            yellowConfigScene.getReturnToConfigSceneButton(), yellowMultipliersConfigScene.getReturnToConfigSceneButton(), roundRewardsConfigScene.getReturnToConfigSceneButton()};

        return saveButtons;
    }

    @SuppressWarnings("exports")
    public Button getExitButtonInEndScene() {
        return endScene.getExitButton();
    }
}
