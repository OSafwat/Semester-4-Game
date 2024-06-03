package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class MagentaScene extends RealmScene {
    private ImageView phoenix, backgroundView;
    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/MagentaRealmImages/Mysitcal_Sky.png")));
        phoenix = new ImageView(new Image(getClass().getResourceAsStream("/images/MagentaRealmImages/MajesticPhoenix.png")));

         // DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));

        phoenix.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.8);
        phoenix.setOnMouseEntered(event -> phoenix.setEffect(glow));
        phoenix.setOnMouseExited(event -> phoenix.setEffect(dropShadow));

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

    public void changeMagentaSceneView(String path) {
        try {
            phoenix.setImage(new Image(getClass().getResourceAsStream(path)));
            if (!root.getChildren().contains(phoenix)) root.getChildren().add(phoenix);
        } catch (NullPointerException e) {
            backgroundView.setImage(new Image(getClass().getResourceAsStream("/images/MagentaRealmImages/Mystical_Sky_Destroyed.png")));
            if (root.getChildren().contains(phoenix)) root.getChildren().remove(phoenix);
        }
    }

    public ImageView getPhoenix() {
        return phoenix;
    }
}
