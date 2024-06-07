package game.gui.scenes.OptionsMenu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

public class OptionsScene {
    Button returnFromOptionsButton, gameConfigButton;
    
    Scene optionsScene;
    // Stage stage;

    Slider volumeSlider;
    MediaPlayer mediaPlayer;

    public OptionsScene() {
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
        mainArea.setPrefHeight(500);
        mainArea.setPrefWidth(1000);
        mainArea.getStyleClass().add("vbox");

        Label optionsLabel = new Label("Options"), volumeLabel = new Label("Volume");
        volumeSlider = new Slider(0, 100, 0);
        volumeSlider.setPrefHeight(50);

        // Add a listener to the slider's value property to update the media player's volume
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
        });

        gameConfigButton = new Button("Game Configuration");

        returnFromOptionsButton = new Button("Return");

        mainArea.getChildren().addAll(optionsLabel, volumeLabel, volumeSlider, gameConfigButton, returnFromOptionsButton);

        root.getChildren().addAll(background, mainArea);
        optionsScene = new Scene(root, 1920, 1080);
    }

    public Button getReturnFromOptionsButton() {
        return returnFromOptionsButton;
    }

    public Button getGameConfigButton() {
        return gameConfigButton;
    }

    public Scene getOptionsScene() {
        return optionsScene;
    }

    public void setMediaPlayer(MediaPlayer mp) {
        this.mediaPlayer = mp;

        // Set the media player's initial volume based on the slider's initial value
        mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
    }
}
