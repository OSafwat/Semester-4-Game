package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MagentaScene extends RealmScene {
    private ImageView phoenix;
    @Override
    public void createScene() {
        root = new AnchorPane();

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/MagentaRealmImages/Mysitcal_Sky.png")));
        phoenix = new ImageView(new Image(getClass().getResourceAsStream("/images/MagentaRealmImages/MajesticPhoenix.png")));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        phoenix.setX(710);
        phoenix.setY(290);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.getChildren().add(phoenix);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createScene();
    }

    public ImageView getPhoenix() {
        return phoenix;
    }

}
