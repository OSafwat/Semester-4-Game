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
    private Scene makeDiceScene() {
        // Create the AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(766, 495);

        // Main game board image
        ImageView mainBoard = new ImageView(new Image(getClass().getResourceAsStream("/images/Game Board.png"))); // Update the path as necessary
        mainBoard.setFitHeight(495);
        mainBoard.setFitWidth(776);
        mainBoard.setLayoutX(-3);

        // White dice image
        ImageView whiteDice = new ImageView(new Image(getClass().getResourceAsStream("/images/Dice/White/white dice 1.png"))); // Update the path as necessary
        whiteDice.setFitHeight(56);
        whiteDice.setFitWidth(56);
        whiteDice.setLayoutX(355);
        whiteDice.setLayoutY(334);

        // Magenta dice image
        ImageView magentaDice = new ImageView(new Image(getClass().getResourceAsStream("/images/Dice/Magenta/magenta dice 1.png"))); // Update the path as necessary
        magentaDice.setFitHeight(56);
        magentaDice.setFitWidth(56);
        magentaDice.setLayoutX(539);
        magentaDice.setLayoutY(236);

        // Green dice image
        ImageView greenDice = new ImageView(new Image(getClass().getResourceAsStream("/images/Dice/Green/green dice 1.png"))); // Update the path as necessary
        greenDice.setFitHeight(56);
        greenDice.setFitWidth(56);
        greenDice.setLayoutX(253);
        greenDice.setLayoutY(236);

        // Red dice image
        ImageView redDice = new ImageView(new Image(getClass().getResourceAsStream("/images/Dice/Red/red dice 1.png"))); // Update the path as necessary
        redDice.setFitHeight(56);
        redDice.setFitWidth(56);
        redDice.setLayoutX(160);
        redDice.setLayoutY(236);

        //Blue dice image
        ImageView blueDice = new ImageView(new Image(getClass().getResourceAsStream("/images/Dice/Blue/blue dice 1.png"))); // Update the path as necessary
        blueDice.setFitHeight(56);
        blueDice.setFitWidth(56);
        blueDice.setLayoutX(355);
        blueDice.setLayoutY(235);

        // Yellow dice image
        ImageView yellowDice = new ImageView(new Image(getClass().getResourceAsStream("/images/Dice/Yellow/yellow dice 1.png"))); // Update the path as necessary
        yellowDice.setFitHeight(56);
        yellowDice.setFitWidth(56);
        yellowDice.setLayoutX(446);
        yellowDice.setLayoutY(236);

        // Grimoire image (left)
        ImageView leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); // Update the path as necessary
        leftGrimoire.setFitHeight(100);
        leftGrimoire.setFitWidth(100);
        leftGrimoire.setLayoutX(48);
        leftGrimoire.setLayoutY(14);
        //leftGrimoire.setOnMousePressed(event -> executeThis()); // Uncomment and define the method if needed

        // Grimoire image (right)
        ImageView rightGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); // Update the path as necessary
        rightGrimoire.setFitHeight(100);
        rightGrimoire.setFitWidth(100);
        rightGrimoire.setLayoutX(627);
        rightGrimoire.setLayoutY(14);
        rightGrimoire.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
        rightGrimoire.setOnMousePressed(event -> displayAlert()); // Uncomment and define the method if needed
        
        // Alert thisIsAnAlert = new Alert(AlertType.INFORMATION);
        // thisIsAnAlert.setContentText("hellloooo!");

        // Add all ImageView nodes to the AnchorPane
        anchorPane.getChildren().addAll(mainBoard, rightGrimoire,leftGrimoire,whiteDice,magentaDice, greenDice, redDice, yellowDice, blueDice);

        // Create the scene
        Scene scene = new Scene(anchorPane);

        return scene;        
    }
    public void displayAlert(){
        Alert thisIsAnAlert = new Alert(AlertType.INFORMATION);
        thisIsAnAlert.setTitle("ScoreSheet");
        thisIsAnAlert.setContentText("hellloooo!");
        thisIsAnAlert.showAndWait();
    }

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
