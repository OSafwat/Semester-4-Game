package game.gui;

import game.creatures.Dragon;
import game.engine.Player;
import game.engine.enums.RealmColor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RedSceneController extends Application {

    private ImageView dragon1, dragon2, dragon3, dragon4;
    private AnchorPane root;
    Player currentPlayer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Emberfall Dominion - Red Realm");

        root = new AnchorPane();
        Scene scene = new Scene(root, 800, 600);

        Image background = new Image("src/main/resources/images/Emberfall-DominionRewards.png");
        ImageView backgroundView = new ImageView(background);
        root.getChildren().add(backgroundView);

        initializeDragons();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeDragons() {
        Dragon dragon = (Dragon) currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED);
        dragon1 = createDragon(dragon.getImage(0), 50, 100);
        dragon2 = createDragon(dragon.getImage(1), 250, 100);
        dragon3 = createDragon(dragon.getImage(2), 450, 100);
        dragon4 = createDragon(dragon.getImage(3), 650, 100);

        root.getChildren().addAll(dragon1, dragon2, dragon3, dragon4);
    }

    private ImageView createDragon(String imagePath, double x, double y) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setOnMouseClicked(this::handleDragonClick);
        return imageView;
    }

    private void handleDragonClick(MouseEvent event) {
        ImageView dragon = (ImageView) event.getSource();
        // Determine which part was clicked and handle the attack
        // For now, let's just show a message
        showAttackPopup("You attacked a part of the dragon!");
    }

    private void showAttackPopup(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attack Result");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setCurrentPlayer (Player player) {
        currentPlayer = player;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
