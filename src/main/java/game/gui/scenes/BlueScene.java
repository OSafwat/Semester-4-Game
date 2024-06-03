package game.gui.scenes;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class BlueScene extends RealmScene {
    private ImageView hydra, backgroundView;

    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueRealmImages/Tide_Abyss.png")));
        hydra = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueRealmImages/HydraSerpent1/HydraSerpent5.png")));

        // DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));

        hydra.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.8);
        hydra.setOnMouseEntered(event -> hydra.setEffect(glow));
        hydra.setOnMouseExited(event -> hydra.setEffect(dropShadow));


        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        hydra.setX(710);
        hydra.setY(290);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createScene();
    }

    public void changeBlueSceneView(String path) {
        try {
            hydra.setImage(new Image(getClass().getResourceAsStream(path)));
            if (!root.getChildren().contains(hydra)) root.getChildren().add(hydra);
        } catch  (NullPointerException e) {
            backgroundView.setImage(new Image(getClass().getResourceAsStream("/images/BlueRealmImages/Tide_Abyss_Destroyed.webp")));
            if (root.getChildren().contains(hydra)) root.getChildren().remove(hydra);
       }
    }

    public ImageView getHydra() {
        return this.hydra;
    }

}
