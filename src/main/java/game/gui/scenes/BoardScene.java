package game.gui.scenes;

import game.dice.Dice;
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

public class BoardScene{

    Scene diceScene;
    ImageView redDice;
    ImageView greenDice;
    ImageView blueDice;
    ImageView magentaDice;
    ImageView yellowDice;
    ImageView arcaneDice;
    public void makeDiceScene(String[] dicePNGs) {
        // Create the AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(766, 495);

        // Main game board image
        ImageView mainBoard = new ImageView(new Image(getClass().getResourceAsStream("/images/Game Board.png"))); // Update the path as necessary
        mainBoard.setFitHeight(495);
        mainBoard.setFitWidth(776);
        mainBoard.setLayoutX(-3);

        // Red dice image
        redDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[0]))); // Update the path as necessary
        redDice.setFitHeight(56);
        redDice.setFitWidth(56);
        redDice.setLayoutX(160);
        redDice.setLayoutY(236);

        // Green dice image
        greenDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[1]))); // Update the path as necessary
        greenDice.setFitHeight(56);
        greenDice.setFitWidth(56);
        greenDice.setLayoutX(253);
        greenDice.setLayoutY(236);

        //Blue dice image
        blueDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[2]))); // Update the path as necessary
        blueDice.setFitHeight(56);
        blueDice.setFitWidth(56);
        blueDice.setLayoutX(355);
        blueDice.setLayoutY(235);

        // Magenta dice image
        magentaDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[3]))); // Update the path as necessary
        magentaDice.setFitHeight(56);
        magentaDice.setFitWidth(56);
        magentaDice.setLayoutX(539);
        magentaDice.setLayoutY(236);

        // Yellow dice image
        yellowDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[4]))); // Update the path as necessary
        yellowDice.setFitHeight(56);
        yellowDice.setFitWidth(56);
        yellowDice.setLayoutX(446);
        yellowDice.setLayoutY(236);

        // White dice image
        arcaneDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[5]))); // Update the path as necessary
        arcaneDice.setFitHeight(56);
        arcaneDice.setFitWidth(56);
        arcaneDice.setLayoutX(355);
        arcaneDice.setLayoutY(334);

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
        anchorPane.getChildren().addAll(mainBoard, rightGrimoire,leftGrimoire,arcaneDice,magentaDice, greenDice, redDice, yellowDice, blueDice);

        // Create the scene
        Scene scene = new Scene(anchorPane);
        diceScene = scene;
    }
    public void displayAlert(){
        Alert thisIsAnAlert = new Alert(AlertType.INFORMATION);
        thisIsAnAlert.setTitle("ScoreSheet");
        thisIsAnAlert.setContentText("hellloooo!");
        thisIsAnAlert.showAndWait();
    }

    public ImageView getRedDice() {
        return redDice;
    }
    public ImageView getGreenDice() {
        return greenDice;
    }
    public ImageView getBlueDie() {
        return blueDice;
    }
    public ImageView getMagentaDice() {
        return magentaDice;
    }
    public ImageView getYellowDice() {
        return yellowDice;
    }
    
    public ImageView getArcaneDice() {
        return arcaneDice;
    }
    public Scene getDiceScene() {
        return diceScene;
    }
}
