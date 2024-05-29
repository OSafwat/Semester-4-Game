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
    ImageView background;

    Button startGameButton;
    Button optionsButton;
    Button exitButton;

    Button pvp;
    Button pvAI;
    Button goBack;

    AnchorPane root;

    public Scene createMainScene() {
        

        // AnchorPane
        root = new AnchorPane();
        root.setPrefSize(1920, 1080);
        background = new ImageView(new Image(getClass().getResourceAsStream("/images/Main menu.png")));
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

        pvp = new Button("Player VS Player");
            pvp.setLayoutX(758);
            pvp.setLayoutY(355);
            pvp.getStyleClass().add("start-game");
            pvp.getStyleClass().add("");
    
        pvAI = new Button("Player VS AI (work in progress)");
            pvAI.setLayoutX(806);
            pvAI.setLayoutY(496);
            pvAI.getStyleClass().add("options");

        goBack = new Button("return");
            goBack.setLayoutX(877);
            goBack.setLayoutY(650);
            //exitButton.getStyleClass().add("");



        // Add children to AnchorPane
        root.getChildren().addAll(background, startGameButton, optionsButton, exitButton);

        // Scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        return scene;
    }
    
    public void switchToMain(){
        pvp = new Button();
        root.getChildren().clear();
        root.getChildren().addAll(background,pvp, pvAI,goBack);
    }
    public void switchFromMain(){
        pvp = new Button();
        root.getChildren().clear();
        root.getChildren().addAll(background, startGameButton, optionsButton, exitButton);
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
    public Button getPvp(){
        return pvp;
    }
    public Button getPvAI(){
        return pvAI;
    }
    public Button getGoBack(){
        return goBack;
    }
}
