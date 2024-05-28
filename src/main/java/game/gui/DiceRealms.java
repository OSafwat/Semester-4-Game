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

    public void setupRed() {
        primaryStage.setScene(sceneController.redScene.getRedScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
