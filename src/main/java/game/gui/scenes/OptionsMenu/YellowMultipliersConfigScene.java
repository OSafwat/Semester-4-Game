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

public class YellowMultipliersConfigScene {
    Properties yellowMultipliersConfigProperties;
    Button saveButton, returnToConfigSceneButton;

    Scene yellowMultiplierConfigScene;

    public YellowMultipliersConfigScene() {
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

        Label configLabel = new Label("Yellow Realm Multipliers Configuration");

        loadProperties();

        mainArea.getChildren().add(configLabel);
        
        for (int i = 1; i <= 11; i++) {
            TextField temp = new TextField("Hit " + i + " Reward");

            final int index = i; // Capture the loop variable

            temp.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    yellowMultipliersConfigProperties.setProperty("hit" + index + "Reward", newValue);
                }
            });

            HBox hbox = new HBox(new Label("Hit " + i + " Reward"), temp);
            mainArea.getChildren().add(hbox);
        }

        saveButton = new Button("Save Configuration");
        saveButton.setOnAction(e -> saveProperties());

        returnToConfigSceneButton = new Button("Return to Game Configuration Menu");

        mainArea.getChildren().addAll(saveButton, returnToConfigSceneButton);

        root.getChildren().addAll(background, mainArea);

        yellowMultiplierConfigScene = new Scene(root, 1920, 1080);
    }

    private void loadProperties() {
        yellowMultipliersConfigProperties = new Properties();
        try (FileInputStream in = new FileInputStream("src/main/resources/config/RadiantSvannaMultipliers.properties")) {
            yellowMultipliersConfigProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/config/RadiantSvannaMultipliers.properties")) {
            yellowMultipliersConfigProperties.store(out, "Updated rewards properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Button getReturnToConfigSceneButton() {
        return returnToConfigSceneButton;
    }

    public Scene getYellowMultiplierConfigScene() {
        return yellowMultiplierConfigScene;
    }
}
