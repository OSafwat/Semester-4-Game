package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class RealmScene {
    public Scene mainScene;
    public AnchorPane root;
    public ImageView goBackButton;
    public void createScene() {
        root = new AnchorPane();
        Rectangle clip = new Rectangle(300, 200);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        
        goBackButton = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueGoBackButton.png")));
        goBackButton.setFitWidth(150);
        goBackButton.setFitHeight(150);
        goBackButton.setLayoutX(1730);
        goBackButton.setLayoutY(30);
        goBackButton.setClip(clip);
        
        root.getChildren().addAll(goBackButton);
        mainScene = new Scene(root,1920,1080);
    };

    public Scene getScene() {
        return mainScene;
    }
}
