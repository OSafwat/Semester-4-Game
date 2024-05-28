package game.gui.scenes;

import game.creatures.Dragon;
import game.engine.Player;
import game.engine.PlayerStatus;
import game.engine.enums.RealmColor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class RedScene {

    private ImageView dragon1, dragon2, dragon3, dragon4;
    private AnchorPane root;
    Player currentPlayer = new Player(PlayerStatus.ACTIVE);
    private boolean isPopupOpen = false;
    Scene mainScene;

    public Scene getRedScene() {
        root = new AnchorPane();
        mainScene = new Scene(root, 1920, 1080);

        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/Emberfall-Dominion.png")));
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);
        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);

        initializeDragons();
        return mainScene;
    }

    public Scene getRoot() {
        return mainScene;
    }

    private void initializeDragons() {
        Dragon dragon = (Dragon) currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED);
        dragon1 = createDragon(dragon.getImage(0), 197, 484);
        dragon2 = createDragon(dragon.getImage(1), 697, 221);
        dragon3 = createDragon(dragon.getImage(2), 1149, 493);
        dragon4 = createDragon(dragon.getImage(3), 668, 619);

        dragon1.setOnMouseClicked(event -> showCustomPopup("You clicked on Dragon 1. Please select the body part you would like to attack."));
        dragon2.setOnMouseClicked(event -> showCustomPopup("You clicked on Dragon 2. Please select the body part you would like to attack."));
        dragon3.setOnMouseClicked(event -> showCustomPopup("You clicked on Dragon 3. Please select the body part you would like to attack."));
        dragon4.setOnMouseClicked(event -> showCustomPopup("You clicked on Dragon 4. Please select the body part you would like to attack."));

        root.getChildren().addAll(dragon1, dragon2, dragon3, dragon4);
    }

    private void showCustomPopup(String message) {
        if (isPopupOpen) {
            return;
        }

        isPopupOpen = true;

        StackPane popupPane = new StackPane();
        popupPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-background-radius: 10; -fx-padding: 20;");
        popupPane.setMaxWidth(1280);
        popupPane.setMaxHeight(720);
        popupPane.setAlignment(Pos.CENTER);

        Label messageLabel = new Label(message);
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setFont(new Font("Arial", 24));
        messageLabel.setAlignment(Pos.TOP_CENTER);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            root.getChildren().remove(popupPane);
            isPopupOpen = false;
        });

        VBox vbox = new VBox(700); // Spacing between elements
        vbox.setPrefHeight(720);
        vbox.setPrefWidth(1280);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(messageLabel, closeButton);

        popupPane.getChildren().add(vbox);

        root.getChildren().add(popupPane);

        popupPane.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            AnchorPane.setTopAnchor(popupPane, (root.getHeight() - newBounds.getHeight()) / 2);
            AnchorPane.setLeftAnchor(popupPane, (root.getWidth() - newBounds.getWidth()) / 2);
        });

        root.widthProperty().addListener((obs, oldVal, newVal) ->
                AnchorPane.setLeftAnchor(popupPane, (root.getWidth() - popupPane.getPrefWidth()) / 2)
        );
        root.heightProperty().addListener((obs, oldVal, newVal) ->
                AnchorPane.setTopAnchor(popupPane, (root.getHeight() - popupPane.getPrefHeight()) / 2)
        );
    }
    private ImageView createDragon(String imagePath, double x, double y) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setOnMouseClicked(this::handleDragonClick);
        return imageView;
    }

    private void handleDragonClick(MouseEvent event) {
        ImageView dragon = (ImageView) event.getSource();
        showAttackPopup("You attacked the dragon");
    }

    private void showAttackPopup(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attack Result");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
