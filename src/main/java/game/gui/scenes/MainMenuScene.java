package game.gui.scenes;

import game.gui.scenes.RedScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuScene{

    Button startGameButton;
    Button optionsButton;

    public Scene createMainScene() {
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
        startGameButton = new Button("Start Game");
        startGameButton.setLayoutX(0);
        startGameButton.setLayoutY(54);
        startGameButton.setPrefHeight(37);
        startGameButton.setPrefWidth(151);
        startGameButton.setStyle("-fx-text-fill: #b0c8b7;");
        startGameButton.setFont(new Font(16));
        startGameButton.getStyleClass().add("main_menu_buttons");

        optionsButton = new Button("Options");
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
    public void displayAlert(){
        Alert thisIsAnAlert = new Alert(AlertType.INFORMATION);
        thisIsAnAlert.setTitle("ScoreSheet");
        thisIsAnAlert.setContentText("hellloooo!");
        thisIsAnAlert.showAndWait();
    }

    public Button getStartGameButton() {
        return startGameButton;
    }

    public Button getOptionsButton() {
        return optionsButton;
    }
}
