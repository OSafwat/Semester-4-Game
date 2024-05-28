package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MagentaScene extends RealmScene {
    @Override
    public void createScene() {
        root = new AnchorPane();
        mainScene = new Scene(root, 1920, 1080);

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/MagentaRealmImages/Mysitcal_Sky 1.png")));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);
    }
}
