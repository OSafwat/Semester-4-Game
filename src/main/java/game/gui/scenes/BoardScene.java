package game.gui.scenes;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


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
    ImageView leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/blue grimoire.png"))); ;
    ImageView player1TimeWarpButton;
    ImageView player1ArcaneBoostButton;
    ImageView player2TimeWarpButton;
    ImageView player2ArcaneBoostButton;
    Button rollDice;
    ImageView timeWarp;
    Label infoLabel;
    public AnchorPane anchorPane;

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

    public void makeboardScene(String[] dicePNGs) {
        infoLabel = new Label();    //the round information should be here and is set in the DiceRealms class
        infoLabel.getStyleClass().add("infoLabel");

        // Create the AnchorPane
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1920,1080 );

        // Main game board image
        ImageView mainBoard = new ImageView(new Image(getClass().getResourceAsStream("/images/Game Board Pixelated3.png"))); 
        mainBoard.setFitHeight(1080);
        mainBoard.setFitWidth(1920);
        //mainBoard.setLayoutX(-3);

        ArrayList<ImageView> imagePaths = new ArrayList<ImageView>();
        for (String imageString : dicePNGs) {
            ImageView temp;
            boolean strikeThrough = imageString.contains(".png123");
            if (imageString.contains(".gif"))
                System.out.println(imageString);
            if (strikeThrough)
                temp = new ImageView(new Image(getClass().getResourceAsStream(imageString.substring(0,imageString.length()-3))));
            else
                temp = new ImageView(new Image(getClass().getResourceAsStream(imageString))); 
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
        rollDice.setLayoutX( (1148 + 884) /2);
        rollDice.setLayoutY(439 - 187);

        timeWarp = new ImageView(new Image(getClass().getResourceAsStream("/images/hourglass frame.png")));
        timeWarp.setLayoutX(1221);
        timeWarp.setLayoutY(145);
        timeWarp.setFitHeight(200);
        timeWarp.setFitWidth(160);

        // Grimoire image (left)
        // leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png")));
        leftGrimoire.setFitHeight(200);
        leftGrimoire.setFitWidth(200);
        leftGrimoire.setLayoutX(496);
        leftGrimoire.setLayoutY(154);

        // // Grimoire image (right)
        //  rightGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/purple grimoire.png"))); 
        // rightGrimoire.setFitHeight(200);
        // rightGrimoire.setFitWidth(200);
        // rightGrimoire.setLayoutX(1438);
        // rightGrimoire.setLayoutY(135);
        // rightGrimoire.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);

        // ImageView for Wizard Hat
        wizardHat = new ImageView(new Image(getClass().getResourceAsStream("/images/wizard hat.png")));
        wizardHat.setFitHeight(200);
        wizardHat.setFitWidth(200);
        wizardHat.setLayoutX(834);
        wizardHat.setLayoutY(14);

        player1TimeWarpButton = new ImageView();
        player1ArcaneBoostButton = new ImageView();
        player2TimeWarpButton = new ImageView();
        player2ArcaneBoostButton = new ImageView();

        // Add all ImageView nodes to the AnchorPane
        anchorPane.getChildren().addAll(mainBoard,leftGrimoire, wizardHat, infoLabel, timeWarp, rollDice);
        for (ImageView diceImage : imagePaths) {
            anchorPane.getChildren().addAll(diceImage);
        }

        // Create the scene
        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add(getClass().getResource("/MainMenu.css").toExternalForm());
        boardScene = scene;
    }

    public ImageView getTimeWarp() {
        return timeWarp;
    }

    /*the following method takes a string array which represent the choosable dice correspondong to the white dice chosen by the useer in the board scene and 
     * returns a dialog that will be shown by the dice realms class to be chosen from by  the user
     */
    public Dialog handleWhiteDice(ArrayList<String> whiteDiceOptions){       
        // Create the custom dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Select an Option");

        // Create buttons with images

        ArrayList<Button> buttons = new ArrayList<>();
        for (String  pathString : whiteDiceOptions) {
            Button tmp = new Button();
            ImageView tempImage = new ImageView(new Image(getClass().getResourceAsStream(pathString)));
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

    public Dialog handleArcaneBoost(){
        Dialog<Button> choice = new Dialog<>();
        choice.setTitle("Would you like to use the ArcaneBoost?");
        choice.setHeaderText("saba7o");
        Button acceptButton = new Button();
        acceptButton.setText("Accept");
        
        Button rejectButton = new Button();
        rejectButton.setText("Reject");

        HBox temp = new HBox();
        temp.getChildren().addAll(acceptButton, rejectButton);
        choice.getDialogPane().setContent(temp);
        acceptButton.setOnAction(event -> choice.setResult(acceptButton));
        rejectButton.setOnAction(event -> choice.setResult(rejectButton));
        choice.setWidth(150);
        choice.showAndWait();
        //choice.setHeight();
        return choice;
        
    }
    public Dialog handleBonus(String color){
        ArrayList<String> paths = new ArrayList<>();
        if (color == "white"){
            paths.addAll(getPaths("red"));
            paths.addAll(getPaths("blue"));
            paths.addAll(getPaths("yellow"));
            paths.addAll(getPaths("magenta"));
            paths.addAll(getPaths("green"));
        }
        else 
            paths.addAll(getPaths(color));
         return  handleWhiteDice(paths);
    }
    public ArrayList<String> getPaths(String color){
        ArrayList<String>  arr = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            char c ;
            arr.add( "/images/Dice/"+color.substring(0,1).toUpperCase()+color.substring(1)+"/"+color+" dice "+i+".png");
        }
        return arr;
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
        if (currentTurn == -1)
            infoLabel.setText("The current round is: Forgotten Round"+"       The current Passive player is: "+playerName);
        else if (currentTurn == -2)
            infoLabel.setText("The current round is: Arcane Boost"+"       The current Arcane Player is: " +playerName);
        else
            infoLabel.setText("The current round is: "+currentRound+"       The current Active player is: "+playerName+"        The current turn number is: "+currentTurn);
        System.out.println(currentTurn);
        return boardScene;
    }

    public ImageView getRightGrimoire () {
        return rightGrimoire;
    }

    public ImageView getLeftGrimoire () {
        return leftGrimoire;
    }

    public void addToAnchorPane(ImageView bg, TextArea textarea,TextArea textarea2, ImageView close) {
        anchorPane.getChildren().addAll(bg, textarea, textarea2,close);
    }
    public void removeFromAnchorPane(ImageView bg, TextArea textarea,TextArea textarea2, ImageView close) {
        anchorPane.getChildren().removeAll(bg, textarea, textarea2,close);
    }

    public Button getRollDiceButton() {
        return rollDice;
    }
}
