package game.gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import game.engine.CLIGameController;

public class DiceRealms extends Application {
    SceneController sceneController;
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        Scene mainScene = makeDiceScene();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/wizard hat.png")));

        primaryStage.setResizable(false);

        primaryStage.setTitle("Dice realms Game");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}
