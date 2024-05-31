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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class RedScene extends RealmScene {

    private ImageView dragon1, dragon2, dragon3, dragon4;
    private ImageView dragonFace, dragonWings, dragonTail, dragonHeart;
    Player currentPlayer = new Player(PlayerStatus.ACTIVE);
    private boolean isPopupOpen = false;
    private AnchorPane dragonPartSelectionMenu;
    public Button closeDragonPartSelectionMenuButton;

    @Override
    public void createScene() {
        root = new AnchorPane();
        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/Emberfall-Dominion.png")));

        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);

        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);

        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        initDragonPartSelectionMenu();
        // showDragonPartSelectionMenu();
        super.createScene();
    }

    public void initializeDragons(String[] paths) {
        dragon1 = createDragon(paths[0], 197, 484);
        dragon2 = createDragon(paths[1], 697, 221);
        dragon3 = createDragon(paths[2], 1149, 493);
        dragon4 = createDragon(paths[3], 668, 619);

        dragon1.setOnMouseClicked(event -> showDragonPartSelectionMenu());
        dragon2.setOnMouseClicked(event -> showDragonPartSelectionMenu());
        dragon3.setOnMouseClicked(event -> showDragonPartSelectionMenu());
        dragon4.setOnMouseClicked(event -> showDragonPartSelectionMenu());

        root.getChildren().addAll(dragon1, dragon2, dragon3, dragon4);
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

    private void initDragonPartSelectionMenu() {
        dragonPartSelectionMenu = new AnchorPane();
        ImageView backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/Emberfall-Dominion.png")));

        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);

        dragonPartSelectionMenu.getChildren().add(backgroundView);
        dragonPartSelectionMenu.setPadding(javafx.geometry.Insets.EMPTY);

        closeDragonPartSelectionMenuButton = new Button();
        closeDragonPartSelectionMenuButton.setLayoutX(1730);
        closeDragonPartSelectionMenuButton.setLayoutY(30);
        
        ImageView goBackFromDragonSelectionMenuImage = new ImageView(new Image(getClass().getResourceAsStream("/images/BlueGoBackButton.png")));
        goBackFromDragonSelectionMenuImage.setFitWidth(150);
        goBackFromDragonSelectionMenuImage.setFitHeight(150);   
        closeDragonPartSelectionMenuButton.setGraphic(goBackFromDragonSelectionMenuImage);

        // Clip to create rounded corners
        Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        closeDragonPartSelectionMenuButton.setClip(clip);

        // DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
        closeDragonPartSelectionMenuButton.setEffect(dropShadow);

        // Add glow effect on hover
        Glow glow = new Glow(0.7);
        closeDragonPartSelectionMenuButton.setOnMouseEntered(event -> closeDragonPartSelectionMenuButton.setEffect(glow));
        closeDragonPartSelectionMenuButton.setOnMouseExited(event -> closeDragonPartSelectionMenuButton.setEffect(dropShadow));

        // Add a click effect (inner shadow)
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(40);
        innerShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
        closeDragonPartSelectionMenuButton.setOnMousePressed(event -> closeDragonPartSelectionMenuButton.setEffect(innerShadow));
        closeDragonPartSelectionMenuButton.setOnMouseReleased(event -> closeDragonPartSelectionMenuButton.setEffect(dropShadow));

        // Create a VBox
        VBox vbox = new VBox();
        vbox.setPrefSize(1280, 350);
        vbox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Please choose the Dargon body part that you would like to attack");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setTextFill(Color.WHITE);

        // Create an HBox
        HBox hbox = new HBox(120);
        hbox.setPrefHeight(275);

        // Set the background to transparent
        hbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

        dragonFace = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/face body part.png")));
        dragonHeart = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/heart body part.png")));
        dragonWings = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/wings body part.png")));
        dragonTail = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/tail body part.png")));

        hbox.getChildren().addAll(dragonFace, dragonHeart, dragonWings, dragonTail);
        vbox.getChildren().addAll(titleLabel, hbox);

        dragonPartSelectionMenu.getChildren().add(vbox);

        // Calculate the center position for the StackPane
        double anchorPaneWidth = 1920;
        double anchorPaneHeight = 1080;
        double vBoxWidth = 1280;
        double vBoxHeight = 350;
        double centerX = (anchorPaneWidth - vBoxWidth) / 2;
        double centerY = (anchorPaneHeight - vBoxHeight) / 2;

        // Set the position of the StackPane in the AnchorPane to be centered
        AnchorPane.setLeftAnchor(vbox, centerX);
        AnchorPane.setTopAnchor(vbox, centerY);

        dragonPartSelectionMenu.getChildren().add(closeDragonPartSelectionMenuButton);
    }

    public void showDragonPartSelectionMenu() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initStyle(StageStyle.UNDECORATED); // Remove title bar
        closeDragonPartSelectionMenuButton.setOnAction(event -> dialogStage.close());
        Scene dialogScene = new Scene(dragonPartSelectionMenu, 1920, 1080);

        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait(); // This will block until the dialog is closed
    }

    public ImageView getDragon1() {
        return dragon1;
    }

    public ImageView getDragon2() {
        return dragon2;
    }

    public ImageView getDragon3() {
        return dragon3;
    }

    public ImageView getDragon4() {
        return dragon4;
    }

    public ImageView getDragonFace() {return dragonFace;}
    public ImageView getDragonWings() {
        return dragonWings;
    }
    public ImageView getDragonTail() {
        return dragonTail;
    }
    public ImageView getDragonHeart() {
        return dragonHeart;
    }
}
