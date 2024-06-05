package game.gui.scenes.OptionsMenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConfigScene {
    private Stage stage;

    public Scene createConfigScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameConfigurationMenu.fxml"));
        Parent root = loader.load();
        
        ConfigMenuFXMLController controller = loader.getController();
        controller.setStage(stage);  // Ensure the controller has access to the stage

        return new Scene(root, 1920, 1080);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
