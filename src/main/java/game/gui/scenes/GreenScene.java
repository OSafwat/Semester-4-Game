package game.gui.scenes;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GreenScene extends RealmScene {
    Button killButton = new Button();
    ImageView backgroundView;
    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/GreenRealmImages/Terra's_Heartland.png")));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        root.getChildren().add(killButton);

        super.createScene();
    }

    public void setBackgroundView(String path) {
        backgroundView.setImage(new Image(getClass().getResourceAsStream(path)));
    }
}
