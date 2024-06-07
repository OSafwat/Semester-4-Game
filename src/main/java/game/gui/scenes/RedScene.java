package game.gui.scenes;

import game.engine.Player;
import game.engine.PlayerStatus;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Pos;

public class RedScene extends RealmScene {

    private ImageView dragon1, dragon2, dragon3, dragon4;
    private ImageView dragonFace, dragonWings, dragonTail, dragonHeart;
    private ImageView backgroundView;
    Player currentPlayer = new Player(PlayerStatus.ACTIVE);
    private AnchorPane dragonPartSelectionMenu;
    public Button closeDragonPartSelectionMenuButton;
    Stage dragonPartSelectionMenuDialogueStage;

    boolean canMakeMove1;
    boolean canMakeMove2;
    boolean canMakeMove3;
    boolean canMakeMove4;
    public void setCanMakeMove1(boolean canMakeMove){
        this.canMakeMove1 = canMakeMove;
    }
    public void setCanMakeMove2(boolean canMakeMove){
        this.canMakeMove2 = canMakeMove;
    }
    public void setCanMakeMove3(boolean canMakeMove){
        this.canMakeMove3 = canMakeMove;
    }
    public void setCanMakeMove4(boolean canMakeMove){
        this.canMakeMove4 = canMakeMove;
    }

    @Override
    public void createScene() {
        root = new AnchorPane();
        backgroundView = new ImageView(new Image(getClass().getResourceAsStream("/images/RedRealmImages/Emberfall-Dominion.png")));

        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);
        backgroundView.setPreserveRatio(false);

        root.getChildren().add(backgroundView);
        root.setPadding(javafx.geometry.Insets.EMPTY);

