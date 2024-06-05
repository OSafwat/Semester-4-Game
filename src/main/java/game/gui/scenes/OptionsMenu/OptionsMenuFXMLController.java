package game.gui.scenes.OptionsMenu;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class OptionsMenuFXMLController {
    @FXML
    private Slider volumeSlider;

    @FXML
    private Button gameConfigurationButton;

    @FXML
    private Button returnFromOptionsButton;

    private MediaPlayer mediaPlayer;
    private Stage stage;

    @FXML
    public void initialize() {
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Volume: " + newValue.intValue());
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
            }
        });

        gameConfigurationButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameConfigurationMenu.fxml"));
                Parent root = loader.load();

                ConfigMenuFXMLController configMenuFXMLController = loader.getController();
                configMenuFXMLController.setStage(stage);

                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Button getReturnFromOptionsButton() {
        return returnFromOptionsButton;
    }
}