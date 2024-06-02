package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class GreenScene extends RealmScene {
    ImageView gaurdian, backgroundView;

    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/GreenRealmImages/Terra's_Heartland_11.png")));
        gaurdian = new ImageView(new Image(getClass().getResourceAsStream("/images/GreenRealmImages/GaiaGuardian.png")));
        gaurdian.setLayoutX(727);
        gaurdian.setLayoutY(457);
        gaurdian.setFitWidth(757);
        gaurdian.setFitHeight(694);
        

         // DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));

        gaurdian.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.8);
        gaurdian.setOnMouseEntered(event -> gaurdian.setEffect(glow));
        gaurdian.setOnMouseExited(event -> gaurdian.setEffect(dropShadow));

        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().addAll(backgroundView, gaurdian);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createScene();
    }

    public void changeGreenSceneBackgroundView(String path) {
        try {
            backgroundView.setImage(new Image(path));
        } catch (NullPointerException e) {
            backgroundView.setImage(new Image("/images/GreenRealmImages/Terra's Heartland_Destroyed.webp"));
        }
    }

    public ImageView getGuardian() {
        return gaurdian;
    }
}
