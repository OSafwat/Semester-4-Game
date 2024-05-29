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
    Button exitButton;

    public Scene createMainScene() {
        

        // AnchorPane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1920, 1080);
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/images/Main menu.png")));
        background.setFitWidth(1920);
        background.setFitHeight(1080);
        background.getStyleClass().add("root");
        AnchorPane.setTopAnchor(background, -6.0);
        //background.setImage());


        // Create the buttons
        startGameButton = new Button("Start Game");
        startGameButton.setLayoutX(758);
        startGameButton.setLayoutY(355);
        startGameButton.getStyleClass().add("start-game");
        startGameButton.getStyleClass().add("rainbow");

        optionsButton = new Button("Options");
        optionsButton.setLayoutX(806);
        optionsButton.setLayoutY(496);
        optionsButton.getStyleClass().add("options");

        exitButton = new Button("Exit");
        exitButton.setLayoutX(877);
        exitButton.setLayoutY(650);
        exitButton.getStyleClass().add("exit");





        // Add children to AnchorPane
        root.getChildren().addAll(background, startGameButton, optionsButton, exitButton);

        // Scene
        Scene scene = new Scene(root);
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
    public Button getExiButton(){
        return exitButton;
    }
}
