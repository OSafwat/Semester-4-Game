package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class YellowScene extends RealmScene {
    @Override
    public void createScene() {
        root = new AnchorPane();

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/YellowRealmImages/Radiant_Savanna.png")));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createScene();
    }

}
