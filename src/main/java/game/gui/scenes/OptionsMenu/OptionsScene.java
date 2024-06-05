package game.gui.scenes.OptionsMenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class OptionsScene {
    private Stage stage;
    OptionsMenuFXMLController controller;

    public Scene createOptionsScene(MediaPlayer mediaPlayer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsMenu.fxml"));
        Parent root = loader.load();
        
        controller = loader.getController();
        controller.setMediaPlayer(mediaPlayer);
        controller.setStage(stage);  // Ensure the controller has access to the stage
        
        return new Scene(root, 1920, 1080);
    }

    public Button getReturnFromOptionsButton() {
        return controller.getReturnFromOptionsButton();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
