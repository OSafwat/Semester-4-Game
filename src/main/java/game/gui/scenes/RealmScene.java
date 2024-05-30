package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class RealmScene {
    public Scene mainScene;
    public AnchorPane root;
    public ImageView goBackButton;
    public void createScene() {
        mainScene = new Scene(root,1920,1080);
    };

    public Scene getScene() {
        return mainScene;
    }

    public void createGoBackButton() {
        goBackButton = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueGoBackButton.png")));
        goBackButton.setFitWidth(150);
        goBackButton.setFitHeight(150);
        goBackButton.setLayoutX(1730);
        goBackButton.setLayoutY(30);
        
       
        // Clip to create rounded corners
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

        // Add a click effect (inner shadow)
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(40);
        innerShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
        goBackButton.setOnMousePressed(event -> goBackButton.setEffect(innerShadow));
        goBackButton.setOnMouseReleased(event -> goBackButton.setEffect(dropShadow));
    }

    public ImageView getGoBackButton() {
        return goBackButton;
    }
}
