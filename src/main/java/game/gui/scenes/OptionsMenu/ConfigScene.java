package game.gui.scenes.OptionsMenu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

public class ConfigScene {
    Button returnToOptionsButton;
    Button redConfigButton, greenConfigButton, blueConfigButton, magentaConfigButton, yellowConfigButton, yellowMultiplierConfigButton;

    Scene configScene;
    // Stage stage;

    int numberOfRounds, numberOfTurnsPerRound;

    public ConfigScene() {
        StackPane root = new StackPane();

        String css = getClass().getResource("/OptionsMenu.css").toExternalForm();
        if (css != null) {
            root.getStylesheets().add(css);
        } else {
            System.err.println("CSS file not found.");
        }

        root.setPrefSize(1920, 1080);
        
        ImageView background;
        background = new ImageView(new Image(getClass().getResourceAsStream("/images/Options Menu.png")));
        background.setFitWidth(1920);
        background.setFitHeight(1080);

        VBox mainArea = new VBox();
        mainArea.setPrefWidth(1000);
        mainArea.getStyleClass().add("vbox");

        Label configLabel = new Label("Game Configuration"), rewardSettingsLabel = new Label("Reward Settings");
        TextField numberOFRoundsField = new TextField("Enter number of rounds"), numberOfTurnsPerRoundField = new TextField("Enter number of turns");
        numberOFRoundsField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean isPositiveInteger = newValue.matches("\\d+") && !newValue.equals("0");
                if (isPositiveInteger) numberOfRounds = Integer.parseInt(newValue);
            }
        });

        numberOfTurnsPerRoundField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean isPositiveInteger = newValue.matches("\\d+") && !newValue.equals("0");
                if (isPositiveInteger) numberOfTurnsPerRound = Integer.parseInt(newValue);
            }
        });

        HBox roundSettingsArea = new HBox(configLabel, numberOFRoundsField, numberOfTurnsPerRoundField);

        redConfigButton = new Button("Red Realm Rewards Configuration");
        // redConfigButton.setOnMouseClicked(e -> {
        //     scene.getChildren().clear();
        // });

        greenConfigButton = new Button("Green Realm Rewards Configuration");
        // greenConfigButton.setOnMouseClicked(e -> {
        //     scene.getChildren().clear();
        // });

        blueConfigButton = new Button("Blue Realm Rewards Configuration");
        // blueConfigButton.setOnMouseClicked(e -> {
        //     scene.getChildren().clear();
        // });

        magentaConfigButton = new Button("Magenta Realm Rewards Configuration");
        // magentaConfigButton.setOnMouseClicked(e -> {
        //     scene.getChildren().clear();
        // });

        yellowConfigButton = new Button("Yellow Realm Rewards Configuration");
        // yellowConfigButton.setOnMouseClicked(e -> {
        //     scene.getChildren().clear();
        // });

        yellowMultiplierConfigButton = new Button("Yellow Realm Multipliers Configuration");
        // yellowMultiplierConfigButton.setOnMouseClicked(e -> {
        //     scene.getChildren().clear();
        // });

        returnToOptionsButton = new Button("Return");

        mainArea.getChildren().addAll(configLabel, rewardSettingsLabel, roundSettingsArea, redConfigButton, greenConfigButton, 
        blueConfigButton, magentaConfigButton, yellowConfigButton, yellowMultiplierConfigButton, returnToOptionsButton);

        root.getChildren().addAll(background, mainArea);
        
        configScene = new Scene(root, 1920, 1080);
    }

    // public void setStage(Stage stage) {
    //     this.stage = stage;
    // }

    public Button getRedConfigButton() {
        return redConfigButton;
    }

    public Button getGreenConfigButton() {
        return greenConfigButton;
    }

    public Button getBlueConfigButton() {
        return blueConfigButton;
    }

    public Button getMagentaConfigButton() {
        return magentaConfigButton;
    }

    public Button getYellowConfigButton() {
        return yellowConfigButton;
    }

    public Button getYellowMultiplierConfigButton() {
        return yellowMultiplierConfigButton;
    }

    public Button getReturnToOptionsButton() {
        return returnToOptionsButton;
    }

    public Scene getConfigScene() {
        return configScene;
    }
}

