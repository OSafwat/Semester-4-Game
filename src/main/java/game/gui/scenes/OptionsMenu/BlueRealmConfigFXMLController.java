package game.gui.scenes.OptionsMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlueRealmConfigFXMLController {

    @FXML
    private VBox dynamicContainer;

    private Stage stage;
    private int numberOfElements;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @FXML
    public void initialize() {
        // Dynamically add elements based on the numberOfElements variable
        for (int i = 1; i <= numberOfElements; i++) {
            Label label = new Label("row" + i + "Reward");
            TextField textField = new TextField("Please enter row reward");
            HBox hbox = new HBox(label, textField);
            
            dynamicContainer.getChildren().add(hbox);
        }
    }
}
