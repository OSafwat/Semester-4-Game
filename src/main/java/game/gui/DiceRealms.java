package game.gui;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DiceRealms extends Application {
    SceneController sceneController;
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setY(0);
        primaryStage.setX(0);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/wizard hat.png")));
        sceneController = new SceneController();
        primaryStage.setResizable(false);

        primaryStage.setTitle("Dice Realms Game");
        primaryStage.setScene(sceneController.boardScene.makeDiceScene());
        sceneController.getRedDice().setOnMouseClicked(e -> setupRed());
        primaryStage.show();
    }

    public void setupGame() {
        primaryStage.setTitle("Dice Realms Game");
        primaryStage.setScene(sceneController.mainMenuScene.createMainScene());
        sceneController.boardScene.makeDiceScene(getDicePNGs(guiGameController.getAvailableDice()));
        sceneController.redScene.createScene();
        sceneController.greenScene.createScene();
        sceneController.blueScene.createScene();
        sceneController.magentaScene.createScene();
        sceneController.yellowScene.createScene();
        initEventListeners();
        primaryStage.show();
    }

    public String[] getDicePNGs(Dice[] dice) {
        String[] dicePNGs = new String[dice.length];
        for (int i = 0; i < dice.length; i++) {
            dicePNGs[i] = getColorAsString(dice[i]) + " dice";
        }
        for (int i = 0; i < dice.length; i++) {
            dicePNGs[i] += " " + dice[i].getValue() + ".png";
        }
        return dicePNGs;
    }

    public String getColorAsString(Dice dice) {
        StringBuilder colorString = new StringBuilder("/images/Dice/");
        switch (dice.getRealm()) {
            case RED: colorString.append("Red/red"); break;
            case GREEN: colorString.append("Green/green"); break;
            case BLUE: colorString.append("Blue/blue"); break;
            case MAGENTA: colorString.append("Magenta/magenta"); break;
            case YELLOW: colorString.append("Yellow/yellow"); break;
            default: colorString.append("White/white"); break;
        }
        return colorString.toString();
    }

    public void initEventListeners() {
        sceneController.mainMenuScene.getStartGameButton().setOnMouseClicked(e -> setupGameScene()); ;
        sceneController.getRedDice().setOnMouseClicked(e -> setupRealmScene("Red"));
        sceneController.getGreenDice().setOnMouseClicked(e -> setupRealmScene("Green"));
        sceneController.getBlueDice().setOnMouseClicked(e -> setupRealmScene("Blue"));
        sceneController.getMagentaDice().setOnMouseClicked(e -> setupRealmScene("Magenta"));
        sceneController.getYellowDice().setOnMouseClicked(e -> setupRealmScene("Yellow"));
    }

    public void setupGameScene() {
        primaryStage.setScene(sceneController.boardScene.getDiceScene());
    }

    public void setupRealmScene(String realmColor) {
        Scene scene;
        switch (realmColor) {
            case "Red": scene = sceneController.redScene.getScene();break;
            case "Green": scene = sceneController.greenScene.getScene(); break;
            case "Blue": scene = sceneController.blueScene.getScene(); break;
            case "Magenta": scene = sceneController.magentaScene.getScene(); break;
            case "Yellow": scene = sceneController.yellowScene.getScene(); break;
            default: scene = null;
        }
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
