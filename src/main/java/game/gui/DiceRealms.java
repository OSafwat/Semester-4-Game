package game.gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DiceRealms extends Application {
    private Scene createMainScene() {
        VBox root = new VBox();
        root.setPrefHeight(400);
        root.setPrefWidth(640);

        // AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        // ImageView
        ImageView imageView = new ImageView();
        imageView.setFitHeight(759);
        imageView.setFitWidth(794);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResourceAsStream("/images/Main Screen.png")));

        // Buttons
        Button startGameButton = new Button("Start Game");
        startGameButton.setLayoutX(0);
        startGameButton.setLayoutY(54);
        startGameButton.setPrefHeight(37);
        startGameButton.setPrefWidth(151);
        startGameButton.setStyle("-fx-text-fill: #b0c8b7;");
        startGameButton.setFont(new Font(16));
        startGameButton.getStyleClass().add("main_menu_buttons");

        Button optionsButton = new Button("Options");
        optionsButton.setLayoutY(92);
        optionsButton.setPrefHeight(37);
        optionsButton.setPrefWidth(151);
        optionsButton.setStyle("-fx-text-fill: #b0c8b7;");
        optionsButton.setFont(new Font(16));
        optionsButton.getStyleClass().add("main_menu_buttons");

        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(-13);
        exitButton.setLayoutY(130);
        exitButton.setPrefHeight(37);
        exitButton.setPrefWidth(151);
        exitButton.setStyle("-fx-text-fill: #b0c8b7;");
        exitButton.setFont(new Font(16));
        exitButton.getStyleClass().add("main_menu_buttons");

        // Add children to AnchorPane
        anchorPane.getChildren().addAll(imageView, startGameButton, optionsButton, exitButton);

        // Scene
        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        return scene;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene mainScene = createMainScene();

        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}
