package game.gui.scenes;

import java.util.ArrayList;

import game.dice.Dice;
import game.gui.scenes.RedScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    ImageView wizardHat;    // will be used to switch to information menu or to display information popup
    ImageView rightGrimoire;    //will be used to diplay the scoresheets
    ImageView leftGrimoire;
    Label infoLabel;

    public void makeboardScene(String[] dicePNGs) {
        infoLabel = new Label();    //the round information should be here and is set in the DiceRealms class
        infoLabel.getStyleClass().add("infoLabel");

        // Create the AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1920,1080 );

        // Main game board image
        ImageView mainBoard = new ImageView(new Image(getClass().getResourceAsStream("/images/Game Board.png"))); 
        mainBoard.setFitHeight(1080);
        mainBoard.setFitWidth(1920);
        mainBoard.setLayoutX(-3);

        ArrayList<ImageView> imagePaths = new ArrayList<ImageView>();
        ImageView temp ;
        for (String imageString : dicePNGs) {
            temp= new ImageView(new Image(getClass().getResourceAsStream(imageString))); 
            imagePaths.add(temp);
            temp.setFitHeight(150);
            temp.setFitWidth(150);
            if (imageString.contains("red")){
                redDice=temp;
                temp.setLayoutX(408);
                temp.setLayoutY(439);
            }
            else if (imageString.contains("blue")){
                blueDice= temp;
                temp.setLayoutX(884);
                temp.setLayoutY(439);
            }
            else if (imageString.contains("green")){
                greenDice = temp;
                temp.setLayoutX(661);
                temp.setLayoutY(439);
            }
            else if (imageString.contains("magenta")){
                magentaDice = temp;
                temp.setLayoutX(1148);
                temp.setLayoutY(439);
            }
            else if (imageString.contains("yellow")){
                yellowDice= temp;
                temp.setLayoutX(1363);
                temp.setLayoutY(439);
            }
            else if (imageString.contains("white")){
                arcaneDice = temp;
                arcaneDice.setLayoutX(884);
                arcaneDice.setLayoutY(624);
            }
            
        }

        // // Red dice image
        // redDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[0]))); 
        // redDice.setFitHeight(150);
        // redDice.setFitWidth(150);
        // redDice.setLayoutX(408);
        // redDice.setLayoutY(439);

        // // Green dice image
        // greenDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[1])));
        // greenDice.setFitHeight(150);
        // greenDice.setFitWidth(150);
        // greenDice.setLayoutX(661);
        // greenDice.setLayoutY(439);

        // //Blue dice image
        // blueDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[2]))); 
        // blueDice.setFitHeight(150);
        // blueDice.setFitWidth(150);
        // blueDice.setLayoutX(884);
        // blueDice.setLayoutY(439);

        // // Magenta dice image
        // magentaDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[3]))); 
        // magentaDice.setFitHeight(150);
        // magentaDice.setFitWidth(150);
        // magentaDice.setLayoutX(1148);
        // magentaDice.setLayoutY(439);

        // // Yellow dice image
        // yellowDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[4]))); 
        // yellowDice.setFitHeight(150);
        // yellowDice.setFitWidth(150);
        // yellowDice.setLayoutX(1363);
        // yellowDice.setLayoutY(439);

        // // White dice image
        // arcaneDice = new ImageView(new Image(getClass().getResourceAsStream(dicePNGs[5]))); 
        // arcaneDice.setFitHeight(150);
        // arcaneDice.setFitWidth(150);
        // arcaneDice.setLayoutX(884);
        // arcaneDice.setLayoutY(624);

        // Grimoire image (left)
         leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); 
        leftGrimoire.setFitHeight(200);
        leftGrimoire.setFitWidth(200);
        leftGrimoire.setLayoutX(276);
        leftGrimoire.setLayoutY(135);

        // Grimoire image (right)
         rightGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); 
        rightGrimoire.setFitHeight(200);
        rightGrimoire.setFitWidth(200);
        rightGrimoire.setLayoutX(1438);
        rightGrimoire.setLayoutY(135);
        rightGrimoire.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);

        // ImageView for Wizard Hat
        wizardHat = new ImageView(new Image(getClass().getResourceAsStream("/images/wizard hat.png")));
        wizardHat.setFitHeight(200);
        wizardHat.setFitWidth(200);
        wizardHat.setLayoutX(834);
        wizardHat.setLayoutY(14);

        // Add all ImageView nodes to the AnchorPane
        anchorPane.getChildren().addAll(mainBoard, rightGrimoire,leftGrimoire, wizardHat, infoLabel);
        for (ImageView diceImage : imagePaths) {
            anchorPane.getChildren().addAll(diceImage);
        }

        // Create the scene
        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
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

    public Scene getBoardScene(int currentRound, int currentTurn, String playerName) {
        infoLabel.setText("The current round is: "+currentRound+"       The current Active player is: "+playerName+"        The current turn number is: "+currentTurn);
        return boardScene;
    }
}