        super.createGoBackButton();
        root.getChildren().add(getGoBackButton());
        super.createGrimoire();
        root.getChildren().add(getLeftGrimoire());
        initDragonPartSelectionMenu();
        // showDragonPartSelectionMenu();
        super.createScene();
    }

    public void glowDragonPart(int value) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));

        System.out.println(value + "MEOWOWOW");

        // Add glow effect on hover
        Glow glow = new Glow(0.7);

        if (value == 0){
            dragonFace.setEffect(getGoldenDropShadow());
        }
        else
            dragonFace.setEffect(dropShadow);
        dragonFace.setOnMouseEntered(event -> dragonFace.setEffect(glow));
        dragonFace.setOnMouseExited(event -> {
            //phoenix.setEffect(null);
            dragonFace.setEffect(dropShadow);
            if (value == 0){
                dragonFace.setEffect(getGoldenDropShadow());
            }
        });

        if (value == 1){
            dragonWings.setEffect(getGoldenDropShadow());
        }
        else
            dragonWings.setEffect(dropShadow);
        dragonWings.setOnMouseEntered(event -> dragonWings.setEffect(glow));
        dragonWings.setOnMouseExited(event -> {
            //phoenix.setEffect(null);
            dragonWings.setEffect(dropShadow);
            if (value == 1){
                dragonWings.setEffect(getGoldenDropShadow());
            }
        });

        if (value == 2){
            dragonTail.setEffect(getGoldenDropShadow());
        }
        else
            dragonTail.setEffect(dropShadow);
        dragonTail.setOnMouseEntered(event -> dragonTail.setEffect(glow));
        dragonTail.setOnMouseExited(event -> {
            //phoenix.setEffect(null);
            dragonTail.setEffect(dropShadow);
            if (value == 2){
                dragonTail.setEffect(getGoldenDropShadow());
            }
        });

        if (value == 3){
            dragonHeart.setEffect(getGoldenDropShadow());
        }
        else
            dragonHeart.setEffect(dropShadow);
        dragonHeart.setOnMouseEntered(event -> dragonHeart.setEffect(glow));
        dragonHeart.setOnMouseExited(event -> {
            //phoenix.setEffect(null);
            dragonHeart.setEffect(dropShadow);
            if (value == 3){
                dragonHeart.setEffect(getGoldenDropShadow());
            }
        });
    }

    public void initializeDragons(String[] paths) {
        dragon1 = createDragon(paths[0], 197, 484);
        dragon2 = createDragon(paths[1], 697, 221);
        dragon3 = createDragon(paths[2], 1149, 493);
        dragon4 = createDragon(paths[3], 668, 619);


        
         // DropShadow effect
         DropShadow dropShadow = new DropShadow();
         dropShadow.setRadius(10);
         dropShadow.setOffsetX(5);
         dropShadow.setOffsetY(5);
         dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5));
 
         
 
         // Add glow effect on hover
         Glow glow = new Glow(0.7);

         if (canMakeMove1)
             dragon1.setEffect(getGoldenDropShadow());
         else
            dragon1.setEffect(dropShadow);
         dragon1.setOnMouseEntered(event -> dragon1.setEffect(glow));
         dragon1.setOnMouseExited(event -> { 
            //phoenix.setEffect(null);
            dragon1.setEffect(dropShadow);
            if (canMakeMove1){
                dragon1.setEffect(getGoldenDropShadow());
            }
        });

        if (canMakeMove2)
            dragon2.setEffect(getGoldenDropShadow());
        else
            dragon2.setEffect(dropShadow);
         dragon2.setOnMouseEntered(event -> dragon2.setEffect(glow));
         dragon2.setOnMouseExited(event -> { 
            //phoenix.setEffect(null);
            dragon2.setEffect(dropShadow);
            if (canMakeMove2){
                dragon2.setEffect(getGoldenDropShadow());
            }
        });

        if (canMakeMove3)
            dragon3.setEffect(getGoldenDropShadow());
        else
            dragon3.setEffect(dropShadow);
         dragon3.setOnMouseEntered(event -> dragon3.setEffect(glow));
         dragon3.setOnMouseExited(event -> { 
            //phoenix.setEffect(null);
            dragon3.setEffect(dropShadow);
            if (canMakeMove3){
                dragon3.setEffect(getGoldenDropShadow());
            }
        });

        if (canMakeMove4)
            dragon4.setEffect(getGoldenDropShadow());
        else
            dragon4.setEffect(dropShadow);
         dragon4.setEffect(dropShadow);
         dragon4.setOnMouseEntered(event -> dragon4.setEffect(glow));
         dragon4.setOnMouseExited(event -> { 
            //phoenix.setEffect(null);
            dragon4.setEffect(dropShadow);
            if (canMakeMove4){
                dragon4.setEffect(getGoldenDropShadow());
            }
        });
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
        return imageView;
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

        dragonPartSelectionMenuDialogueStage = new Stage();
        dragonPartSelectionMenuDialogueStage.initModality(Modality.WINDOW_MODAL);
        dragonPartSelectionMenuDialogueStage.initStyle(StageStyle.UNDECORATED); // Remove title bar
        closeDragonPartSelectionMenuButton.setOnAction(event -> closeDragonPartSelectionMenu());
        Scene dialogScene = new Scene(dragonPartSelectionMenu, 1920, 1080);

        dragonPartSelectionMenuDialogueStage.setScene(dialogScene);
    }

    public void showDragonPartSelectionMenu() {
        dragonPartSelectionMenuDialogueStage.showAndWait(); // This will block until the dialog is closed
    }

    public void closeDragonPartSelectionMenu() {
        dragonPartSelectionMenuDialogueStage.close(); // This will block until the dialog is closed
    }

    public void changeRedSceneView(String[] paths) {
        int deadDragons = 0, counter = 0;
        ImageView currentDragon;
        for (String path : paths) {
            counter++;
            switch(counter) {
                case 1: 
                    currentDragon = dragon1;
                    break;
                case 2:
                    currentDragon = dragon2;
                    break;
                case 3:
                    currentDragon = dragon3;
                    break;
                case 4:
                    currentDragon = dragon4;
                    break;
                default:
                    currentDragon = dragon1;
            }
            try {
                currentDragon.setImage(new Image(getClass().getResourceAsStream(path)));
                if (!root.getChildren().contains(currentDragon)) root.getChildren().add(currentDragon);
                if (path.contains("face-wings-tail-heart"))
                    deadDragons++;
            } catch (NullPointerException e) {
                deadDragons += 1;
                if (root.getChildren().contains(currentDragon)) root.getChildren().remove(currentDragon);
            }
        }

        if (deadDragons == 4) backgroundView.setImage(new Image(getClass().getResourceAsStream("/images/GreenRealmImages/Terra's Heartland_Destroyed.png")));
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

    public ImageView getDragonFace() {
        return dragonFace;
    }

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
