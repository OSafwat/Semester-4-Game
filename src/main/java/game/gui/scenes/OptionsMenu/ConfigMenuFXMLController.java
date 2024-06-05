package game.gui.scenes.OptionsMenu;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigMenuFXMLController {
    @FXML
    private Button roundRewards;
    @FXML
    private Button redRealmRewards;
    @FXML
    private Button greenRealmRewards;
    @FXML
    private Button blueRealmRewards;
    @FXML
    private Button magentaRealmRewards;
    @FXML
    private Button yellowRealmRewards;
    @FXML
    private Button yellowRealmMultiplier;
    @FXML
    private Button goBackToOptionsMenuButton;

    @FXML
    private TextField numberOfRounds;
    @FXML
    private TextField numberOfTurns;

    private Stage stage;

    private final String defaultRounds = "6";
    private final String defaultTurns = "3";

    @FXML
    public void initialize() {
        // Set default values
        numberOfRounds.setText(defaultRounds);
        numberOfTurns.setText(defaultTurns);

        // Add listeners to enforce numeric input and handle invalid input
        numberOfRounds.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOfRounds.setText(defaultRounds);
            }
        });

        numberOfTurns.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOfTurns.setText(defaultTurns);
            }
        });

        roundRewards.setOnAction(event -> {
            System.out.println("Round Rewards button clicked");
        });

        redRealmRewards.setOnAction(event -> {
            System.out.println("Red Realm Rewards button clicked");
        });

        greenRealmRewards.setOnAction(event -> {
            System.out.println("Green Realm Rewards button clicked");
        });

        blueRealmRewards.setOnAction(event -> {
            System.out.println("Blue Realm Rewards button clicked");
        });

        magentaRealmRewards.setOnAction(event -> {
            System.out.println("Magenta Realm Rewards button clicked");
        });

        yellowRealmRewards.setOnAction(event -> {
            System.out.println("Yellow Realm Rewards button clicked");
        });

        yellowRealmMultiplier.setOnAction(event -> {
            System.out.println("Yellow Realm Multiplier button clicked");
        });

        goBackToOptionsMenuButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsMenu.fxml"));
                Parent root = loader.load();

                OptionsMenuFXMLController optionsMenuFXMLController = loader.getController();
                optionsMenuFXMLController.setStage(stage);

                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
