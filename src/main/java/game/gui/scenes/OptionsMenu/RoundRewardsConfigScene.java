package game.gui.scenes.OptionsMenu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

public class RoundRewardsConfigScene {
    Properties roundRewardsProperties;
    Button saveButton, returnToConfigSceneButton;

    Scene roundRewardsConfigScene;
    int numberOfRounds;

    VBox mainArea;

    public RoundRewardsConfigScene() {
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

        mainArea = new VBox();
        mainArea.setPrefWidth(1000);
        mainArea.getStyleClass().add("vbox");

        Label configLabel = new Label("Round Rewards Configuration");

        loadProperties();

        mainArea.getChildren().add(configLabel);
        
        for (int i = 1; i <= numberOfRounds; i++) {
            TextField temp = new TextField("Round " + i + " Reward");

            HBox hbox = new HBox(new Label("Round " + i + " Reward"), temp);

            mainArea.getChildren().add(hbox);
        }

        saveButton = new Button("Save Configuration");
        saveButton.setOnAction(e -> saveProperties());

        returnToConfigSceneButton = new Button("Return to Game Configuration Menu");

        mainArea.getChildren().addAll(saveButton, returnToConfigSceneButton);

        root.getChildren().addAll(background, mainArea);

        roundRewardsConfigScene = new Scene(root, 1920, 1080);
    }

    private void loadProperties() {
        roundRewardsProperties = new Properties();
        try (FileInputStream in = new FileInputStream("src/main/resources/config/RadiantSvannaRewards.properties")) {
            roundRewardsProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/config/RoundsRewards.properties")) {
            roundRewardsProperties.store(out, "Updated rewards properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateNumberOfRounds() {
        Properties roundSettingsProperties = new Properties();
        int rounds = 0;
        try (FileInputStream in = new FileInputStream("src/main/resources/config/RoundsSettings.properties")) {
            roundSettingsProperties.load(in);
            rounds = Integer.parseInt(roundSettingsProperties.getProperty("numberOfRounds"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.numberOfRounds = rounds == 0? this.numberOfRounds : rounds;

        mainArea.getChildren().clear();
        for (int i = 1; i <= numberOfRounds; i++) {
            TextField temp = new TextField("Round " + i + " Reward");

            HBox hbox = new HBox(new Label("Round " + i + " Reward"), temp);

            mainArea.getChildren().add(hbox);
        }

        mainArea.getChildren().addAll(saveButton, returnToConfigSceneButton);
    }
    
    public Button getReturnToConfigSceneButton() {
        return returnToConfigSceneButton;
    }

    public Scene getRoundRewardsConfigScene() {
        return roundRewardsConfigScene;
    }
}
