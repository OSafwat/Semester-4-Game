package game.gui.scenes;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;


public class BoardScene{

    Scene boardScene;
    ImageView redDice;
    ImageView greenDice;
    ImageView blueDice;
    ImageView magentaDice;
    ImageView yellowDice;
    ImageView arcaneDice;
    ImageView wizardHat = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/wizard hat.png"))));
        // will be used to switch to information menu or to display information popup
    ImageView leftGrimoire = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/blue grimoire.png"))));
    ImageView player1TimeWarpButton;
    ImageView player1ArcaneBoostButton;
    ImageView player2TimeWarpButton;
    ImageView player2ArcaneBoostButton;
    Button rollDice;
    ImageView timeWarp;
    Label infoLabel;
    public AnchorPane root;

    public ImageView getPlayer1TimeWarpButton() {
        return player1TimeWarpButton;
    }

    public ImageView getPlayer1ArcaneBoostButton() {
        return player1ArcaneBoostButton;
    }

    public ImageView getPlayer2TimeWarpButton() {
        return player2TimeWarpButton;
    }

    public ImageView getPlayer2ArcaneBoostButton() {
        return player2ArcaneBoostButton;
    }
    public ImageView getInfoButton(){
        return wizardHat;
    }

    public void makeboardScene(String[] dicePNGs) {
        infoLabel = new Label();    //the round information should be here and is set in the DiceRealms class
        infoLabel.getStyleClass().add("infoLabel");

        // Create the AnchorPane
        root = new AnchorPane();
        root.setPrefSize(1920,1080 );

        // Main game board image
        ImageView mainBoard = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Game Board Pixelated3.png"))));
        mainBoard.setFitHeight(1080);
        mainBoard.setFitWidth(1920);
        //mainBoard.setLayoutX(-3);

        ArrayList<ImageView> imagePaths = new ArrayList<>();
        for (String imageString : dicePNGs) {
            ImageView temp;
            boolean strikeThrough = imageString.contains(".png123");
            if (strikeThrough)
                temp = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageString.substring(0, imageString.length() - 3)))));
            else
                temp = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageString))));
            imagePaths.add(temp);
            temp.setFitHeight(150);
            temp.setFitWidth(150);
            if (imageString.toLowerCase().contains("red")){
                redDice=temp;
                temp.setLayoutX(713);
                temp.setLayoutY(338);
            }
            else if (imageString.toLowerCase().contains("blue")){
                blueDice= temp;
                temp.setLayoutX(1129);
                temp.setLayoutY(603);
            }
            else if (imageString.toLowerCase().contains("green")){
                greenDice = temp;
                temp.setLayoutX(898);
                temp.setLayoutY(263);
            }
            else if (imageString.toLowerCase().contains("magenta")){
                magentaDice = temp;
                temp.setLayoutX(1095);
                temp.setLayoutY(338);
            }
            else if (imageString.toLowerCase().contains("yellow")){
                yellowDice= temp;
                temp.setLayoutX(687);
                temp.setLayoutY(603);
            }
            else if (imageString.toLowerCase().contains("white") || imageString.toLowerCase().contains("arcane")){
                arcaneDice = temp;
                arcaneDice.setLayoutX(902);
                arcaneDice.setLayoutY(727);
            }
            if (!strikeThrough) {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(10);
                dropShadow.setOffsetX(5);
                dropShadow.setOffsetY(5);
                dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));

                temp.setEffect(dropShadow);

                Glow glow = new Glow(0.8);
                temp.setOnMouseEntered(event -> temp.setEffect(glow));
                temp.setOnMouseExited(event -> temp.setEffect(dropShadow));
            }
        }
        
        rollDice = new Button();
        rollDice.setText("Roll Dice");
        rollDice.setPrefWidth(290);
        rollDice.setLayoutX( 865);
        rollDice.setLayoutY(509);
        rollDice.setStyle("-fx-background-color: lightblue; -fx-text-fill: darkblue; -fx-font-size: 26px; -fx-font-weight: bold; -fx-padding: 10px; ");

        // Create a shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.GRAY);

        // Apply the shadow effect to the button
        rollDice.setEffect(shadow);

        // Add glow effect on hover
        rollDice.setOnMouseEntered(e -> {
            rollDice.setEffect(new Glow(0.8));
            rollDice.setStyle("-fx-background-color: lightblue; -fx-text-fill: darkblue; -fx-font-size: 46px; -fx-font-weight: bold; -fx-border-color: darkblue; -fx-border-width: 2px;");
        });

        rollDice.setOnMouseExited(e -> {
            rollDice.setEffect(shadow);
            rollDice.setStyle("-fx-background-color: lightblue; -fx-text-fill: darkblue; -fx-font-size: 26px; -fx-font-weight: bold;");
        });


        timeWarp = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/hourglass frame.png"))));
        timeWarp.setLayoutX(1221);
        timeWarp.setLayoutY(145);
        timeWarp.setFitHeight(200);
        timeWarp.setFitWidth(160);

        // Grimoire image (left)
        // leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png")));
        leftGrimoire.setFitHeight(200);
        leftGrimoire.setFitWidth(200);
        leftGrimoire.setLayoutX(41);
        leftGrimoire.setLayoutY(33);
           DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
        leftGrimoire.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.7);
        leftGrimoire.setOnMouseEntered(event -> leftGrimoire.setEffect(glow));
        leftGrimoire.setOnMouseExited(event -> leftGrimoire.setEffect(dropShadow));

        // Add a click effect (inner shadow)
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(40);
        innerShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
        leftGrimoire.setOnMousePressed(event -> leftGrimoire.setEffect(innerShadow));
        leftGrimoire.setOnMouseReleased(event -> leftGrimoire.setEffect(dropShadow));

        // // Grimoire image (right)
        //  rightGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); 
        // rightGrimoire.setFitHeight(200);
        // rightGrimoire.setFitWidth(200);
        // rightGrimoire.setLayoutX(1438);
        // rightGrimoire.setLayoutY(135);
        // rightGrimoire.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);

        // ImageView for Wizard Hat
        wizardHat.setFitHeight(200);
        wizardHat.setFitWidth(200);
        wizardHat.setLayoutX(834);
        wizardHat.setLayoutY(14);

        player1TimeWarpButton = new ImageView();
        player1ArcaneBoostButton = new ImageView();
        player2TimeWarpButton = new ImageView();
        player2ArcaneBoostButton = new ImageView();

        // Add all ImageView nodes to the AnchorPane
        root.getChildren().addAll(mainBoard,leftGrimoire, wizardHat, infoLabel, timeWarp, rollDice);
        for (ImageView diceImage : imagePaths) {
            root.getChildren().addAll(diceImage);
        }

        // Create the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/MainMenu.css")).toExternalForm());
        boardScene = scene;
    }

    public ImageView getTimeWarp() {
        return timeWarp;
    }

    /*the following method takes a string array which represent the choosable dice correspondong to the white dice chosen by the useer in the board scene and 
     * returns a dialog that will be shown by the dice realms class to be chosen from by  the user
     */
    public Dialog<String> handleWhiteDice(ArrayList<String> whiteDiceOptions){
        // Create the custom dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Select an Option");

        // Create buttons with images

        ArrayList<Button> buttons = new ArrayList<>();
        for (String  pathString : whiteDiceOptions) {
            Button tmp = new Button();
            ImageView tempImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(pathString))));
            tempImage.setFitHeight(150);
            tempImage.setFitWidth(150);
            tmp.setGraphic(tempImage);
            tmp.setOnAction(event -> dialog.setResult(pathString.split("/")[4]));
            buttons.add(tmp);
        }
        Button close = new Button();
        close.setText("Go back");
        buttons.add(close);
        close.setOnAction(event -> dialog.setResult("CLOSED"));
        // Create a container to hold the buttons
        FlowPane buttonBox = new FlowPane(20,20);   // if you want it horizontal instead of change it to an HBox
        buttonBox.setPrefWrapLength(1200); // added this so that the ArcaneBoost dice can all fit comfortably in the screen
        for (Button dialogButton : buttons) {
            buttonBox.getChildren().add(dialogButton);
        }
        // Set the dialog content
        dialog.getDialogPane().setContent(buttonBox);
        
        return dialog;
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
        if (currentTurn == -1)
            infoLabel.setText("The current round is: Forgotten Round"+"       The current Passive player is: "+playerName);
        else if (currentTurn == -2)
            infoLabel.setText("The current round is: Arcane Boost"+"       The current Arcane Player is: " +playerName);
        else
            infoLabel.setText("The current round is: "+currentRound+"       The current Active player is: "+playerName+"        The current turn number is: "+currentTurn);
        return boardScene;
    }

    public ImageView getLeftGrimoire () {
        return leftGrimoire;
    }

    public Button getRollDiceButton() {
        return rollDice;
    }
}
