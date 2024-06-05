package game.gui.scenes.OptionsMenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
// import javafx.stage.Stage;

public class OptionsScene {
    //private Stage stage;
    OptionsMenuFXMLController controller;

    public OptionsScene() {
        controller = new OptionsMenuFXMLController();
    }

    public Scene createOptionsScene(MediaPlayer mediaPlayer) {
        Parent root;
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsMenu.fxml"));
            root = loader.load();

            controller = loader.getController();
            if (mediaPlayer != null) controller.setMediaPlayer(mediaPlayer);
            else controller.setMediaPlayer(new MediaPlayer(new Media(null)));

        } catch (IOException e) {
            root = new StackPane();
        }
        
        return new Scene(root, 1920, 1080);
    }

    public Button getReturnFromOptionsButton() {
        return controller.getReturnFromOptionsButton();
    }
    
    // public void setStage(Stage stage) {
    //     this.stage = stage;
    // }
}
