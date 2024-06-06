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

public class RedConfigScene {
    Properties redConfigProperties;
    Button saveButton, returnToConfigSceneButton;

    Scene redConfigScene;
    
    public RedConfigScene() {
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

        Label configLabel = new Label("Red Realm Rewards Configuration");

        mainArea.getChildren().add(configLabel);
        
        loadProperties();

        for (int i = 1; i <= 4; i++) {
            TextField temp = new TextField("Row " + i + " Reward");

            final int index = i; // Capture the loop variable

            temp.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    redConfigProperties.setProperty("row" + index + "Reward", newValue);
                }
            });

            HBox hbox = new HBox(new Label("Row " + i + " Reward"), temp);
            mainArea.getChildren().add(hbox);
        }

        TextField temp = new TextField("Diagonal Reward");
            
        temp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                redConfigProperties.setProperty("diagonalReward", newValue);
            }
        });

        HBox hbox = new HBox(new Label("Diagonal Reward"), temp);

        saveButton = new Button("Save Configuration");
        saveButton.setOnAction(e -> saveProperties());

        returnToConfigSceneButton = new Button("Return to Game Configuration Menu");

        mainArea.getChildren().addAll(hbox, saveButton, returnToConfigSceneButton);

        root.getChildren().addAll(background, mainArea);

        redConfigScene = new Scene(root, 1920, 1080);
    }

    private void loadProperties() {
        redConfigProperties = new Properties();
        try (FileInputStream in = new FileInputStream("src/main/resources/config/EmberfallDominionRewards.properties")) {
            redConfigProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/config/EmberfallDominionRewards.properties")) {
            redConfigProperties.store(out, "Updated rewards properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Button getReturnToConfigSceneButton() {
        return returnToConfigSceneButton;
    }

    public Scene getRedConfigScene() {
        return redConfigScene;
    }
}
