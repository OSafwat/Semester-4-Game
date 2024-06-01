package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class YellowScene extends RealmScene {
    ImageView lion;
    @Override
    public void createScene() {
        root = new AnchorPane();

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/YellowRealmImages/Radiant_Savanna.png")));
        lion = new ImageView(new Image(getClass().getResourceAsStream("/images/YellowRealmImages/SolarLions.png")));
        lion.setLayoutX(648);
        lion.setLayoutY(383);
        lion.setFitHeight(652);
        lion.setFitWidth(610);

         // DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));

        lion.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.7);
        lion.setOnMouseEntered(event -> lion.setEffect(glow));
        lion.setOnMouseExited(event -> lion.setEffect(dropShadow));

        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().addAll(backgroundView, lion);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createScene();
    }

    public ImageView getLion() {
        return lion;
    }
}
