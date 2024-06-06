package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class RealmScene {
    public Scene mainScene;
    public AnchorPane root;
    public ImageView goBackButton;
    public ImageView leftGrimoire;
    public void createScene() {
        mainScene = new Scene(root,1920,1080);
    };

    public Scene getScene() {
        return mainScene;
    }

    public DropShadow getGoldenDropShadow(){
        Glow glow = new Glow(0.9);

        // Create a drop shadow effect with a golden color
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GOLD);
        dropShadow.setRadius(20);
        dropShadow.setSpread(0.5);

                // Combine glow and drop shadow
        // label.setEffect(glow);
        glow.setInput(dropShadow);
        return dropShadow;
    }
    
    public ImageView getLeftGrimoire(){
        return this.leftGrimoire;
    }
    public void createGrimoire(){
        leftGrimoire = new ImageView(new Image(getClass().getResourceAsStream("/images/blue grimoire.png"))); ;
            leftGrimoire.setFitHeight(200);
            leftGrimoire.setFitWidth(200);
            leftGrimoire.setLayoutX(182);
            leftGrimoire.setLayoutY(143);

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
    public void addToAnchorPane(ImageView bg, TextArea textarea,TextArea textarea2, ImageView close) {
        root.getChildren().addAll(bg, textarea, textarea2,close);
    }
    public void removeFromAnchorPane(ImageView bg, TextArea textarea,TextArea textarea2, ImageView close) {
        root.getChildren().removeAll(bg, textarea, textarea2,close);
    }
}
