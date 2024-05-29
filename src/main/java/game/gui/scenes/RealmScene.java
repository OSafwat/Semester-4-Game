package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class RealmScene {
    public Scene mainScene;
    public AnchorPane root;
    public ImageView goBackButton;
    public void createScene() {
        goBackButton = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueGoBackButton.png")));
        goBackButton.setFitWidth(150);
        goBackButton.setFitHeight(150);
        goBackButton.setLayoutX(1730);
        goBackButton.setLayoutY(30);

        root.getChildren().addAll(goBackButton);
        mainScene = new Scene(root,1920,1080);
    };

    public Scene getScene() {
        return mainScene;
    }
}
