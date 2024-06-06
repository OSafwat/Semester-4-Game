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

public class YellowConfigScene {
    Properties yellowConfigProperties;
    Button saveButton, returnToConfigSceneButton;

    public Scene createYellowConfigScene() {
        StackPane root = new StackPane();

        root.getStylesheets().add(getClass().getResource("OptionsMenu.css").toExternalForm());

        root.setPrefSize(1920, 1080);
        
        ImageView background;
        background = new ImageView(new Image(getClass().getResourceAsStream("/images/Options Menu.png")));
        background.setFitWidth(1920);
        background.setFitHeight(1080);

        VBox mainArea = new VBox();
        mainArea.setPrefWidth(1000);
        mainArea.getStyleClass().add("vbox");

        Label configLabel = new Label("Yellow Realm Rewards Configuration");

        loadProperties();

        mainArea.getChildren().add(configLabel);
        
        for (int i = 1; i <= 11; i++) {
            TextField temp = new TextField("Hit " + i + " Reward");

            final int index = i; // Capture the loop variable

            temp.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    yellowConfigProperties.setProperty("hit" + index + "Reward", newValue);
                }
            });

            HBox hbox = new HBox(new Label("Hit " + i + " Reward"), temp);

            mainArea.getChildren().add(hbox);
        }

        saveButton = new Button("Save Configuration");
        saveButton.setOnAction(e -> saveProperties());

        mainArea.getChildren().addAll(saveButton, returnToConfigSceneButton);

        return new Scene(root, 1920, 1080);
    }

    private void loadProperties() {
        try (FileInputStream in = new FileInputStream("src/main/resources/config/RadiantSvannaRewards.properties")) {
            yellowConfigProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/config/RadiantSvannaRewards.properties")) {
            yellowConfigProperties.store(out, "Updated rewards properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Button getReturnToConfigSceneButton() {
        return returnToConfigSceneButton;
    }
}
