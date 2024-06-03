package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BlueScene extends RealmScene {
    private ImageView hydra, backgroundView;
    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueRealmImages/Tide_Abyss.png")));
        hydra = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueRealmImages/HydraSerpent"));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createScene();
    }

    public ImageView getHydra() {
        return this.hydra;
    }

}
