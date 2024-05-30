package game.gui.scenes;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GreenScene extends RealmScene {
    Button attackButton;
    ImageView backgroundView;

    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/GreenRealmImages/Terra's Heartland_11.png")));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        attackButton = new Button();
        attackButton.setPrefHeight(700);
        attackButton.setPrefWidth(700);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        root.getChildren().add(attackButton);
        // Set the position of the button
        root.setLeftAnchor(attackButton, 764.0);
        root.setTopAnchor(attackButton, 466.0);

        root.getStylesheets().add(getClass().getResource("/GreenScene.css").toExternalForm());

        super.createScene();
    }

    public void setBackgroundView(String path) {
        backgroundView.setImage(new Image(getClass().getResourceAsStream(path)));
    }

    public Button getGreenAttackButton() {
        return attackButton;
    }
}
