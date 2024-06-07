package game.gui.scenes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HowToPlayScene{

    ImageView goBackButton;
    public ImageView getGoBackButton(){
        return goBackButton;
    }
    public Scene createHelpScreen() {

        AnchorPane root = new AnchorPane();
        ImageView bg = new ImageView(new Image(getClass().getResourceAsStream("/images/Game Board Pixelated3 light.png")));
        bg.setFitWidth(1920);
        bg.setFitHeight(1080);
        
            goBackButton = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueGoBackButton.png")));
            goBackButton.setFitWidth(150);
            goBackButton.setFitHeight(150);
            goBackButton.setLayoutX(1730);
            goBackButton.setLayoutY(30);
        
             Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        goBackButton.setClip(clip);

        // DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
        goBackButton.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.7);
        goBackButton.setOnMouseEntered(event -> goBackButton.setEffect(glow));
        goBackButton.setOnMouseExited(event -> goBackButton.setEffect(dropShadow));
            
        TextArea helpText = new TextArea("1. The current round and turn are displayed at the top of the screen.\r\n" + //
                        "2. The top part shows the name of the player whose turn it is and whether they are active or passive.\r\n" + //
                        "3. The grimoire is found in the top left corner. By clicking on it, the score sheets of the two players are displayed:\r\n" + //
                        "              player one's score sheet on the left and player two's score sheet on the right.\r\n" + //
                        "4. Available dice to play will glow upon hovering with the mouse. If dice don’t glow, that means they are in the Forgotten Realm.\r\n" + //
                        "5. To use a Timewarp, if available, click on the hourglass.\r\n" + //
                        "6. For a player to go to the next round, they should first play a move to play, if available, and then click on \"Roll Dice,\" even if all dice are in the Forgotten Realm.\r\n" + //
                        "7. Upon clicking \"Roll Dice,\" the dice will show a rolling animation, indicating they are not in the Forgotten Realm.\r\n" + //
                        "8. Clicking on an available die will direct you to its respective realm to attack.\r\n" + //
                        "9. For an available die, after clicking on it, if the creature glows with gold color, it means it can be attacked; otherwise, not. \r\n" + //
                        "              For example, attempting to attack Magenta with a 1 when the previous attack was with a 5.\r\n" + //
                        "10. For the Red Realm, after choosing the dragon to attack, you will be directed to another screen to choose the part of the dragon to attack. \r\n" + //
                        "                 Valid parts to attack will also glow with gold color.\r\n" + //
                        "11. If a white die is chosen, a pop-up screen will allow the player to choose the realm to attack.\r\n"+//
                        "12. After getting an arcane boost it will allow you to choose the dice to attack with and the attck will be done off-screen \r\n"  );
        helpText.setEditable(false);
        //helpText.setWrapText(true);
        helpText.getStylesheets().add(getClass().getResource("/MainMenu.css").toExternalForm());
        helpText.getStyleClass().add("helpText");
        helpText.setPrefWidth(1536);
        helpText.setPrefHeight(812);
        helpText.setLayoutX(216);
        helpText.setLayoutY(156);

        root.requestFocus();
        
        root.getChildren().addAll(bg, helpText, goBackButton);
        return new Scene(root);
    }

    
}
