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

    Scene boardScene;
    ImageView redDice;
    ImageView greenDice;
    ImageView blueDice;
    ImageView magentaDice;
    ImageView yellowDice;
    ImageView arcaneDice;
    public void makeboardScene(String[] dicePNGs) {
        // Create the AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1920,1080 );

        // Main game board image
        ImageView mainBoard = new ImageView(new Image(getClass().getResourceAsStream("/images/Game Board.png"))); // Update the path as necessary
        mainBoard.setFitHeight(1080);
        mainBoard.setFitWidth(1920);
        mainBoard.setLayoutX(-3);

        // Red dice image
        redDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[0]))); // Update the path as necessary
        redDice.setFitHeight(150);
        redDice.setFitWidth(150);
        redDice.setLayoutX(408);
        redDice.setLayoutY(439);

        // Green dice image
        greenDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[1]))); // Update the path as necessary
        greenDice.setFitHeight(150);
        greenDice.setFitWidth(150);
        greenDice.setLayoutX(661);
        greenDice.setLayoutY(439);

        //Blue dice image
        blueDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[2]))); // Update the path as necessary
        blueDice.setFitHeight(150);
        blueDice.setFitWidth(150);
        blueDice.setLayoutX(884);
        blueDice.setLayoutY(439);

        // Magenta dice image
        magentaDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[3]))); // Update the path as necessary
        magentaDice.setFitHeight(150);
        magentaDice.setFitWidth(150);
        magentaDice.setLayoutX(1148);
        magentaDice.setLayoutY(439);

        // Yellow dice image
        yellowDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[4]))); // Update the path as necessary
        yellowDice.setFitHeight(150);
        yellowDice.setFitWidth(150);
        yellowDice.setLayoutX(1363);
        yellowDice.setLayoutY(439);

        // White dice image
        arcaneDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[5]))); // Update the path as necessary
        arcaneDice.setFitHeight(150);
        arcaneDice.setFitWidth(150);
        arcaneDice.setLayoutX(884);
        arcaneDice.setLayoutY(624);

        // Grimoire image (left)
        ImageView leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); // Update the path as necessary
        leftGrimoire.setFitHeight(200);
        leftGrimoire.setFitWidth(200);
        leftGrimoire.setLayoutX(276);
        leftGrimoire.setLayoutY(135);
        //leftGrimoire.setOnMousePressed(event -> executeThis()); // Uncomment and define the method if needed

        // Grimoire image (right)
        ImageView rightGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); // Update the path as necessary
        rightGrimoire.setFitHeight(200);
        rightGrimoire.setFitWidth(200);
        rightGrimoire.setLayoutX(1438);
        rightGrimoire.setLayoutY(135);
        rightGrimoire.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
        rightGrimoire.setOnMousePressed(event -> displayAlert()); // Uncomment and define the method if needed

        // Alert thisIsAnAlert = new Alert(AlertType.INFORMATION);
        // thisIsAnAlert.setContentText("hellloooo!");

        // Add all ImageView nodes to the AnchorPane
        anchorPane.getChildren().addAll(mainBoard, rightGrimoire,leftGrimoire,arcaneDice,magentaDice, greenDice, redDice, yellowDice, blueDice);

        // Create the scene
        Scene scene = new Scene(anchorPane);
        boardScene = scene;
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
    public Scene getBoardScene() {
        return boardScene;
    }
}
