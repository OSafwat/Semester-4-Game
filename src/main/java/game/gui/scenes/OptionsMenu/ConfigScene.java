package game.gui.scenes.OptionsMenu;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
    Button roundRewardsConfigButton, saveRoundSettingsConfig, redConfigButton, greenConfigButton, blueConfigButton, magentaConfigButton, yellowConfigButton, yellowMultiplierConfigButton;

    Scene configScene;
    
    Properties roundSettingsProperties;

    TextField numberOFRoundsField, numberOfTurnsPerRoundField;


    int numberOfRounds, numberOfTurnsPerRound;

    public ConfigScene() {
        StackPane root = new StackPane();

        String css = getClass().getResource("/ConfigMenu.css").toExternalForm();
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
        numberOFRoundsField = new TextField("Enter number of rounds");
        numberOfTurnsPerRoundField = new TextField("Enter number of turns");

        saveRoundSettingsConfig = new Button("Save");

        // numberOFRoundsField.textProperty().addListener(new ChangeListener<String>() {
        //     @Override
        //     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //         boolean isPositiveInteger = newValue.matches("\\d+") && !newValue.equals("0");
        //         if (isPositiveInteger) {
        //             numberOfRounds = Integer.parseInt(newValue);
        //             updateConfigFile();
        //         }
        //     }
        // });

        // numberOfTurnsPerRoundField.textProperty().addListener(new ChangeListener<String>() {
        //     @Override
        //     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //         boolean isPositiveInteger = newValue.matches("\\d+") && !newValue.equals("0");
        //         if (isPositiveInteger) {
        //             numberOfTurnsPerRound = Integer.parseInt(newValue);
        //             updateConfigFile();
        //         }
        //     }
        // });

        HBox roundSettingsArea = new HBox(configLabel, numberOFRoundsField, numberOfTurnsPerRoundField);

        roundRewardsConfigButton = new Button("Round Rewards Configuration");

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

        mainArea.getChildren().addAll(configLabel, rewardSettingsLabel, roundSettingsArea, saveRoundSettingsConfig, roundRewardsConfigButton, redConfigButton, greenConfigButton, 
        blueConfigButton, magentaConfigButton, yellowConfigButton, yellowMultiplierConfigButton, returnToOptionsButton);

        root.getChildren().addAll(background, mainArea);
        
        configScene = new Scene(root, 1920, 1080);
    }

    public void updateRoundSettingsConfigFile() {
        roundSettingsProperties = new Properties();
        int rounds = 0;
        try (FileInputStream in = new FileInputStream("src/main/resources/config/RoundsSettings.properties")) {
            roundSettingsProperties.load(in);
            rounds = Integer.parseInt(roundSettingsProperties.getProperty("numberOfRounds"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (numberOfRounds <= 0 && numberOfRounds != 6) roundSettingsProperties.setProperty("numberOfRounds", String.valueOf(numberOfRounds));
        if (numberOfTurnsPerRound <= 0 && numberOfRounds != 3) roundSettingsProperties.setProperty("numberOfRounds", String.valueOf(numberOfTurnsPerRound));
        else System.out.println("Please enter a proper number, other than the default!");
    }

    public Button getRoundRewardsConfigButton() {
        return roundRewardsConfigButton;
    }

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

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getNumberOfTurnsPerRound() {
        return numberOfTurnsPerRound;
    }

    public TextField getNumberOFRoundsField() {
        return numberOFRoundsField;
    }

    public TextField getNumberOfTurnsPerRoundField() {
        return numberOfTurnsPerRoundField;
    }

    public Button getSaveRoundSettingsConfig() {
        return saveRoundSettingsConfig;
    }

}


