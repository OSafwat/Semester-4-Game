package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GreenScene extends RealmScene {
    @Override
    public void createScene() {
        root = new AnchorPane();

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/GreenRealmImages/Terra's_Heartland_11.png")));
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
