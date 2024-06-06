package game.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class YellowScene extends RealmScene {
    ImageView lion, backgroundView;
    boolean canMakeMove;
    public void setCanMakeMove(boolean canMakeMove){
        this.canMakeMove = canMakeMove;
    }
    @Override
    public void createScene() {
        root = new AnchorPane();

        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/YellowRealmImages/Radiant_Savanna.png")));
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

        if (canMakeMove){
            lion.setEffect(getGoldenDropShadow());
        }
        lion.setOnMouseEntered(event -> lion.setEffect(glow));
        lion.setOnMouseExited(event ->{ 
            //phoenix.setEffect(null);
            lion.setEffect(dropShadow);
            if (canMakeMove){
                lion.setEffect(getGoldenDropShadow());
            }
        }
    );
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().addAll(backgroundView, lion);
        root.setPadding(javafx.geometry.Insets.EMPTY);
        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createGrimoire();
        root.getChildren().add(getLeftGrimoire());
        super.createScene();
    }

    public void changeYellowSceneView(String path) {
        try {
            lion.setImage(new Image(getClass().getResourceAsStream(path)));
            if (!root.getChildren().contains(lion)) root.getChildren().add(lion);
        } catch (NullPointerException e) {
            backgroundView.setImage(new Image(getClass().getResourceAsStream("/images/YellowRealmImages/Radiant_Savanna_Destroyed.png")));
            if (root.getChildren().contains(lion)) root.getChildren().remove(lion);
        }
    }

    public ImageView getLion() {
        return lion;
    }
}
